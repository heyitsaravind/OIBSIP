-- Create database
CREATE DATABASE IF NOT EXISTS train_booking_system;
USE train_booking_system;

-- Customers table (updated structure)
CREATE TABLE customers (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(50) UNIQUE NOT NULL,
    secure_password VARCHAR(100) NOT NULL,
    email_address VARCHAR(100) UNIQUE NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    registration_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    INDEX idx_login_id (login_id),
    INDEX idx_email (email_address)
);

-- Trains table (updated structure)
CREATE TABLE trains (
    train_id INT PRIMARY KEY,
    train_name VARCHAR(100) NOT NULL,
    origin_station VARCHAR(50) NOT NULL,
    destination_station VARCHAR(50) NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    total_capacity INT NOT NULL,
    seats_remaining INT NOT NULL,
    train_type VARCHAR(20) DEFAULT 'EXPRESS',
    is_operational BOOLEAN DEFAULT TRUE,
    INDEX idx_route (origin_station, destination_station),
    INDEX idx_train_name (train_name)
);

-- Reservations table (updated structure)
CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    confirmation_code VARCHAR(10) UNIQUE NOT NULL,
    customer_id INT NOT NULL,
    train_id INT NOT NULL,
    traveler_name VARCHAR(100) NOT NULL,
    travel_class VARCHAR(20) NOT NULL,
    travel_date DATE NOT NULL,
    boarding_station VARCHAR(50) NOT NULL,
    alighting_station VARCHAR(50) NOT NULL,
    status ENUM('CONFIRMED', 'CANCELLED', 'WAITING_LIST', 'REFUNDED') DEFAULT 'CONFIRMED',
    ticket_price DECIMAL(10,2) NOT NULL,
    booking_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    seat_number VARCHAR(10),
    number_of_passengers INT DEFAULT 1,
    FOREIGN KEY (customer_id) REFERENCES customers(account_id) ON DELETE CASCADE,
    FOREIGN KEY (train_id) REFERENCES trains(train_id) ON DELETE CASCADE,
    INDEX idx_confirmation_code (confirmation_code),
    INDEX idx_customer_id (customer_id),
    INDEX idx_travel_date (travel_date)
);

-- Insert sample trains with updated structure
INSERT INTO trains (train_id, train_name, origin_station, destination_station, departure_time, arrival_time, total_capacity, seats_remaining, train_type) VALUES
(10001, 'Golden Express', 'DELHI', 'MUMBAI', '06:00:00', '14:00:00', 200, 200, 'SUPERFAST'),
(10002, 'Silver Bullet', 'DELHI', 'CHANDIGARH', '07:00:00', '10:30:00', 150, 150, 'EXPRESS'),
(10003, 'Lightning Express', 'MUMBAI', 'KOLKATA', '20:00:00', '18:00:00', 300, 300, 'SUPERFAST'),
(10004, 'Comfort Rider', 'CHENNAI', 'BANGALORE', '22:00:00', '06:00:00', 250, 250, 'EXPRESS'),
(10005, 'City Connect', 'PUNE', 'MUMBAI', '06:30:00', '09:00:00', 100, 100, 'PASSENGER'),
(10006, 'Mountain Express', 'DELHI', 'SHIMLA', '05:30:00', '12:00:00', 180, 180, 'EXPRESS'),
(10007, 'Coastal Runner', 'MUMBAI', 'GOA', '08:00:00', '16:00:00', 220, 220, 'EXPRESS');

-- Insert sample customer (login_id: testuser, password: test123)
INSERT INTO customers (login_id, secure_password, email_address, customer_name, contact_number) VALUES
('testuser', 'test123', 'test@example.com', 'Test User', '9876543210'),
('admin', 'admin123', 'admin@trainbooking.com', 'System Administrator', '9999999999');

-- Create indexes for better performance
CREATE INDEX idx_trains_operational ON trains(is_operational);
CREATE INDEX idx_reservations_status ON reservations(status);
CREATE INDEX idx_customers_active ON customers(is_active);