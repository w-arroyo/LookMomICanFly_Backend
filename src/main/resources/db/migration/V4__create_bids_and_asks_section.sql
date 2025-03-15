CREATE TABLE IF NOT EXISTS shipping_companies (
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS fees (
    id VARCHAR(36) PRIMARY KEY,
    percentage DECIMAL(10,1) NOT NULL,
    description VARCHAR(255) NOT NULL,
    default BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS shipping_options (
    id VARCHAR(36) PRIMARY KEY,
    price DECIMAL(10,2) NOT NULL,
    name VARCHAR(255) NOT NULL,
    estimated_time VARCHAR(255) NOT NULL,
    shipping_company_id VARCHAR(36) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (shipping_company_id) REFERENCES shipping_companies(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS asks (
    id VARCHAR(36) PRIMARY KEY,
    amount INTEGER NOT NULL,
    fee_id VARCHAR(36) NOT NULL,
    shipping_fee DECIMAL(10,2) NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    address_id VARCHAR(36) NOT NULL,
    size ENUM('NO_SIZE','SMALL','MEDIUM','LARGE','EXTRA_LARGE','THIRTY_EIGHT','THIRTY_NINE','FORTY','FORTY_AND_HALF','FORTY_ONE','FORTY_TWO','FORTY_TWO_AND_HALF','FORTY_THREE','FORTY_FOUR','FORTY_FOUR_AND_HALF','FORTY_FIVE') NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    finalized BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE CASCADE,
    FOREIGN KEY (fee_id) REFERENCES fees(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bids (
    id VARCHAR(36) PRIMARY KEY,
    amount INTEGER NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    address_id VARCHAR(36) NOT NULL,
    size ENUM('NO_SIZE','SMALL','MEDIUM','LARGE','EXTRA_LARGE','THIRTY_EIGHT','THIRTY_NINE','FORTY','FORTY_AND_HALF','FORTY_ONE','FORTY_TWO','FORTY_TWO_AND_HALF','FORTY_THREE','FORTY_FOUR','FORTY_FOUR_AND_HALF','FORTY_FIVE') NOT NULL,
    shipping_option_id VARCHAR(36) NOT NULL,
    operational_fee INTEGER NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    finalized BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (shipping_option_id) REFERENCES shipping_options(id) ON DELETE CASCADE
);

INSERT INTO shipping_companies (id, name) VALUES
(UUID(), 'DHL'),
(UUID(), 'UPS'),
(UUID(), 'CORREOS');

SET @dhl_id = (SELECT id FROM shipping_companies WHERE name = 'DHL' LIMIT 1);
SET @ups_id = (SELECT id FROM shipping_companies WHERE name = 'UPS' LIMIT 1);
SET @correos_id = (SELECT id FROM shipping_companies WHERE name = 'CORREOS' LIMIT 1);

INSERT INTO shipping_options (id, price, name, estimated_time, shipping_company_id, active) VALUES
(UUID(), 9.99, 'ECONOMY', 'Around 22 days', @correos_id, TRUE),
(UUID(), 16.99, 'STANDARD', 'Around 14 days', @ups_id, TRUE),
(UUID(), 25.00, 'EXPRESS', 'Around 9 days', @dhl_id, TRUE);

INSERT INTO fees (id, percentage, description, active) VALUES
(UUID(), 14, 'LEVEL_1', FALSE),
(UUID(), 13, 'LEVEL_2', FALSE),
(UUID(), 12, 'LEVEL 3', FALSE),
(UUID(), 11, 'LEVEL 4', FALSE);
