-- Tạo database
DROP DATABASE IF EXISTS hoatuoiuit;
CREATE DATABASE hoatuoiuit CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hoatuoiuit;

-- Xóa các bảng nếu tồn tại
DROP TABLE IF EXISTS order_product;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS product_discount;
DROP TABLE IF EXISTS product_occasion;
DROP TABLE IF EXISTS product_flower;
DROP TABLE IF EXISTS promotions;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS occasion;
DROP TABLE IF EXISTS flowers;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS cart;

-- Tạo bảng flowers
CREATE TABLE flowers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    INDEX idx_name (name),
    INDEX idx_is_active (is_active),
    CONSTRAINT unique_name UNIQUE (name)
);

-- Tạo bảng occasion
CREATE TABLE occasion (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
	image_url VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    INDEX idx_name (name),
    INDEX idx_is_active (is_active),
    CONSTRAINT unique_name UNIQUE (name),
    CONSTRAINT unique_slug UNIQUE (slug)
);

-- Tạo bảng categories
CREATE TABLE categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    INDEX idx_name (name),
    INDEX idx_is_active (is_active),
    CONSTRAINT unique_name UNIQUE (name),
    CONSTRAINT unique_slug UNIQUE (slug)
);

-- Tạo bảng payments
CREATE TABLE payments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    payment_name VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    INDEX idx_payment_name (payment_name),
    INDEX idx_is_active (is_active),
    CONSTRAINT unique_payment_name UNIQUE (payment_name)
);


-- Tạo bảng customers
CREATE TABLE customers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    email VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    phone VARCHAR(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    address VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    password_hash VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_phone (phone),
    INDEX idx_is_active (is_active),
    CONSTRAINT unique_email UNIQUE (email),
    CONSTRAINT unique_phone UNIQUE (phone)
);

-- Tạo bảng products
CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    image_url VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    category_id INT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    is_featured BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
    INDEX idx_name (name),
    INDEX idx_is_active (is_active),
    INDEX idx_category_id (category_id),
    INDEX idx_is_featured (is_featured),
    INDEX idx_price (price),
    CONSTRAINT unique_sku UNIQUE (sku)
);

-- Tạo bảng promotions
CREATE TABLE promotions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    discount_value DECIMAL(5,2) NOT NULL CHECK (discount_value >= 0),
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    INDEX idx_code (code),
    INDEX idx_dates (start_date, end_date),
    INDEX idx_is_active (is_active),
    CONSTRAINT unique_code UNIQUE (code),
    CONSTRAINT check_dates CHECK (start_date < end_date)
);

-- Tạo bảng product_flower
CREATE TABLE product_flower (
    product_id INT,
    flower_id INT,
    PRIMARY KEY (product_id, flower_id),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (flower_id) REFERENCES flowers(id) ON DELETE CASCADE,
    INDEX idx_flower_id (flower_id)
);

-- Tạo bảng product_occasion
CREATE TABLE product_occasion (
    product_id INT,
    occasion_id INT,
    PRIMARY KEY (product_id, occasion_id),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (occasion_id) REFERENCES occasion(id) ON DELETE CASCADE,
    INDEX idx_occasion_id (occasion_id)
);

-- Tạo bảng product_discount
CREATE TABLE product_discount (
    product_id INT,
    discount_id INT,
    PRIMARY KEY (product_id, discount_id),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (discount_id) REFERENCES promotions(id) ON DELETE CASCADE,
    INDEX idx_discount_id (discount_id)
);

-- Tạo bảng reviews
CREATE TABLE reviews (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    product_id INT,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    INDEX idx_customer_id (customer_id),
    INDEX idx_product_id (product_id),
    INDEX idx_rating (rating)
);

-- Tạo bảng orders
CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    delivery_date DATETIME,
    delivery_address VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    total_amount DECIMAL(10,2) NOT NULL CHECK (total_amount >= 0),
    status ENUM('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
    payment_id INT,
    note TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (payment_id) REFERENCES payments(id) ON DELETE SET NULL,
    INDEX idx_customer_id (customer_id),
    INDEX idx_payment_id (payment_id),
    INDEX idx_status (status),
    INDEX idx_order_date (order_date)
);

-- Tạo bảng order_product
CREATE TABLE order_product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL CHECK (quantity > 0),
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    discount_applied DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id)
);

-- Tạo bảng cart
CREATE TABLE cart (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    INDEX idx_customer_id (customer_id)
);

-- Tạo bảng cart_items
CREATE TABLE cart_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cart_id INT,
    product_id INT,
    quantity INT NOT NULL CHECK (quantity > 0),
    FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    INDEX idx_cart_id (cart_id),
    INDEX idx_product_id (product_id)
);
