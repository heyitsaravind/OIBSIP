const express = require('express');
const db = require('../config/database');
const { authenticateToken, requireAdmin } = require('../middleware/auth');

const router = express.Router();

// Dashboard statistics
router.get('/dashboard', authenticateToken, requireAdmin, (req, res) => {
  const stats = {};
  
  // Get total books
  db.get('SELECT COUNT(*) as total FROM books', (err, result) => {
    if (err) return res.status(500).json({ error: 'Database error' });
    stats.totalBooks = result.total;
    
    // Get available books
    db.get('SELECT SUM(available_copies) as available FROM books', (err, result) => {
      if (err) return res.status(500).json({ error: 'Database error' });
      stats.availableBooks = result.available || 0;
      
      // Get total members
      db.get('SELECT COUNT(*) as total FROM users WHERE role = "user"', (err, result) => {
        if (err) return res.status(500).json({ error: 'Database error' });
        stats.totalMembers = result.total;
        
        // Get active transactions
        db.get('SELECT COUNT(*) as active FROM transactions WHERE status = "issued"', (err, result) => {
          if (err) return res.status(500).json({ error: 'Database error' });
          stats.activeTransactions = result.active;
          
          // Get overdue books
          db.get(
            'SELECT COUNT(*) as overdue FROM transactions WHERE status = "issued" AND due_date < datetime("now")',
            (err, result) => {
              if (err) return res.status(500).json({ error: 'Database error' });
              stats.overdueBooks = result.overdue;
              
              // Get total fines collected
              db.get('SELECT SUM(fine_amount) as total FROM transactions WHERE fine_amount > 0', (err, result) => {
                if (err) return res.status(500).json({ error: 'Database error' });
                stats.totalFines = result.total || 0;
                
                res.json(stats);
              });
            }
          );
        });
      });
    });
  });
});

// Most popular books
router.get('/popular-books', authenticateToken, requireAdmin, (req, res) => {
  db.all(
    `SELECT b.title, b.author, b.category, COUNT(t.id) as issue_count
     FROM books b
     LEFT JOIN transactions t ON b.id = t.book_id
     GROUP BY b.id
     ORDER BY issue_count DESC
     LIMIT 10`,
    (err, books) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      res.json(books);
    }
  );
});

// Active members (most active borrowers)
router.get('/active-members', authenticateToken, requireAdmin, (req, res) => {
  db.all(
    `SELECT u.name, u.email, COUNT(t.id) as books_borrowed
     FROM users u
     LEFT JOIN transactions t ON u.id = t.user_id
     WHERE u.role = "user"
     GROUP BY u.id
     ORDER BY books_borrowed DESC
     LIMIT 10`,
    (err, members) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      res.json(members);
    }
  );
});

// Overdue books report
router.get('/overdue', authenticateToken, requireAdmin, (req, res) => {
  db.all(
    `SELECT t.*, b.title, b.author, b.isbn, u.name as user_name, u.email as user_email,
            CAST((julianday('now') - julianday(t.due_date)) AS INTEGER) as days_overdue
     FROM transactions t
     JOIN books b ON t.book_id = b.id
     JOIN users u ON t.user_id = u.id
     WHERE t.status = "issued" AND t.due_date < datetime('now')
     ORDER BY days_overdue DESC`,
    (err, overdueBooks) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      res.json(overdueBooks);
    }
  );
});

// Monthly transaction report
router.get('/monthly-transactions', authenticateToken, requireAdmin, (req, res) => {
  db.all(
    `SELECT 
       strftime('%Y-%m', issue_date) as month,
       COUNT(*) as total_issues,
       COUNT(CASE WHEN return_date IS NOT NULL THEN 1 END) as total_returns,
       SUM(fine_amount) as total_fines
     FROM transactions
     WHERE issue_date >= date('now', '-12 months')
     GROUP BY strftime('%Y-%m', issue_date)
     ORDER BY month DESC`,
    (err, monthlyData) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      res.json(monthlyData);
    }
  );
});

// Category-wise book distribution
router.get('/category-distribution', authenticateToken, requireAdmin, (req, res) => {
  db.all(
    `SELECT category, COUNT(*) as book_count, SUM(total_copies) as total_copies
     FROM books
     GROUP BY category
     ORDER BY book_count DESC`,
    (err, categories) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      res.json(categories);
    }
  );
});

// User queries report
router.get('/queries', authenticateToken, requireAdmin, (req, res) => {
  const { status } = req.query;
  
  let query = `
    SELECT q.*, u.name as user_name, u.email as user_email
    FROM queries q
    JOIN users u ON q.user_id = u.id
  `;
  let params = [];
  
  if (status) {
    query += ' WHERE q.status = ?';
    params.push(status);
  }
  
  query += ' ORDER BY q.created_at DESC';
  
  db.all(query, params, (err, queries) => {
    if (err) {
      return res.status(500).json({ error: 'Database error' });
    }
    res.json(queries);
  });
});

// Submit a query (User)
router.post('/queries', authenticateToken, requireUser, (req, res) => {
  const { subject, message } = req.body;
  
  if (!subject || !message) {
    return res.status(400).json({ error: 'Subject and message are required' });
  }
  
  db.run(
    'INSERT INTO queries (user_id, subject, message) VALUES (?, ?, ?)',
    [req.user.id, subject, message],
    function(err) {
      if (err) {
        return res.status(500).json({ error: 'Error submitting query' });
      }
      res.status(201).json({
        message: 'Query submitted successfully',
        queryId: this.lastID
      });
    }
  );
});

// Respond to a query (Admin)
router.put('/queries/:id', authenticateToken, requireAdmin, (req, res) => {
  const { admin_response, status } = req.body;
  
  db.run(
    'UPDATE queries SET admin_response = ?, status = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?',
    [admin_response, status || 'resolved', req.params.id],
    function(err) {
      if (err) {
        return res.status(500).json({ error: 'Error updating query' });
      }
      if (this.changes === 0) {
        return res.status(404).json({ error: 'Query not found' });
      }
      res.json({ message: 'Query updated successfully' });
    }
  );
});

module.exports = router;