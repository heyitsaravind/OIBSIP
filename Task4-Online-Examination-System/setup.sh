#!/bin/bash

echo "🚀 Setting up EduExam Pro - Smart Online Testing Platform..."

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "❌ Node.js is not installed. Please install Node.js first."
    echo "   Visit: https://nodejs.org/en/download/"
    exit 1
fi

# Check if MongoDB is running
if ! pgrep -x "mongod" > /dev/null; then
    echo "⚠️  MongoDB is not running. Please start MongoDB first."
    echo "   macOS: brew services start mongodb/brew/mongodb-community"
    echo "   Linux: sudo systemctl start mongod"
    echo "   Windows: net start MongoDB"
fi

echo "📦 Installing backend dependencies..."
cd backend
npm install

echo "🌱 Initializing database with sample data..."
node seedData.js

echo "📦 Installing frontend dependencies..."
cd ../frontend
npm install

echo "✅ EduExam Pro setup completed successfully!"
echo ""
echo "🎯 To start the application:"
echo "1. Start the backend server:"
echo "   cd backend && npm start"
echo ""
echo "2. In a new terminal, start the frontend:"
echo "   cd frontend && npm start"
echo ""
echo "3. Open your browser and navigate to: http://localhost:3000"
echo ""
echo "🔐 Demo login credentials:"
echo "   Email: student@eduexam.com"
echo "   Password: demo2024"
echo ""
echo "🎉 Happy learning and testing!"