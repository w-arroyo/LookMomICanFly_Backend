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
  category ENUM('SNEAKERS','CLOTHING','ACCESSORY','COLLECTIBLE','SKATEBOARD','MUSIC') NOT NULL,
  subcategory ENUM('HIGH','LOW','LIGHT') NOT NULL,
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
  material VARCHAR(255) NOT NULL,
  FOREIGN KEY (id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS clothing (
  id VARCHAR(36) PRIMARY KEY,
  season VARCHAR(10) NOT NULL,
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
  format VARCHAR(40) NOT NULL,
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
  (UUID(), 'Nocta'),
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
  (UUID(), 'GAP'),
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
  (UUID(), 'Rick Owens'),
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

