#!/bin/bash

# RailConnect Deployment Script
# This script helps deploy the application quickly

echo "🚂 RailConnect Deployment Script"
echo "================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if Java is installed
print_status "Checking Java installation..."
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    print_success "Java found: $JAVA_VERSION"
else
    print_error "Java not found. Please install Java 11 or higher."
    exit 1
fi

# Check if Maven is installed
print_status "Checking Maven installation..."
if command -v mvn &> /dev/null; then
    MAVEN_VERSION=$(mvn -version | head -n 1 | cut -d' ' -f3)
    print_success "Maven found: $MAVEN_VERSION"
else
    print_error "Maven not found. Please install Maven 3.6 or higher."
    exit 1
fi

# Check if MySQL is running
print_status "Checking MySQL connection..."
if command -v mysql &> /dev/null; then
    print_success "MySQL client found"
    print_warning "Please ensure MySQL server is running and database is set up"
    print_warning "Run: mysql -u root -p < database_schema.sql"
else
    print_warning "MySQL client not found. Please install MySQL"
fi

# Clean and compile the project
print_status "Cleaning and compiling project..."
if mvn clean compile; then
    print_success "Project compiled successfully"
else
    print_error "Compilation failed"
    exit 1
fi

# Run tests if they exist
print_status "Running tests..."
mvn test -q

# Package the application
print_status "Packaging application..."
if mvn package -q; then
    print_success "Application packaged successfully"
else
    print_error "Packaging failed"
    exit 1
fi

# Display run instructions
echo ""
echo "🎉 Deployment completed successfully!"
echo ""
echo "To run the application:"
echo "  mvn exec:java -Dexec.mainClass=\"com.reservation.TrainBookingApplication\""
echo ""
echo "Or run the JAR file:"
echo "  java -cp target/classes:target/dependency/* com.reservation.TrainBookingApplication"
echo ""
echo "Make sure to:"
echo "  1. Update database credentials in DatabaseConnectionManager.java"
echo "  2. Ensure MySQL server is running"
echo "  3. Create the database using database_schema.sql"
echo ""
echo "Happy booking! 🚂✨"