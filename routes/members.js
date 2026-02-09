const express = require('express');
const db = require('../config/database');
const { authenticateToken, requireAdmin } = require('../middleware/auth');

const router = express.Router();

// Admin only - Get all members
router.get('/', authenticateToken, requireAdmin, (req, res) => {
  const { search, page = 1, limit = 10 } = req.query;
  const offset = (page - 1) * limit;
  
  let query = 'SELECT id, email, name, phone, address, role, created_at FROM users WHERE role = "user"';
  let params = [];
  
  if (search) {
    query += ' AND (name LIKE ? OR email LIKE ?)';
    params.push(`%${search}%`, `%${search}%`);
  }
  
  query += ' ORDER BY name LIMIT ? OFFSET ?';
  params.push(parseInt(limit), parseInt(offset));
  
  db.all(query, params, (err, members) => {
    if (err) {
      return res.status(500).json({ error: 'Database error' });
    }
    
    // Get total count
    let countQuery = 'SELECT COUNT(*) as total FROM users WHERE role = "user"';
    let countParams = [];
    
    if (search) {
      countQuery += ' AND (name LIKE ? OR email LIKE ?)';
      countParams.push(`%${search}%`, `%${search}%`);
    }
    
    db.get(countQuery, countParams, (err, countResult) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      
      res.json({
        members,
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

// Admin only - Get member by ID
router.get('/:id', authenticateToken, requireAdmin, (req, res) => {
  db.get(
    'SELECT id, email, name, phone, address, role, created_at FROM users WHERE id = ? AND role = "user"',
    [req.params.id],
    (err, member) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      if (!member) {
        return res.status(404).json({ error: 'Member not found' });
      }
      res.json(member);
    }
  );
});

// Admin only - Update member
router.put('/:id', authenticateToken, requireAdmin, (req, res) => {
  const { name, email, phone, address } = req.body;
  
  db.run(
    'UPDATE users SET name = ?, email = ?, phone = ?, address = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ? AND role = "user"',
    [name, email, phone, address, req.params.id],
    function(err) {
      if (err) {
        if (err.message.includes('UNIQUE constraint failed')) {
          return res.status(400).json({ error: 'Email already exists' });
        }
        return res.status(500).json({ error: 'Error updating member' });
      }
      if (this.changes === 0) {
        return res.status(404).json({ error: 'Member not found' });
      }
      res.json({ message: 'Member updated successfully' });
    }
  );
});

// Admin only - Delete member
router.delete('/:id', authenticateToken, requireAdmin, (req, res) => {
  // Check if member has active transactions
  db.get(
    'SELECT COUNT(*) as count FROM transactions WHERE user_id = ? AND status = "issued"',
    [req.params.id],
    (err, result) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      
      if (result.count > 0) {
        return res.status(400).json({ error: 'Cannot delete member with active transactions' });
      }
      
      db.run('DELETE FROM users WHERE id = ? AND role = "user"', [req.params.id], function(err) {
        if (err) {
          return res.status(500).json({ error: 'Error deleting member' });
        }
        if (this.changes === 0) {
          return res.status(404).json({ error: 'Member not found' });
        }
        res.json({ message: 'Member deleted successfully' });
      });
    }
  );
});

// Admin only - Get member's transaction history
router.get('/:id/transactions', authenticateToken, requireAdmin, (req, res) => {
  db.all(
    `SELECT t.*, b.title, b.author, b.isbn 
     FROM transactions t 
     JOIN books b ON t.book_id = b.id 
     WHERE t.user_id = ? 
     ORDER BY t.created_at DESC`,
    [req.params.id],
    (err, transactions) => {
      if (err) {
        return res.status(500).json({ error: 'Database error' });
      }
      res.json(transactions);
    }
  );
});

module.exports = router;