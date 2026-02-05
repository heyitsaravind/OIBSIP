#!/bin/bash

echo "🚀 Starting EduExam Pro Backend Server..."

cd backend

# Check if node_modules exists
if [ ! -d "node_modules" ]; then
    echo "📦 Installing dependencies..."
    npm install
fi

# Check if .env exists
if [ ! -f ".env" ]; then
    echo "⚠️  .env file not found. Using default configuration."
fi

echo "🌐 Backend server starting on http://localhost:5000"
echo "📊 API endpoints will be available at /api/*"
npm start