-- 1. Crear la tabla user_types primero (no tiene dependencias)
CREATE TABLE user_types (
    id INT AUTO_INCREMENT PRIMARY KEY,  -- SERIAL no es compatible con MySQL
    description VARCHAR(255) NOT NULL
);

-- 2. Crear la tabla users (sin las claves foráneas a addresses)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,  -- SERIAL no es compatible con MySQL
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    registration_date DATETIME DEFAULT CURRENT_TIMESTAMP,  -- Usar DATETIME en lugar de TIMESTAMP
    user_type_id INT NOT NULL,
    FOREIGN KEY (user_type_id) REFERENCES user_types(id)
);

-- 3. Crear la tabla addresses (depende de users)
CREATE TABLE addresses (
    id INT AUTO_INCREMENT PRIMARY KEY,  -- SERIAL no es compatible con MySQL
    user_id INT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    full_name VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 4. Actualizar la tabla users para agregar las claves foráneas a addresses
ALTER TABLE users
    ADD COLUMN default_shipping_address_id INT,
    ADD COLUMN default_billing_address_id INT,
    ADD FOREIGN KEY (default_shipping_address_id) REFERENCES addresses(id),
    ADD FOREIGN KEY (default_billing_address_id) REFERENCES addresses(id);