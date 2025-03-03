USE lookmomicanfly;

CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARBINARY(255) NOT NULL,
    active BOOLEAN DEFAULT TRUE NOT NULL,
    registration_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    user_type ENUM('ADMIN', 'STANDARD') NOT NULL
);

CREATE TABLE IF NOT EXISTS addresses (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    active BOOLEAN DEFAULT TRUE NOT NULL,
    full_name VARBINARY(255) NOT NULL,
    street VARBINARY(255) NOT NULL,
    zip_code VARBINARY(255) NOT NULL,
    city VARBINARY(255) NOT NULL,
    country VARBINARY(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO users (id, email, password, name, active, registration_date, user_type) VALUES
(UUID(), 'adminarroyo@lookmomicanfly.com', '$2a$10$roKxmra2SwWEYe2uDmjk4eRvR0AdAgSabWq6NRonSQRO6SE6/rDdq',
UNHEX('536f6d65456e637279707465644e616d65'), 1, NOW(), 'ADMIN');