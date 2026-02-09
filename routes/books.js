const express = require('express');
const db = require('../config/database');
const { authenticateToken, requireAdmin } = require('../middleware/auth');

const router = express.Router();

// Get all books (public access for browsing)
router.get('/', (req, res) => {
  const { category, search, page = 1, limit = 10 } = req.query;
  const offset = (page - 1) * limit;
  
  let query = 'SELECT * FROM books WHERE 1=1';
  let params = [];
  
  if (category) {
    query += ' AND category = ?';
    params.push(category);
  }
  
  if (search) {
    query += ' AND (title LIKE ? OR author LIKE ? OR isbn LIKE ?)';
    params.push(`%${search}%`, `%${search}%`, `%${search}%`);
  }
  
  query += ' ORDER BY title LIMIT ? OFFSET ?';
  params.push(parseInt(limit), parseInt(offset));
  
  db.all(query, params, (err, books) => {
    if (err) {
      return res.status(500).json({ error: 'Database error' });
    }
    
    // Get total count for pagination
    let countQuery = 'SELECT COUNT(*) as total FROM books WHERE 1=1';
    let countParams = [];
    
    if (category) {
      countQuery += ' AND category = ?';
      countParams.push(category);
    }
    
    if (search) {
      countQuery += ' AND (title LIKE ? OR author LIKE ? OR isbn LIKE ?)';
      countParams.push(`%${search}%`, `%${search}%`, `%${search}%`);
    }
    
    db.get(countQuery, countParams, (err, countResult) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      
      res.json({
        books,
        pagination: {
          page: parseInt(page),
          limit: parseInt(limit),
          total: countResult.total,
          pages: Math.ceil(countResult.total / limit)
        }
      });
    });
  });
});

// Get book by ID
router.get('/:id', (req, res) => {
  db.get('SELECT * FROM books WHERE id = ?', [req.params.id], (err, book) => {
    if (err) {
      return res.status(500).json({ error: 'Database error' });
    }
    if (!book) {
      return res.status(404).json({ error: 'Book not found' });
    }
    res.json(book);
  });
});

// Get all categories
router.get('/meta/categories', (req, res) => {
  db.all('SELECT DISTINCT category FROM books ORDER BY category', (err, rows) => {
    if (err) {
      return res.status(500).json({ error: 'Database error' });
    }
    const categories = rows.map(row => row.category);
    res.json(categories);
  });
});

// Admin routes - Add new book
router.post('/', authenticateToken, requireAdmin, (req, res) => {
  const {
    isbn, title, author, category, publisher,
    publication_year, total_copies, description
  } = req.body;

  if (!title || !author || !category) {
    return res.status(400).json({ error: 'Title, author, and category are required' });
  }

  const available_copies = total_copies || 1;

  db.run(
    `INSERT INTO books (isbn, title, author, category, publisher, publication_year, 
     total_copies, available_copies, description) 
     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)`,
    [isbn, title, author, category, publisher, publication_year, total_copies || 1, available_copies, description],
    function(err) {
      if (err) {
        if (err.message.includes('UNIQUE constraint failed')) {
          return res.status(400).json({ error: 'Book with this ISBN already exists' });
        }
        return res.status(500).json({ error: 'Error adding book' });
      }
      res.status(201).json({
        message: 'Book added successfully',
        bookId: this.lastID
      });
    }
  );
});

// Admin routes - Update book
router.put('/:id', authenticateToken, requireAdmin, (req, res) => {
  const {
    isbn, title, author, category, publisher,
    publication_year, total_copies, description
  } = req.body;

  db.run(
    `UPDATE books SET isbn = ?, title = ?, author = ?, category = ?, 
     publisher = ?, publication_year = ?, total_copies = ?, description = ?,
     updated_at = CURRENT_TIMESTAMP WHERE id = ?`,
    [isbn, title, author, category, publisher, publication_year, total_copies, description, req.params.id],
    function(err) {
      if (err) {
        if (err.message.includes('UNIQUE constraint failed')) {
          return res.status(400).json({ error: 'Book with this ISBN already exists' });
        }
        return res.status(500).json({ error: 'Error updating book' });
      }
      if (this.changes === 0) {
        return res.status(404).json({ error: 'Book not found' });
      }
      res.json({ message: 'Book updated successfully' });
    }
  );
});

// Admin routes - Delete book
router.delete('/:id', authenticateToken, requireAdmin, (req, res) => {
  // Check if book has active transactions
  db.get(
    'SELECT COUNT(*) as count FROM transactions WHERE book_id = ? AND status = "issued"',
    [req.params.id],
    (err, result) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      
      if (result.count > 0) {
        return res.status(400).json({ error: 'Cannot delete book with active transactions' });
      }
      
      db.run('DELETE FROM books WHERE id = ?', [req.params.id], function(err) {
        if (err) {
          return res.status(500).json({ error: 'Error deleting book' });
        }
        if (this.changes === 0) {
          return res.status(404).json({ error: 'Book not found' });
        }
        res.json({ message: 'Book deleted successfully' });
      });
    }
  );
});

module.exports = router;