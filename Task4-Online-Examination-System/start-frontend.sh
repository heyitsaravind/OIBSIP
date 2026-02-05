#!/bin/bash

echo "🚀 Starting EduExam Pro Frontend Application..."

cd frontend

# Check if node_modules exists
if [ ! -d "node_modules" ]; then
    echo "📦 Installing dependencies..."
    npm install
fi

echo "🌐 React application starting on http://localhost:3000"
echo "🎯 EduExam Pro will open in your default browser"
npm start