-- TaoCacDataBase_SpringBoot.sql
-- SQL Server DDL for Spring Boot project (simple DROP/CREATE)
-- Database: baitapWeb2

-- Create database if not exists
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'baitapWeb2')
BEGIN
    CREATE DATABASE baitapWeb2;
END
GO

USE baitapWeb2;
GO

-- Drop tables if exist (respect FK order)
IF OBJECT_ID('dbo.Video', 'U') IS NOT NULL DROP TABLE dbo.Video;
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

-- Video table
CREATE TABLE dbo.Video (
    video_id INT IDENTITY(1,1) NOT NULL,
    title NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX) NULL,
    poster NVARCHAR(500) NULL,
    views BIGINT NOT NULL DEFAULT 0,
    category_id INT NOT NULL,
    CONSTRAINT PK_Video PRIMARY KEY (video_id),
    CONSTRAINT FK_Video_Category FOREIGN KEY (category_id) REFERENCES dbo.Category(category_id)
);

CREATE INDEX IX_Video_title ON dbo.Video(title);
CREATE INDEX IX_Video_category_id ON dbo.Video(category_id);

-- Insert sample data
-- Admin user (roleid = 1)
INSERT INTO dbo.Users (id, email, username, fullname, password, avatar, roleid, phone, createdDate) VALUES
(100001, 'thang@example.com', 'thang', 'Huynh Ngoc Thang', '123456', NULL, 1, '0123456789', GETDATE());

-- User (roleid = 2)  
INSERT INTO dbo.Users (id, email, username, fullname, password, avatar, roleid, phone, createdDate) VALUES
(100002, 'jack@example.com', 'jack', 'Jack User', '123456', NULL, 2, '0987654321', GETDATE());

-- Sample categories
INSERT INTO dbo.Category (categoryName, images) VALUES
(N'Giáo dục', NULL),
(N'Giải trí', NULL),
(N'Thể thao', NULL),
(N'Âm nhạc', NULL);

-- Sample videos
INSERT INTO dbo.Video (title, description, poster, views, category_id) VALUES
(N'Video học Spring Boot', N'Hướng dẫn cơ bản về Spring Boot', N'bi-play-circle', 150, 1),
(N'Video giải trí vui nhộn', N'Những video hài hước nhất', N'bi-emoji-laughing', 300, 2),
(N'Video bóng đá hay', N'Highlight bóng đá tuần này', N'bi-trophy', 500, 3),
(N'Video nhạc mới', N'Bài hát hot nhất hiện tại', N'bi-music-note', 200, 4);

