-- Bước 1: Khởi tạo database và user cho SQL Server
-- Tạo database baitapWeb4
CREATE DATABASE baitapWeb4;
GO

-- Sử dụng database vừa tạo
USE baitapWeb4;
GO

-- Tạo bảng UserInfo
CREATE TABLE UserInfo (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    roles NVARCHAR(50) NOT NULL
);
GO

-- Insert dữ liệu mẫu
INSERT INTO UserInfo (name, email, password, roles) VALUES 
('thang', 'thang@student.hcmute.edu.vn', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ADMIN'),
('user', 'user@student.hcmute.edu.vn', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'USER');
GO

-- Kiểm tra dữ liệu
SELECT * FROM UserInfo;
GO
