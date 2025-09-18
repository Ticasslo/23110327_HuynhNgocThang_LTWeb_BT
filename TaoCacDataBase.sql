-- TaoCacDataBase.sql
-- SQL Server DDL for JPA project
-- Database: baitapWeb

-- Create database if not exists
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'baitapWeb')
BEGIN
    CREATE DATABASE baitapWeb;
END
GO

USE baitapWeb;
GO

-- Drop tables
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
    id INT NOT NULL,                           -- Manual ID (dùng RandomUtils)
    email NVARCHAR(255) NOT NULL,
    username NVARCHAR(100) NOT NULL,
    fullname NVARCHAR(255) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    avatar NVARCHAR(500) NULL,
    roleid INT NOT NULL DEFAULT 5,             -- Default là User role
    phone NVARCHAR(20) NULL,
    createdDate DATETIME DEFAULT GETDATE(),
    
    -- Constraints
    CONSTRAINT PK_Users PRIMARY KEY (id),
    CONSTRAINT UQ_Users_Email UNIQUE (email),
    CONSTRAINT UQ_Users_Username UNIQUE (username),
    CONSTRAINT CK_Users_Roleid CHECK (roleid IN (1, 2, 5))  -- Chỉ cho phép Admin, Manager, User
);

CREATE TABLE category (
    cate_id INT IDENTITY(1,1) NOT NULL,
    cate_name NVARCHAR(255) NOT NULL,
    icons NVARCHAR(500) NULL,
    userid INT NULL,                           -- ID của người tạo category
    created_date DATETIME DEFAULT GETDATE(),
    
    -- Constraints
    CONSTRAINT PK_Category PRIMARY KEY (cate_id),
    CONSTRAINT FK_category_userid FOREIGN KEY (userid) REFERENCES Users(id)
);