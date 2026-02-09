const express = require('express');
const db = require('../config/database');
const { authenticateToken, requireAdmin, requireUser } = require('../middleware/auth');

const router = express.Router();

// Issue a book (Admin only)
router.post('/issue', authenticateToken, requireAdmin, (req, res) => {
  const { userId, bookId, daysToReturn = 14 } = req.body;

  if (!userId || !bookId) {
    return res.status(400).json({ error: 'User ID and Book ID are required' });
  }

  // Check if book is available
  db.get('SELECT available_copies FROM books WHERE id = ?', [bookId], (err, book) => {
    if (err) {
      return res.status(500).json({ error: 'Database error' });
    }
    if (!book) {
      return res.status(404).json({ error: 'Book not found' });
    }
    if (book.available_copies <= 0) {
      return res.status(400).json({ error: 'Book not available' });
    }

    // Check if user already has this book issued
    db.get(
      'SELECT id FROM transactions WHERE user_id = ? AND book_id = ? AND status = "issued"',
      [userId, bookId],
      (err, existingTransaction) => {
        if (err) {
          return res.status(500).json({ error: 'Database error' });
        }
        if (existingTransaction) {
          return res.status(400).json({ error: 'User already has this book issued' });
        }

        const dueDate = new Date();
        dueDate.setDate(dueDate.getDate() + daysToReturn);

        // Create transaction and update book availability
        db.serialize(() => {
          db.run('BEGIN TRANSACTION');
          
          db.run(
            'INSERT INTO transactions (user_id, book_id, due_date) VALUES (?, ?, ?)',
            [userId, bookId, dueDate.toISOString()],
            function(err) {
              if (err) {
                db.run('ROLLBACK');
                return res.status(500).json({ error: 'Error creating transaction' });
              }

              db.run(
                'UPDATE books SET available_copies = available_copies - 1 WHERE id = ?',
                [bookId],
                (err) => {
                  if (err) {
                    db.run('ROLLBACK');
                    return res.status(500).json({ error: 'Error updating book availability' });
                  }

                  db.run('COMMIT');
                  res.status(201).json({
                    message: 'Book issued successfully',
                    transactionId: this.lastID,
                    dueDate: dueDate.toISOString()
                  });
                }
              );
            }
          );
        });
      }
    );
  });
});

// Return a book
router.post('/return', authenticateToken, requireAdmin, (req, res) => {
  const { transactionId } = req.body;

  if (!transactionId) {
    return res.status(400).json({ error: 'Transaction ID is required' });
  }

  // Get transaction details
  db.get(
    'SELECT * FROM transactions WHERE id = ? AND status = "issued"',
    [transactionId],
    (err, transaction) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      if (!transaction) {
        return res.status(404).json({ error: 'Active transaction not found' });
      }

      const returnDate = new Date();
      const dueDate = new Date(transaction.due_date);
      let fineAmount = 0;

      // Calculate fine if overdue (₹1 per day)
      if (returnDate > dueDate) {
        const overdueDays = Math.ceil((returnDate - dueDate) / (1000 * 60 * 60 * 24));
        fineAmount = overdueDays * 1; // ₹1 per day
      }

      // Update transaction and book availability
      db.serialize(() => {
        db.run('BEGIN TRANSACTION');
        
        db.run(
          'UPDATE transactions SET return_date = ?, fine_amount = ?, status = "returned" WHERE id = ?',
          [returnDate.toISOString(), fineAmount, transactionId],
          (err) => {
            if (err) {
              db.run('ROLLBACK');
              return res.status(500).json({ error: 'Error updating transaction' });
            }

            db.run(
              'UPDATE books SET available_copies = available_copies + 1 WHERE id = ?',
              [transaction.book_id],
              (err) => {
                if (err) {
                  db.run('ROLLBACK');
                  return res.status(500).json({ error: 'Error updating book availability' });
                }

                db.run('COMMIT');
                res.json({
                  message: 'Book returned successfully',
                  fineAmount,
                  returnDate: returnDate.toISOString()
                });
              }
            );
          }
        );
      });
    }
  );
});

// Get user's current transactions
router.get('/my-books', authenticateToken, requireUser, (req, res) => {
  db.all(
    `SELECT t.*, b.title, b.author, b.isbn, b.category
     FROM transactions t 
     JOIN books b ON t.book_id = b.id 
     WHERE t.user_id = ? AND t.status = "issued"
     ORDER BY t.due_date ASC`,
    [req.user.id],
    (err, transactions) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      res.json(transactions);
    }
  );
});

// Get user's transaction history
router.get('/history', authenticateToken, requireUser, (req, res) => {
  db.all(
    `SELECT t.*, b.title, b.author, b.isbn, b.category
     FROM transactions t 
     JOIN books b ON t.book_id = b.id 
     WHERE t.user_id = ? 
     ORDER BY t.created_at DESC`,
    [req.user.id],
    (err, transactions) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      res.json(transactions);
    }
  );
});

// Admin - Get all transactions
router.get('/', authenticateToken, requireAdmin, (req, res) => {
  const { status, page = 1, limit = 20 } = req.query;
  const offset = (page - 1) * limit;
  
  let query = `
    SELECT t.*, b.title, b.author, b.isbn, u.name as user_name, u.email as user_email
    FROM transactions t 
    JOIN books b ON t.book_id = b.id 
    JOIN users u ON t.user_id = u.id
    WHERE 1=1
  `;
  let params = [];
  
  if (status) {
    query += ' AND t.status = ?';
    params.push(status);
  }
  
  query += ' ORDER BY t.created_at DESC LIMIT ? OFFSET ?';
  params.push(parseInt(limit), parseInt(offset));
  
  db.all(query, params, (err, transactions) => {
    if (err) {
      return res.status(500).json({ error: 'Database error' });
    }
    res.json(transactions);
  });
});

// Reserve a book (User)
router.post('/reserve', authenticateToken, requireUser, (req, res) => {
  const { bookId } = req.body;

  if (!bookId) {
    return res.status(400).json({ error: 'Book ID is required' });
  }

  // Check if book exists and is not available
  db.get('SELECT * FROM books WHERE id = ?', [bookId], (err, book) => {
    if (err) {
      return res.status(500).json({ error: 'Database error' });
    }
    if (!book) {
      return res.status(404).json({ error: 'Book not found' });
    }
    if (book.available_copies > 0) {
      return res.status(400).json({ error: 'Book is currently available for immediate issue' });
    }

    // Check if user already has a reservation for this book
    db.get(
      'SELECT id FROM reservations WHERE user_id = ? AND book_id = ? AND status = "active"',
      [req.user.id, bookId],
      (err, existingReservation) => {
        if (err) {
          return res.status(500).json({ error: 'Database error' });
        }
        if (existingReservation) {
          return res.status(400).json({ error: 'You already have a reservation for this book' });
        }

        db.run(
          'INSERT INTO reservations (user_id, book_id) VALUES (?, ?)',
          [req.user.id, bookId],
          function(err) {
            if (err) {
              return res.status(500).json({ error: 'Error creating reservation' });
            }
            res.status(201).json({
              message: 'Book reserved successfully',
              reservationId: this.lastID
            });
          }
        );
      }
    );
  });
});

// Get user's reservations
router.get('/reservations', authenticateToken, requireUser, (req, res) => {
  db.all(
    `SELECT r.*, b.title, b.author, b.isbn, b.category
     FROM reservations r 
     JOIN books b ON r.book_id = b.id 
     WHERE r.user_id = ? AND r.status = "active"
     ORDER BY r.created_at ASC`,
    [req.user.id],
    (err, reservations) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      res.json(reservations);
    }
  );
});

module.exports = router;