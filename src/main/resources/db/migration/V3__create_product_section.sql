CREATE TABLE IF NOT EXISTS manufacturers (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS product_categories (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS sneakers (
    id UUID PRIMARY KEY REFERENCES product_categories(id),
    sku VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS clothing (
    id UUID PRIMARY KEY REFERENCES product_categories(id),
    season VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS accessories (
    id UUID PRIMARY KEY REFERENCES product_categories(id),
    material VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS collectibles (
    id UUID PRIMARY KEY REFERENCES product_categories(id),
    collection_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS skateboards (
    id UUID PRIMARY KEY REFERENCES product_categories(id),
    length INT
);

CREATE TABLE IF NOT EXISTS music (
    id UUID PRIMARY KEY REFERENCES product_categories(id),
    format VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    id UUID PRIMARY KEY,
    product_category_id UUID NOT NULL REFERENCES product_categories(id),
    name VARCHAR(255) NOT NULL,
    launch_year INT,
    active BOOLEAN NOT NULL,
    manufacturer_id UUID REFERENCES manufacturers(id)
);

CREATE TABLE IF NOT EXISTS colors (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS product_colors (
    product_id UUID REFERENCES products(id),
    color_id UUID REFERENCES colors(id),
    PRIMARY KEY (product_id, color_id)
);

CREATE TABLE IF NOT EXISTS product_subcategories (
    id UUID PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    product_category_id UUID NOT NULL REFERENCES product_categories(id)
);

CREATE TABLE IF NOT EXISTS sizes (
    id UUID PRIMARY KEY,
    size VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS size_charts (
    product_category_id UUID REFERENCES product_categories(id),
    size_id UUID REFERENCES sizes(id),
    PRIMARY KEY (product_category_id, size_id)
);