IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'BookWeb')
BEGIN
	CREATE DATABASE BookWeb;
END
GO

USE BookWeb;
GO

IF OBJECT_ID('rating', 'U') IS NOT NULL DROP TABLE rating;
IF OBJECT_ID('book_author', 'U') IS NOT NULL DROP TABLE book_author;
IF OBJECT_ID('books', 'U') IS NOT NULL DROP TABLE books;
IF OBJECT_ID('author', 'U') IS NOT NULL DROP TABLE author;
IF OBJECT_ID('users', 'U') IS NOT NULL DROP TABLE users;
GO

CREATE TABLE users (
	id			INT IDENTITY(1,1) PRIMARY KEY,
	email		VARCHAR(50) NOT NULL UNIQUE,
	fullname	NVARCHAR(50) NULL,
	phone		INT NULL,
	passwd		VARCHAR(32) NOT NULL,
	signup_date	DATETIME NULL DEFAULT GETDATE(),
	last_login	DATETIME NULL,
	is_admin	BIT NOT NULL DEFAULT 0
);
GO

CREATE TABLE author (
	author_id	INT IDENTITY(1,1) PRIMARY KEY,
	author_name	VARCHAR(100) NOT NULL,
	date_of_birth	DATE NULL
);
GO

CREATE TABLE books (
	bookid		INT IDENTITY(1,1) PRIMARY KEY,
	isbn		BIGINT NULL,
	title		VARCHAR(200) NOT NULL,
	publisher	VARCHAR(100) NULL,
	price		DECIMAL(6,2) NULL,
	description	TEXT NULL,
	publish_date	DATE NULL,
	cover_image	VARCHAR(100) NULL,
	quantity	INT NULL
);
GO

CREATE TABLE book_author (
	bookid		INT NOT NULL,
	author_id	INT NOT NULL,
	CONSTRAINT PK_book_author PRIMARY KEY (bookid, author_id),
	CONSTRAINT FK_book_author_books FOREIGN KEY (bookid) REFERENCES books(bookid),
	CONSTRAINT FK_book_author_author FOREIGN KEY (author_id) REFERENCES author(author_id)
);
GO

CREATE TABLE rating (
	userid		INT NOT NULL,
	bookid		INT NOT NULL,
	rating		TINYINT NULL,
	review_text	TEXT NULL,
	CONSTRAINT PK_rating PRIMARY KEY (userid, bookid),
	CONSTRAINT FK_rating_users FOREIGN KEY (userid) REFERENCES users(id),
	CONSTRAINT FK_rating_books FOREIGN KEY (bookid) REFERENCES books(bookid)
);
GO


INSERT INTO users (email, fullname, phone, passwd, signup_date, last_login, is_admin)
VALUES
	('admin@bookweb.local', N'Admin Hệ Thống', 0909000001, '123456', GETDATE(), NULL, 1),
	('nguyenvana@bookweb.local', N'Nguyễn Văn A', 0909000002, '123456', GETDATE(), NULL, 0),
	('lethib@bookweb.local', N'Lê Thị B', 0909000003, '123456', GETDATE(), NULL, 0),
	('tranminhc@bookweb.local', N'Trần Minh C', 0909000004, '123456', GETDATE(), NULL, 0),
	('phamthid@bookweb.local', N'Phạm Thị D', 0909000005, '123456', GETDATE(), NULL, 0),
	('hoanganhe@bookweb.local', N'Hoàng Anh E', 0909000006, '123456', GETDATE(), NULL, 0),
	('dangquoct@bookweb.local', N'Đặng Quốc T', 0909000007, '123456', GETDATE(), NULL, 0),
	('buidieup@bookweb.local', N'Bùi Diệu P', 0909000008, '123456', GETDATE(), NULL, 0),
	('phungthiq@bookweb.local', N'Phùng Thị Q', 0909000009, '123456', GETDATE(), NULL, 0),
	('votrungr@bookweb.local', N'Võ Trung R', 0909000010, '123456', GETDATE(), NULL, 0);

INSERT INTO author (author_name, date_of_birth)
VALUES
	('J. K. Rowling', '1965-07-31'),
	('George R. R. Martin', '1948-09-20'),
	('Haruki Murakami', '1949-01-12'),
	('Dan Brown', '1964-06-22'),
	('Stephen King', '1947-09-21'),
	('Yuval Noah Harari', '1976-02-24'),
	('Paulo Coelho', '1947-08-24'),
	('Agatha Christie', '1890-09-15');

INSERT INTO books (isbn, title, publisher, price, description, publish_date, cover_image, quantity)
VALUES
	(9780747532743, 'Harry Potter and the Philosopher''s Stone', 'Bloomsbury', 199.00, N'Tập 1 Harry Potter', '1997-06-26', NULL, 100),
	(9780553103540, 'A Game of Thrones', 'Bantam Spectra', 249.00, N'Tập 1 A Song of Ice and Fire', '1996-08-06', NULL, 80),
	(9780099448761, 'Kafka on the Shore', 'Vintage', 189.00, N'Kafka bên bờ biển', '2002-09-12', NULL, 60),
	(9780385504201, 'The Da Vinci Code', 'Doubleday', 199.00, N'Tiểu thuyết trinh thám', '2003-03-18', NULL, 70),
	(9780385121675, 'The Shining', 'Doubleday', 179.00, N'Kinh dị cổ điển', '1977-01-28', NULL, 50),
	(9780062316097, 'Sapiens: A Brief History of Humankind', 'Harper', 299.00, N'Lịch sử nhân loại', '2011-01-01', NULL, 65),
	(9780062315007, 'The Alchemist', 'HarperOne', 129.00, N'Người luyện kim', '1988-04-14', NULL, 120),
	(9780007119318, 'Murder on the Orient Express', 'Collins Crime Club', 159.00, N'Truyện trinh thám', '1934-01-01', NULL, 40),
	(9780099448822, 'Norwegian Wood', 'Vintage', 159.00, N'Rừng Na Uy', '1987-09-04', NULL, 55),
	(9780747538486, 'Harry Potter and the Chamber of Secrets', 'Bloomsbury', 209.00, N'Tập 2 Harry Potter', '1998-07-02', NULL, 95);

INSERT INTO book_author (bookid, author_id)
VALUES
	(1, 1),
	(2, 2),
	(3, 3),
	(4, 4),
	(5, 5),
	(6, 6),
	(7, 7),
	(8, 8),
	(9, 3),
	(10, 1);

INSERT INTO rating (userid, bookid, rating, review_text)
VALUES
	(1, 1, 5, N'Rất hấp dẫn!'),
	(1, 2, 4, N'Đậm chất sử thi'),
	(2, 3, 5, N'Siêu thực và cuốn hút'),
	(1, 4, 4, N'Tiết tấu nhanh, nhiều bí ẩn'),
	(2, 5, 4, N'Lạnh gáy nhưng rất lôi cuốn'),
	(1, 6, 5, N'Rất nhiều kiến thức hay'),
	(2, 7, 5, N'Câu chuyện truyền cảm hứng'),
	(2, 10, 5, N'Tập 2 tuyệt vời');