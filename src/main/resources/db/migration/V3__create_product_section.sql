CREATE TABLE IF NOT EXISTS manufacturers (
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS colors (
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS products (
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  release_year INT NOT NULL,
  active BOOLEAN DEFAULT TRUE NOT NULL,
  category ENUM('SNEAKERS','CLOTHING','ACCESSORIES','COLLECTIBLES','SKATEBOARDS','MUSIC') NOT NULL,
  subcategory ENUM('HIGH','LOW','LIGHT','PANTS','HOODIE','JACKET','COAT','PUFFER','BOXERS','CREWNECK','TEE','CAP','GLOVES','SHADES','BACKPACK','CARD','FIGURE','PLUSH','SKATEBOARD','SNOWBOARD','CD','VINYL','TAPE') NOT NULL,
  manufacturer_id VARCHAR(36) NOT NULL,
  FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS sneakers (
  id VARCHAR(36) PRIMARY KEY,
  sku VARCHAR(100) NOT NULL,
  FOREIGN KEY (id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS colors_products (
  product_id VARCHAR(36) NOT NULL,
  color_id VARCHAR(36) NOT NULL,
  PRIMARY KEY (product_id, color_id),
  FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
  FOREIGN KEY (color_id) REFERENCES colors(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS accessories (
  id VARCHAR(36) PRIMARY KEY,
  material ENUM('Cotton', 'Wool','Polyester','Nylon','Leather','Suede', 'Denim','Silk','Rubber','Plastic','Cashmere', 'Velvet') NOT NULL,
  FOREIGN KEY (id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS clothing (
  id VARCHAR(36) PRIMARY KEY,
  season ENUM('SS','FW') NOT NULL,
  FOREIGN KEY (id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS collectibles (
  id VARCHAR(36) PRIMARY KEY,
  collection_name VARCHAR(255) NOT NULL,
  FOREIGN KEY (id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS skateboards (
  id VARCHAR(36) PRIMARY KEY,
  length VARCHAR(10) NOT NULL,
  FOREIGN KEY (id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS music (
  id VARCHAR(36) PRIMARY KEY,
  format ENUM('Single','Album','Mixtape') NOT NULL,
  FOREIGN KEY (id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO manufacturers (id, name) VALUES
  (UUID(), 'Yeezy'),
  (UUID(), 'Nike'),
  (UUID(), 'Jordan'),
  (UUID(), 'Adidas'),
  (UUID(), 'Travis Scott'),
  (UUID(), 'Corteiz'),
  (UUID(), 'Seventh Store'),
  (UUID(), 'NOCTA'),
  (UUID(), 'Patta'),
  (UUID(), 'Fragment'),
  (UUID(), 'Kith'),
  (UUID(), 'KAWS'),
  (UUID(), 'Supreme'),
  (UUID(), 'Palace'),
  (UUID(), 'Aime Leon Dore'),
  (UUID(), 'New Balance'),
  (UUID(), 'Asics'),
  (UUID(), 'Salomon'),
  (UUID(), 'Puma'),
  (UUID(), 'Timberland'),
  (UUID(), 'UGG'),
  (UUID(), 'Crocs'),
  (UUID(), 'Off-White'),
  (UUID(), 'Union LA'),
  (UUID(), 'Stussy'),
  (UUID(), 'Fear of God'),
  (UUID(), 'Bape'),
  (UUID(), 'Denim Tears'),
  (UUID(), 'The North Face'),
  (UUID(), 'Sp5der'),
  (UUID(), 'Balenciaga'),
  (UUID(), 'Carhartt'),
  (UUID(), 'Sony'),
  (UUID(), 'NVIDIA'),
  (UUID(), 'Apple'),
  (UUID(), 'Pokemon'),
  (UUID(), 'Topps'),
  (UUID(), 'Yu-Gi-Oh!'),
  (UUID(), 'Pop Mart'),
  (UUID(), 'Barbie'),
  (UUID(), 'CPFM'),
  (UUID(), 'LEGO'),
  (UUID(), 'Prada'),
  (UUID(), 'Versace'),
  (UUID(), 'Oakley'),
  (UUID(), 'Vlone'),
  (UUID(), 'Represent'),
  (UUID(), 'ROSALÍA'),
  (UUID(), 'Ye-Kanye West'),
  (UUID(), 'Future'),
  (UUID(), 'ASAP Rocky'),
  (UUID(), 'The Weeknd'),
  (UUID(), 'Playboi Carti'),
  (UUID(), 'MIKE DEAN'),
  (UUID(), 'Chief Keef'),
  (UUID(), 'Rihanna'),
  (UUID(), 'Uniqlo');

INSERT INTO colors (id, name) VALUES
  (UUID(), 'Black'),
  (UUID(), 'White'),
  (UUID(), 'Grey'),
  (UUID(), 'Red'),
  (UUID(), 'Blue'),
  (UUID(), 'Orange'),
  (UUID(), 'Green'),
  (UUID(), 'Brown'),
  (UUID(), 'Purple'),
  (UUID(), 'Cream'),
  (UUID(), 'Navy'),
  (UUID(), 'Pink'),
  (UUID(), 'Yellow'),
  (UUID(), 'Off White'),
  (UUID(), 'Multicolor'),
  (UUID(), 'Lilac'),
  (UUID(), 'Tan');

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 750 Light Brown', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Brown', 'White')
WHERE p.name = 'Yeezy Boost 750 Light Brown';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 Turtle Dove', 2015, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('White', 'Grey')
WHERE p.name = 'Yeezy Boost 350 Turtle Dove';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 Pirate Black', 2015, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Boost 350 Pirate Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 950 Pirate Black', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Boost 950 Pirate Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 950 Moonrock', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Off White'
WHERE p.name = 'Yeezy Boost 950 Moonrock';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 950 Peyote', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Cream'
WHERE p.name = 'Yeezy Boost 950 Peyote';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 950 Turtle Dove', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('White', 'Grey')
WHERE p.name = 'Yeezy Boost 950 Turtle Dove';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 950 Chocolate', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Brown'
WHERE p.name = 'Yeezy Boost 950 Chocolate';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 Moonrock', 2015, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Tan'
WHERE p.name = 'Yeezy Boost 350 Moonrock';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 750 Triple Black', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Boost 750 Triple Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 Oxford Tan', 2015, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Tan'
WHERE p.name = 'Yeezy Boost 350 Oxford Tan';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Beluga', 2016, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Grey', 'Orange')
WHERE p.name = 'Yeezy Boost 350 V2 Beluga';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Black / Red', 2016, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black', 'Red')
WHERE p.name = 'Yeezy Boost 350 V2 Black / Red';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Oreo', 2016, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black', 'White')
WHERE p.name = 'Yeezy Boost 350 V2 Oreo';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Green / Black', 2016, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black', 'Green')
WHERE p.name = 'Yeezy Boost 350 V2 Green / Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Copper / Black', 2016, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black', 'Grey')
WHERE p.name = 'Yeezy Boost 350 V2 Copper / Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 750 Chocolate', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Brown'
WHERE p.name = 'Yeezy Boost 750 Chocolate';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 Wave Runner', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Grey', 'Navy')
WHERE p.name = 'Yeezy Boost 700 Wave Runner';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Powerphase Calabasas White', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Powerphase Calabasas White';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Powerphase Calabasas Grey', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Grey'
WHERE p.name = 'Yeezy Powerphase Calabasas Grey';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Zebra', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Boost 350 V2 Zebra';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Bred', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black', 'Red')
WHERE p.name = 'Yeezy Boost 350 V2 Bred';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Cream', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Boost 350 V2 Cream';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Semi Frozen', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Yellow', 'Green')
WHERE p.name = 'Yeezy Boost 350 V2 Semi Frozen';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Blue Tint', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Blue', 'Grey')
WHERE p.name = 'Yeezy Boost 350 V2 Blue Tint';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 500 Blush', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Off White'
WHERE p.name = 'Yeezy 500 Blush';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 500 Super Moon', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Yellow'
WHERE p.name = 'Yeezy 500 Super Moon';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 500 Utility Black', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy 500 Utility Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 500 Salt', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Tan'
WHERE p.name = 'Yeezy 500 Salt';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 V2 Static', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Boost 700 V2 Static';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 V2 Mauve', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Brown'
WHERE p.name = 'Yeezy Boost 700 V2 Mauve';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Powerphase Calabasas Black', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Powerphase Calabasas Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Butter', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Cream'
WHERE p.name = 'Yeezy Boost 350 V2 Butter';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Sesame', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Tan'
WHERE p.name = 'Yeezy Boost 350 V2 Sesame';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Static', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Boost 350 V2 Static';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Desert Boot Rock', 2019, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Tan'
WHERE p.name = 'Yeezy Desert Boot Rock';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Desert Boot Oil', 2019, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Desert Boot Oil';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Desert Boot Salt', 2019, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Off White'
WHERE p.name = 'Yeezy Desert Boot Salt';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Slide Bone', 2019, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Slide Bone';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Slide Resin', 2019, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Tan'
WHERE p.name = 'Yeezy Slide Resin';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 380 Alien', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('White', 'Pink')
WHERE p.name = 'Yeezy Boost 380 Alien';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 700 V3 Azael', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('White', 'Black')
WHERE p.name = 'Yeezy 700 V3 Azael';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 Salt', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Boost 700 Salt';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 Inertia', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Blue', 'Grey')
WHERE p.name = 'Yeezy Boost 700 Inertia';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Glow', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Green'
WHERE p.name = 'Yeezy Boost 350 V2 Glow';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 V2 Vanta', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Boost 700 V2 Vanta';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Static Black', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Boost 350 V2 Static Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Cloud', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Boost 350 V2 Cloud';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 500 Soft Vision', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Pink'
WHERE p.name = 'Yeezy 500 Soft Vision';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 MVMN', 2020, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Orange'
WHERE p.name = 'Yeezy Boost 700 MVMN';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy QNTM OG', 2020, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Grey', 'Black')
WHERE p.name = 'Yeezy QNTM OG';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy FOAM RNNR Ararat', 2020, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy FOAM RNNR Ararat';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Tail Light', 2020, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Grey', 'Orange')
WHERE p.name = 'Yeezy Boost 350 V2 Tail Light';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Desert Sage', 2020, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Green'
WHERE p.name = 'Yeezy Boost 350 V2 Desert Sage';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 700 V3 Alvah', 2020, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy 700 V3 Alvah';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 450 Cloud', 2021, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy 450 Cloud';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy KNIT RNNR Sulfur', 2021, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Yellow'
WHERE p.name = 'Yeezy KNIT RNNR Sulfur';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy NSLTD Boot Khaki', 2021, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Brown'
WHERE p.name = 'Yeezy NSLTD Boot Khaki';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 V2 Cream', 2021, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Cream'
WHERE p.name = 'Yeezy Boost 700 V2 Cream';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy FOAM RNNR Sand', 2021, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Off White'
WHERE p.name = 'Yeezy FOAM RNNR Sand';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy FOAM RNNR MX Moon Grey', 2021, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Grey', 'Cream')
WHERE p.name = 'Yeezy FOAM RNNR MX Moon Grey';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Slide Pure', 2021, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Cream'
WHERE p.name = 'Yeezy Slide Pure';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 1050 Hi Res', 2021, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Orange'
WHERE p.name = 'Yeezy 1050 Hi Res';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Slide Enflame', 2021, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Orange'
WHERE p.name = 'Yeezy Slide Enflame';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy FOAM RNNR Vermillon', 2021, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Red'
WHERE p.name = 'Yeezy FOAM RNNR Vermillon';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Slide Onyx', 2022, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Slide Onyx';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy FOAM RNNR Onyx', 2022, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy FOAM RNNR Onyx';


INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Pods', 2023, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Pods';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 750 Light Brown';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 Turtle Dove';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 Pirate Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 950 Pirate Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 950 Moonrock';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 950 Peyote';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 950 Turtle Dove';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 950 Chocolate';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 Moonrock';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 750 Triple Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 Oxford Tan';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Beluga';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Black / Red';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Oreo';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Green / Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Copper / Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 750 Chocolate';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 Wave Runner';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Powerphase Calabasas White';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Powerphase Calabasas Grey';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Zebra';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Bred';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Cream';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Semi Frozen';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Blue Tint';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 500 Blush';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 500 Super Moon';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 500 Utility Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 500 Salt';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 V2 Static';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 V2 Mauve';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Powerphase Calabasas Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Butter';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Sesame';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Static';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Desert Boot Rock';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Desert Boot Oil';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Desert Boot Salt';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Slide Bone';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Slide Resin';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 380 Alien';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 700 V3 Azael';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 Salt';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 Inertia';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Glow';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 V2 Vanta';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Static Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Cloud';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 500 Soft Vision';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 MVMN';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy QNTM OG';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy FOAM RNNR Ararat';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Tail Light';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Desert Sage';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 700 V3 Alvah';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 450 Cloud';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy KNIT RNNR Sulfur';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy NSLTD Boot Khaki';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 V2 Cream';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy FOAM RNNR Sand';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy FOAM RNNR MX Moon Grey';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Slide Pure';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 1050 Hi Res';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Slide Enflame';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy FOAM RNNR Vermillon';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Slide Onyx';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy FOAM RNNR Onyx';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Pods';



INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id) VALUES
((SELECT UUID()), 'Yeezy Gap Hoodie', 2021, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezy Gap Engineered by Balenciaga Dove Hoodie', 2022, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezy Gap Engineered by Balenciaga Dove Tee', 2022, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezy Gap Engineered by Balenciaga Padded Denim Jacket', 2022, 1, 'CLOTHING', 'JACKET', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezy Gap Round Jacket', 2021, 1, 'CLOTHING', 'JACKET', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezus Native Skull Merch', 2013, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezus Flying Reaper Merch', 2013, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezus Skeleton Merch', 2013, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezus Made in America Merch', 2013, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'TLOP Merch', 2016, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'The North Face 1996 Retro Nuptse', 1996, 1, 'CLOTHING', 'PUFFER', (SELECT id FROM manufacturers WHERE name = 'The North Face')),
((SELECT UUID()), 'Supreme X The North Face Map Expedition Coaches Jacket', 2014, 1, 'CLOTHING', 'COAT', (SELECT id FROM manufacturers WHERE name = 'Supreme')),
((SELECT UUID()), 'Palace Fallen City Hood', 2022, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Palace')),
((SELECT UUID()), 'BAPE ABC Camo Patchwork Zip Hoodie', 2025, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Bape')),
((SELECT UUID()), 'BAPE ABC Camo Shark Full Zip Hoodie', 2023, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Bape')),
((SELECT UUID()), 'Nike x NOCTA NRG Tech Fleece Full Zip Hoodie', 2023, 1, 'CLOTHING', 'JACKET', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Nike x NOCTA Tech Fleece Pants', 2023, 1, 'CLOTHING', 'PANTS', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Nike x NOCTA NRG Tech Fleece Crewneck', 2023, 1, 'CLOTHING', 'CREWNECK', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Nike x NOCTA Sunset Puffer Jacket', 2020, 1, 'CLOTHING', 'PUFFER', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Nike x NOCTA Black Sunset Puffer Jacket', 2020, 1, 'CLOTHING', 'PUFFER', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Nike x NOCTA SYSMAS Tee', 2022, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Nike x NOCTA Distant Regards Jersey', 2023, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Nike x NOCTA L\'Art Racing Jacket', 2024, 1, 'CLOTHING', 'JACKET', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Fear of God Essentials Sweatpants', 2020, 1, 'CLOTHING', 'PANTS', (SELECT id FROM manufacturers WHERE name = 'Fear of God')),
((SELECT UUID()), 'Fear of God Essentials Fleece Hoodie', 2024, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Fear of God')),
((SELECT UUID()), 'Denim Tears The Cotton Wreath Sweatshirt', 2023, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Denim Tears')),
((SELECT UUID()), 'Sp5der OG Web Hoodie', 2024, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Sp5der')),
((SELECT UUID()), 'Juice Wrld x Vlone 999 Tee', 2020, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Supreme')),
((SELECT UUID()), 'Supreme Hanes Boxer Briefs', 2022, 1, 'CLOTHING', 'BOXERS', (SELECT id FROM manufacturers WHERE name = 'Supreme')),
((SELECT UUID()), 'Represent Owner\'s Club Hoodie', 2022, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Represent')),
((SELECT UUID()), 'Represent Owner\'s Club Tee', 2023, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Represent')),
((SELECT UUID()), 'Represent Storms In Heaven Tee', 2023, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Represent')),
((SELECT UUID()), 'Palace X Oakley Tee', 2024, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Palace')),
((SELECT UUID()), 'Palace x Oakley Puffa', 2024, 1, 'CLOTHING', 'COAT', (SELECT id FROM manufacturers WHERE name = 'Palace')),
((SELECT UUID()), 'Carhartt WIP OG Active Rivet Jacket', 2022, 1, 'CLOTHING', 'JACKET', (SELECT id FROM manufacturers WHERE name = 'Carhartt')),
((SELECT UUID()), 'Carhartt WIP OG Detroit Aged Canvas Jacket', 2024, 1, 'CLOTHING', 'JACKET', (SELECT id FROM manufacturers WHERE name = 'Carhartt')),
((SELECT UUID()), 'Nike Lakers Lebron James Jersey', 2019, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Supreme X Fox Racing Moto Jersey Top', 2018, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Supreme')),
((SELECT UUID()), 'Travis Scott Astroworld WYWH Tee', 2018, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Supreme Box Logo Tee', 2014, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Supreme'));

INSERT INTO clothing (id, season) VALUES
((SELECT id FROM products WHERE name = 'Yeezy Gap Hoodie'), 'FW'),
((SELECT id FROM products WHERE name = 'Yeezy Gap Engineered by Balenciaga Dove Hoodie'), 'SS'),
((SELECT id FROM products WHERE name = 'Yeezy Gap Engineered by Balenciaga Dove Tee'), 'SS'),
((SELECT id FROM products WHERE name = 'Yeezy Gap Engineered by Balenciaga Padded Denim Jacket'), 'SS'),
((SELECT id FROM products WHERE name = 'Yeezy Gap Round Jacket'), 'FW'),
((SELECT id FROM products WHERE name = 'Yeezus Native Skull Merch'), 'FW'),
((SELECT id FROM products WHERE name = 'Yeezus Flying Reaper Merch'), 'FW'),
((SELECT id FROM products WHERE name = 'Yeezus Skeleton Merch'), 'FW'),
((SELECT id FROM products WHERE name = 'Yeezus Made in America Merch'), 'FW'),
((SELECT id FROM products WHERE name = 'TLOP Merch'), 'SS'),
((SELECT id FROM products WHERE name = 'The North Face 1996 Retro Nuptse'), 'FW'),
((SELECT id FROM products WHERE name = 'Supreme X The North Face Map Expedition Coaches Jacket'), 'FW'),
((SELECT id FROM products WHERE name = 'Palace Fallen City Hood'), 'SS'),
((SELECT id FROM products WHERE name = 'BAPE ABC Camo Patchwork Zip Hoodie'), 'SS'),
((SELECT id FROM products WHERE name = 'BAPE ABC Camo Shark Full Zip Hoodie'), 'SS'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA NRG Tech Fleece Full Zip Hoodie'), 'SS'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Tech Fleece Pants'), 'SS'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA NRG Tech Fleece Crewneck'), 'SS'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Sunset Puffer Jacket'), 'FW'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Black Sunset Puffer Jacket'), 'FW'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA SYSMAS Tee'), 'SS'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Distant Regards Jersey'), 'SS'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA L\'Art Racing Jacket'), 'SS'),
((SELECT id FROM products WHERE name = 'Fear of God Essentials Sweatpants'), 'FW'),
((SELECT id FROM products WHERE name = 'Fear of God Essentials Fleece Hoodie'), 'FW'),
((SELECT id FROM products WHERE name = 'Denim Tears The Cotton Wreath Sweatshirt'), 'SS'),
((SELECT id FROM products WHERE name = 'Sp5der OG Web Hoodie'), 'SS'),
((SELECT id FROM products WHERE name = 'Juice Wrld x Vlone 999 Tee'), 'SS'),
((SELECT id FROM products WHERE name = 'Supreme Hanes Boxer Briefs'), 'SS'),
((SELECT id FROM products WHERE name = 'Represent Owner\'s Club Hoodie'), 'SS'),
((SELECT id FROM products WHERE name = 'Represent Owner\'s Club Tee'), 'SS'),
((SELECT id FROM products WHERE name = 'Represent Storms In Heaven Tee'), 'SS'),
((SELECT id FROM products WHERE name = 'Palace X Oakley Tee'), 'FW'),
((SELECT id FROM products WHERE name = 'Palace x Oakley Puffa'), 'FW'),
((SELECT id FROM products WHERE name = 'Carhartt WIP OG Active Rivet Jacket'), 'FW'),
((SELECT id FROM products WHERE name = 'Carhartt WIP OG Detroit Aged Canvas Jacket'), 'FW'),
((SELECT id FROM products WHERE name = 'Nike Lakers Lebron James Jersey'), 'FW'),
((SELECT id FROM products WHERE name = 'Supreme X Fox Racing Moto Jersey Top'), 'SS'),
((SELECT id FROM products WHERE name = 'Travis Scott Astroworld WYWH Tee'), 'SS'),
((SELECT id FROM products WHERE name = 'Supreme Box Logo Tee'), 'SS');

INSERT INTO colors_products (product_id, color_id) VALUES
((SELECT id FROM products WHERE name = 'Yeezy Gap Hoodie'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Yeezy Gap Engineered by Balenciaga Dove Hoodie'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Yeezy Gap Engineered by Balenciaga Dove Tee'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Yeezy Gap Engineered by Balenciaga Padded Denim Jacket'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Yeezy Gap Round Jacket'), (SELECT id FROM colors WHERE name = 'Red')),
((SELECT id FROM products WHERE name = 'Yeezus Native Skull Merch'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Yeezus Flying Reaper Merch'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Yeezus Skeleton Merch'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Yeezus Made in America Merch'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'TLOP Merch'), (SELECT id FROM colors WHERE name = 'Yellow')),
((SELECT id FROM products WHERE name = 'The North Face 1996 Retro Nuptse'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Supreme X The North Face Map Expedition Coaches Jacket'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Supreme X The North Face Map Expedition Coaches Jacket'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Palace Fallen City Hood'), (SELECT id FROM colors WHERE name = 'Purple')),
((SELECT id FROM products WHERE name = 'BAPE ABC Camo Patchwork Zip Hoodie'), (SELECT id FROM colors WHERE name = 'Multicolor')),
((SELECT id FROM products WHERE name = 'BAPE ABC Camo Shark Full Zip Hoodie'), (SELECT id FROM colors WHERE name = 'Pink')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA NRG Tech Fleece Full Zip Hoodie'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Tech Fleece Pants'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA NRG Tech Fleece Crewneck'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Sunset Puffer Jacket'), (SELECT id FROM colors WHERE name = 'Yellow')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Black Sunset Puffer Jacket'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA SYSMAS Tee'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Distant Regards Jersey'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA L\'Art Racing Jacket'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA L\'Art Racing Jacket'), (SELECT id FROM colors WHERE name = 'Navy')),
((SELECT id FROM products WHERE name = 'Fear of God Essentials Sweatpants'), (SELECT id FROM colors WHERE name = 'Cream')),
((SELECT id FROM products WHERE name = 'Fear of God Essentials Fleece Hoodie'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Denim Tears The Cotton Wreath Sweatshirt'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Sp5der OG Web Hoodie'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Juice Wrld x Vlone 999 Tee'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Supreme Hanes Boxer Briefs'), (SELECT id FROM colors WHERE name = 'White')),
((SELECT id FROM products WHERE name = 'Represent Owner\'s Club Hoodie'), (SELECT id FROM colors WHERE name = 'Green')),
((SELECT id FROM products WHERE name = 'Represent Owner\'s Club Tee'), (SELECT id FROM colors WHERE name = 'Lilac')),
((SELECT id FROM products WHERE name = 'Represent Storms In Heaven Tee'), (SELECT id FROM colors WHERE name = 'Pink')),
((SELECT id FROM products WHERE name = 'Palace X Oakley Tee'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Palace x Oakley Puffa'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Palace x Oakley Puffa'), (SELECT id FROM colors WHERE name = 'White')),
((SELECT id FROM products WHERE name = 'Carhartt WIP OG Active Rivet Jacket'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Carhartt WIP OG Detroit Aged Canvas Jacket'), (SELECT id FROM colors WHERE name = 'Tan')),
((SELECT id FROM products WHERE name = 'Nike Lakers Lebron James Jersey'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Nike Lakers Lebron James Jersey'), (SELECT id FROM colors WHERE name = 'Yellow')),
((SELECT id FROM products WHERE name = 'Supreme X Fox Racing Moto Jersey Top'), (SELECT id FROM colors WHERE name = 'White')),
((SELECT id FROM products WHERE name = 'Supreme X Fox Racing Moto Jersey Top'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Travis Scott Astroworld WYWH Tee'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Supreme Box Logo Tee'), (SELECT id FROM colors WHERE name = 'White')),
((SELECT id FROM products WHERE name = 'Supreme Box Logo Tee'), (SELECT id FROM colors WHERE name = 'Red'));