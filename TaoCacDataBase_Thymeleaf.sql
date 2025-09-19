-- TaoCacDataBase_Thymeleaf.sql
-- SQL Server DDL for Thymeleaf project (simple DROP/CREATE)
-- Database: baitapWeb3

-- Create database if not exists
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'baitapWeb3')
BEGIN
    CREATE DATABASE baitapWeb3;
END
GO

USE baitapWeb3;
GO

-- Drop tables if exist (respect FK order)
IF OBJECT_ID('dbo.Product', 'U') IS NOT NULL DROP TABLE dbo.Product;
IF OBJECT_ID('dbo.Category', 'U') IS NOT NULL DROP TABLE dbo.Category;
IF OBJECT_ID('dbo.Users', 'U') IS NOT NULL DROP TABLE dbo.Users;

-- Users table (the app may generate id manually)
CREATE TABLE dbo.Users (
    id INT NOT NULL,
    email NVARCHAR(255) NOT NULL,
    username NVARCHAR(100) NOT NULL,
    fullname NVARCHAR(255) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    avatar NVARCHAR(500) NULL,
    roleid INT NOT NULL DEFAULT 2,
    phone NVARCHAR(20) NULL,
    createdDate DATETIME DEFAULT GETDATE(),
    CONSTRAINT PK_Users PRIMARY KEY (id),
    CONSTRAINT UQ_Users_Email UNIQUE (email),
    CONSTRAINT UQ_Users_Username UNIQUE (username),
    CONSTRAINT CK_Users_Roleid CHECK (roleid IN (1,2))
);

-- Category table
CREATE TABLE dbo.Category (
    category_id INT IDENTITY(1,1) NOT NULL,
    categoryName NVARCHAR(255) NOT NULL,
    images NVARCHAR(MAX) NULL,
    CONSTRAINT PK_Category PRIMARY KEY (category_id)
);

CREATE INDEX IX_Category_categoryName ON dbo.Category(categoryName);

-- Product table
CREATE TABLE dbo.Product (
    product_id INT IDENTITY(1,1) NOT NULL,
    productName NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX) NULL,
    image NVARCHAR(500) NULL,  -- ảnh sản phẩm
    price DECIMAL(10,2) NOT NULL DEFAULT 0,  -- giá sản phẩm
    purchases BIGINT NOT NULL DEFAULT 0,  -- số lượng đã bán
    stock INT NOT NULL DEFAULT 0,  -- số lượng tồn kho
    category_id INT NOT NULL,
    CONSTRAINT PK_Product PRIMARY KEY (product_id),
    CONSTRAINT FK_Product_Category FOREIGN KEY (category_id) REFERENCES dbo.Category(category_id)
);

CREATE INDEX IX_Product_productName ON dbo.Product(productName);
CREATE INDEX IX_Product_category_id ON dbo.Product(category_id);
CREATE INDEX IX_Product_price ON dbo.Product(price);

-- Insert sample data
-- Admin user (roleid = 1)
INSERT INTO dbo.Users (id, email, username, fullname, password, avatar, roleid, phone, createdDate) VALUES
(100001, 'thang@example.com', 'thang', 'Huynh Ngoc Thang', '123456', NULL, 1, '0123456789', GETDATE());

-- User (roleid = 2)  
INSERT INTO dbo.Users (id, email, username, fullname, password, avatar, roleid, phone, createdDate) VALUES
(100002, 'jack@example.com', 'jack', 'Jack User', '123456', NULL, 2, '0987654321', GETDATE());

-- Sample categories
INSERT INTO dbo.Category (categoryName, images) VALUES
(N'Điện tử', NULL),
(N'Thời trang', NULL),
(N'Thể thao', NULL),
(N'Gia dụng', NULL);

-- Sample products
INSERT INTO dbo.Product (productName, description, image, price, purchases, stock, category_id) VALUES
(N'iPhone 15 Pro Max', N'Điện thoại thông minh cao cấp với camera 48MP', NULL, 29990000, 25, 50, 1),
(N'Áo thun nam cao cấp', N'Áo thun cotton 100% thoáng mát', NULL, 299000, 150, 200, 2),
(N'Giày thể thao Nike Air Max', N'Giày chạy bộ với công nghệ Air Max', NULL, 2500000, 80, 30, 3),
(N'Máy lọc nước RO', N'Máy lọc nước công nghệ RO 5 cấp', NULL, 3500000, 45, 15, 4),
(N'Laptop Dell XPS 13', N'Laptop cao cấp với màn hình 4K', NULL, 25000000, 12, 8, 1),
(N'Quần jean nữ', N'Quần jean skinny chất liệu cao cấp', NULL, 450000, 95, 60, 2),
(N'Bóng đá Adidas', N'Bóng đá chính hãng Adidas', NULL, 350000, 200, 100, 3),
(N'Nồi cơm điện tử', N'Nồi cơm điện tử 1.8L công nghệ mới', NULL, 1200000, 35, 25, 4);

