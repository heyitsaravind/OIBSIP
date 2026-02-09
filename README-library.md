# Digital Library Management System

A complete web-based library management system that digitizes all library operations including book management, member management, book issuing/returning, fine generation, advance booking, and comprehensive reporting.

## Features

### User Features
- **Book Browsing**: Search and browse books by title, author, category, or ISBN
- **User Registration & Login**: Secure authentication system
- **Book Reservations**: Reserve books when not available
- **Transaction History**: View borrowing history and current books
- **Query System**: Submit queries to librarians via email

### Admin Features
- **Complete Dashboard**: Overview of library statistics
- **Book Management**: Add, edit, delete books with full details
- **Member Management**: Manage user accounts and profiles
- **Transaction Management**: Issue and return books with fine calculation
- **Advanced Reporting**: Popular books, active members, overdue reports
- **Query Management**: Respond to user queries

### System Features
- **Automated Fine Calculation**: ₹1 per day for overdue books
- **Real-time Availability**: Track book availability in real-time
- **Responsive Design**: Works on desktop, tablet, and mobile devices
- **Secure Authentication**: JWT-based authentication system
- **Rate Limiting**: Protection against API abuse

## Technology Stack

- **Backend**: Node.js, Express.js
- **Database**: SQLite (easily upgradeable to PostgreSQL/MySQL)
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **Authentication**: JWT (JSON Web Tokens)
- **Email**: Nodemailer for query notifications
- **Security**: bcryptjs for password hashing, CORS protection

## Installation & Setup

### Prerequisites
- Node.js (v14 or higher)
- npm or yarn

### Installation Steps

1. **Clone or download the project**
   ```bash
   # If you have the project files, navigate to the directory
   cd digital-library-management
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Set up environment variables**
   ```bash
   # Copy the example environment file
   cp .env.example .env
   
   # Edit .env file with your configurations
   # At minimum, change the JWT_SECRET for security
   ```

4. **Initialize the database**
   ```bash
   npm run init-db
   ```

5. **Start the application**
   ```bash
   # For development
   npm run dev
   
   # For production
   npm start
   ```

6. **Access the application**
   - Open your browser and go to `http://localhost:3000`
   - Default admin credentials: `admin@library.com` / `admin123`

## Project Structure

```
digital-library-management/
├── config/
│   └── database.js          # Database configuration
├── middleware/
│   └── auth.js             # Authentication middleware
├── routes/
│   ├── auth.js             # Authentication routes
│   ├── books.js            # Book management routes
│   ├── members.js          # Member management routes
│   ├── transactions.js     # Transaction routes
│   └── reports.js          # Reports and queries routes
├── scripts/
│   └── initDatabase.js     # Database initialization
├── public/
│   ├── index.html          # Main HTML file
│   ├── styles.css          # CSS styles
│   └── script.js           # Frontend JavaScript
├── database/
│   └── library.db          # SQLite database (created after init)
├── server.js               # Main server file
├── package.json            # Dependencies and scripts
├── .env                    # Environment variables
└── README.md              # This file
```

## Database Schema

### Users Table
- User authentication and profile information
- Roles: 'admin' and 'user'

### Books Table
- Complete book information including availability tracking
- ISBN, title, author, category, publisher, publication year
- Total copies and available copies tracking

### Transactions Table
- Book issue and return records
- Due date tracking and fine calculation
- Status: 'issued', 'returned', 'overdue'

### Reservations Table
- Book reservation system for unavailable books
- Queue management for popular books

### Queries Table
- User query system with admin responses
- Status tracking: 'open', 'resolved', 'closed'

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/auth/profile` - Get user profile
- `PUT /api/auth/profile` - Update user profile

### Books
- `GET /api/books` - Get all books (with search and pagination)
- `GET /api/books/:id` - Get book by ID
- `GET /api/books/meta/categories` - Get all categories
- `POST /api/books` - Add new book (Admin only)
- `PUT /api/books/:id` - Update book (Admin only)
- `DELETE /api/books/:id` - Delete book (Admin only)

### Transactions
- `POST /api/transactions/issue` - Issue book (Admin only)
- `POST /api/transactions/return` - Return book (Admin only)
- `GET /api/transactions/my-books` - Get user's current books
- `GET /api/transactions/history` - Get user's transaction history
- `POST /api/transactions/reserve` - Reserve book
- `GET /api/transactions/reservations` - Get user's reservations

### Reports & Queries
- `GET /api/reports/dashboard` - Admin dashboard statistics
- `GET /api/reports/popular-books` - Most popular books
- `GET /api/reports/overdue` - Overdue books report
- `POST /api/reports/queries` - Submit query
- `GET /api/reports/queries` - Get queries (Admin: all, User: own)

## Configuration

### Environment Variables
- `PORT`: Server port (default: 3000)
- `JWT_SECRET`: Secret key for JWT tokens (change in production!)
- `DB_PATH`: Database file path
- `EMAIL_*`: Email configuration for notifications
- `ADMIN_EMAIL`: Default admin email
- `ADMIN_PASSWORD`: Default admin password

### Email Setup (Optional)
For query notifications, configure email settings in `.env`:
1. Use Gmail SMTP or your preferred email service
2. For Gmail, use App Passwords instead of regular password
3. Enable 2-factor authentication and generate an App Password

## Usage Guide

### For Users
1. **Register**: Create an account with your details
2. **Browse Books**: Search and filter books by various criteria
3. **Reserve Books**: Reserve unavailable books for future issue
4. **Track Books**: View your current books and return dates
5. **Submit Queries**: Contact librarians for help

### For Administrators
1. **Login**: Use admin credentials to access admin panel
2. **Dashboard**: Monitor library statistics and activity
3. **Manage Books**: Add, edit, or remove books from collection
4. **Manage Members**: View and manage user accounts
5. **Handle Transactions**: Issue and return books, calculate fines
6. **Generate Reports**: View various reports and analytics
7. **Respond to Queries**: Handle user queries and provide support

## Security Features

- Password hashing using bcryptjs
- JWT-based authentication
- Rate limiting to prevent abuse
- CORS protection
- SQL injection prevention
- Input validation and sanitization

## Customization

### Adding New Features
1. Create new routes in the `routes/` directory
2. Add corresponding frontend functions in `script.js`
3. Update the database schema if needed
4. Add new UI components in `index.html` and style in `styles.css`

### Styling
- Modify `public/styles.css` for custom styling
- The design is fully responsive and mobile-friendly
- Uses Font Awesome icons (included via CDN)

## Troubleshooting

### Common Issues

1. **Database not found**
   - Run `npm run init-db` to initialize the database

2. **Permission errors**
   - Ensure the application has write permissions to the database directory

3. **Port already in use**
   - Change the PORT in `.env` file or stop other applications using port 3000

4. **JWT errors**
   - Make sure JWT_SECRET is set in `.env` file
   - Clear browser localStorage if tokens are corrupted

### Development Tips

1. **Database Reset**
   ```bash
   rm database/library.db
   npm run init-db
   ```

2. **View Database**
   - Use SQLite browser or command line tools to inspect the database
   - Database file is located at `database/library.db`

3. **Logs**
   - Check console output for server logs
   - Use browser developer tools for frontend debugging

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License. See LICENSE file for details.

## Support

For support and questions:
1. Check the troubleshooting section
2. Review the code comments
3. Submit an issue in the project repository

---

**Note**: This is a complete, production-ready library management system. Remember to change default passwords and JWT secrets before deploying to production!