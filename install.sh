#!/bin/bash

echo "🚀 Digital Library Management System - Installation Script"
echo "========================================================="

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "❌ Node.js is not installed!"
    echo "Please install Node.js first:"
    echo "  - macOS: brew install node"
    echo "  - Ubuntu: curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash - && sudo apt-get install -y nodejs"
    echo "  - Windows: Download from https://nodejs.org/"
    exit 1
fi

echo "✅ Node.js found: $(node --version)"
echo "✅ npm found: $(npm --version)"

# Install dependencies
echo "📦 Installing dependencies..."
npm install

if [ $? -ne 0 ]; then
    echo "❌ Failed to install dependencies"
    exit 1
fi

echo "✅ Dependencies installed successfully"

# Initialize database
echo "🗄️  Initializing database..."
npm run init-db

if [ $? -ne 0 ]; then
    echo "❌ Failed to initialize database"
    exit 1
fi

echo "✅ Database initialized successfully"

# Create database directory if it doesn't exist
mkdir -p database

echo ""
echo "🎉 Installation completed successfully!"
echo ""
echo "To start the application:"
echo "  npm start          # Production mode"
echo "  npm run dev        # Development mode (auto-restart)"
echo ""
echo "Then open: http://localhost:3000"
echo "Admin login: admin@library.com / admin123"
echo ""
echo "📖 Read README.md for detailed usage instructions"