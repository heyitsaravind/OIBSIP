const db = require('../config/database');
const bcrypt = require('bcryptjs');
require('dotenv').config();

const initDatabase = () => {
  // Users table (both admin and regular users)
  db.run(`
    CREATE TABLE IF NOT EXISTS users (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      email TEXT UNIQUE NOT NULL,
      password TEXT NOT NULL,
      name TEXT NOT NULL,
      role TEXT DEFAULT 'user' CHECK(role IN ('admin', 'user')),
      phone TEXT,
      address TEXT,
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
    )
  `);

  // Books table
  db.run(`
    CREATE TABLE IF NOT EXISTS books (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      isbn TEXT UNIQUE,
      title TEXT NOT NULL,
      author TEXT NOT NULL,
      category TEXT NOT NULL,
      publisher TEXT,
      publication_year INTEGER,
      total_copies INTEGER DEFAULT 1,
      available_copies INTEGER DEFAULT 1,
      description TEXT,
      cover_image TEXT,
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
    )
  `);

  // Transactions table (issue/return records)
  db.run(`
    CREATE TABLE IF NOT EXISTS transactions (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      user_id INTEGER NOT NULL,
      book_id INTEGER NOT NULL,
      issue_date DATETIME DEFAULT CURRENT_TIMESTAMP,
      due_date DATETIME NOT NULL,
      return_date DATETIME,
      fine_amount DECIMAL(10,2) DEFAULT 0,
      status TEXT DEFAULT 'issued' CHECK(status IN ('issued', 'returned', 'overdue')),
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (user_id) REFERENCES users (id),
      FOREIGN KEY (book_id) REFERENCES books (id)
    )
  `);

  // Reservations table (advance booking)
  db.run(`
    CREATE TABLE IF NOT EXISTS reservations (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      user_id INTEGER NOT NULL,
      book_id INTEGER NOT NULL,
      reservation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
      status TEXT DEFAULT 'active' CHECK(status IN ('active', 'fulfilled', 'cancelled')),
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (user_id) REFERENCES users (id),
      FOREIGN KEY (book_id) REFERENCES books (id)
    )
  `);

  // Queries table (user queries/emails)
  db.run(`
    CREATE TABLE IF NOT EXISTS queries (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      user_id INTEGER NOT NULL,
      subject TEXT NOT NULL,
      message TEXT NOT NULL,
      status TEXT DEFAULT 'open' CHECK(status IN ('open', 'resolved', 'closed')),
      admin_response TEXT,
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (user_id) REFERENCES users (id)
    )
  `);

  // Create default admin user
  const adminEmail = process.env.ADMIN_EMAIL || 'admin@library.com';
  const adminPassword = process.env.ADMIN_PASSWORD || 'admin123';
  
  bcrypt.hash(adminPassword, 10, (err, hashedPassword) => {
    if (err) {
      console.error('Error hashing admin password:', err);
      return;
    }

    db.run(`
      INSERT OR IGNORE INTO users (email, password, name, role)
      VALUES (?, ?, 'System Administrator', 'admin')
    `, [adminEmail, hashedPassword], (err) => {
      if (err) {
        console.error('Error creating admin user:', err);
      } else {
        console.log('Database initialized successfully!');
        console.log(`Admin credentials: ${adminEmail} / ${adminPassword}`);
      }
    });
  });

  // Insert sample books
  const sampleBooks = [
    ['978-0-7432-7356-5', 'The Da Vinci Code', 'Dan Brown', 'Fiction', 'Doubleday', 2003, 3, 3, 'A mystery thriller novel'],
    ['978-0-06-112008-4', 'To Kill a Mockingbird', 'Harper Lee', 'Fiction', 'J.B. Lippincott & Co.', 1960, 2, 2, 'A classic American novel'],
    ['978-0-452-28423-4', '1984', 'George Orwell', 'Fiction', 'Secker & Warburg', 1949, 4, 4, 'Dystopian social science fiction'],
    ['978-0-7432-4722-4', 'The Catcher in the Rye', 'J.D. Salinger', 'Fiction', 'Little, Brown and Company', 1951, 2, 2, 'Coming-of-age story'],
    ['978-0-316-76948-0', 'The Alchemist', 'Paulo Coelho', 'Fiction', 'HarperCollins', 1988, 3, 3, 'Philosophical novel']
  ];

  const insertBook = db.prepare(`
    INSERT OR IGNORE INTO books (isbn, title, author, category, publisher, publication_year, total_copies, available_copies, description)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
  `);

  sampleBooks.forEach(book => {
    insertBook.run(book);
  });

  insertBook.finalize();
};

initDatabase();

// Close database connection after initialization
setTimeout(() => {
  db.close((err) => {
    if (err) {
      console.error('Error closing database:', err.message);
    } else {
      console.log('Database connection closed.');
    }
    process.exit(0);
  });
}, 1000);