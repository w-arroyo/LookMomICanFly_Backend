-- V2__modify_fields_to_varbinary.sql

-- Modificar el campo name en users
ALTER TABLE users
    MODIFY COLUMN name VARBINARY(255) NOT NULL;

-- Modificar los campos en addresses
ALTER TABLE addresses
    MODIFY COLUMN full_name VARBINARY(255) NOT NULL,
    MODIFY COLUMN street VARBINARY(255) NOT NULL,
    MODIFY COLUMN zip_code VARBINARY(255) NOT NULL,
    MODIFY COLUMN city VARBINARY(255) NOT NULL,
    MODIFY COLUMN country VARBINARY(255) NOT NULL;
