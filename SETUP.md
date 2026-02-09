# Setup Instructions for Digital Library Management System

## Prerequisites Installation

### 1. Install Node.js

#### On macOS:
```bash
# Using Homebrew (recommended)
brew install node

# Or download from official website
# Visit: https://nodejs.org/en/download/
```

#### On Ubuntu/Debian:
```bash
# Using NodeSource repository
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
sudo apt-get install -y nodejs

# Or using snap
sudo snap install node --classic
```

#### On Windows:
- Download the installer from https://nodejs.org/en/download/
- Run the installer and follow the setup wizard

### 2. Verify Installation
```bash
node --version
npm --version
```

## Project Setup

### 1. Navigate to Project Directory
```bash
cd digital-library-management
```

### 2. Install Dependencies
```bash
npm install
```

### 3. Initialize Database
```bash
npm run init-db
```

### 4. Start the Application
```bash
# For development (with auto-restart)
npm run dev

# For production
npm start
```

### 5. Access the Application
- Open your browser and go to: http://localhost:3000
- Default admin login: admin@library.com / admin123

## Quick Start Commands

```bash
# Complete setup in one go (after Node.js is installed)
npm install && npm run init-db && npm start
```

## Troubleshooting

### Node.js Installation Issues
- Make sure you have the latest version of Node.js (v14 or higher)
- Restart your terminal after installation
- Check your PATH environment variable

### Permission Issues
```bash
# On macOS/Linux, you might need to use sudo for global installations
sudo npm install -g npm@latest
```

### Database Issues
```bash
# If database initialization fails, try:
rm -rf database/
npm run init-db
```

## Development Mode

For development with auto-restart on file changes:
```bash
npm run dev
```

This uses nodemon to automatically restart the server when you make changes to the code.

## Production Deployment

1. Set environment variables properly
2. Change default passwords
3. Use a process manager like PM2:
```bash
npm install -g pm2
pm2 start server.js --name "digital-library"
```

## Next Steps

1. Change the default admin password
2. Configure email settings for query notifications
3. Customize the styling and branding
4. Add more books to the collection
5. Set up regular database backups