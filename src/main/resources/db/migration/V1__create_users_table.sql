CREATE TABLE user_types (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    default_shipping_address_id INT,
    default_billing_address_id INT,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_type_id INT NOT NULL,
    FOREIGN KEY (default_shipping_address_id) REFERENCES addresses(id),
    FOREIGN KEY (default_billing_address_id) REFERENCES addresses(id),
    FOREIGN KEY (user_type_id) REFERENCES user_types(id)
);

CREATE TABLE addresses (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    full_name VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
