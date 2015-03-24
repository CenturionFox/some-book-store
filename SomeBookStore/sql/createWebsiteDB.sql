--PostGreSQL

DROP TABLE IF EXISTS member 	 CASCADE;
DROP TABLE IF EXISTS address	 CASCADE;
DROP TABLE IF EXISTS credit_card CASCADE;
DROP TABLE IF EXISTS orders 	 CASCADE;
DROP TABLE IF EXISTS order_item	 CASCADE;
DROP TABLE IF EXISTS book		 CASCADE;
DROP TABLE IF EXISTS title		 CASCADE;
DROP TABLE IF EXISTS author		 CASCADE;

DROP DOMAIN IF EXISTS email_domain  	CASCADE;
DROP DOMAIN IF EXISTS zip_domain		CASCADE;
DROP DOMAIN IF EXISTS isbn_dom			CASCADE;
DROP DOMAIN IF EXISTS credit_card_num	CASCADE;

--Email is defined in the style of "blah@blah.blah"
CREATE DOMAIN email_domain VARCHAR(128)
	CHECK(VALUE LIKE '%@%.%');
	
CREATE DOMAIN zip_domain CHAR(5)
	CHECK(VALUE BETWEEN '00000' AND '99999'
		AND VALUE LIKE '%%%%%');
		
CREATE DOMAIN isbn_dom CHAR(14)
	CHECK(VALUE BETWEEN '000-0000000000' AND '999-9999999999'
		AND VALUE LIKE '%%%-%%%%%%%%%%');
		
CREATE DOMAIN credit_card_num CHAR(19)
	CHECK(VALUE BETWEEN '0000-0000-0000-0000' AND '9999-9999-9999-9999'
		AND VALUE LIKE '%%%%-%%%%-%%%%-%%%%');

CREATE TABLE member (
	email		email_domain NOT NULL,
	password	TEXT NOT NULL,
	fname		VARCHAR(12) NOT NULL,
	minits		VARCHAR,
	lname		VARCHAR(12) NOT NULL,
	title		VARCHAR,
	PRIMARY KEY (email)
);

CREATE TABLE credit_card (
	email		email_domain NOT NULL,
	cardnum		credit_card_num NOT NULL,
	csvnum		CHAR(3) NOT NULL,
	PRIMARY KEY (cardnum),
	FOREIGN KEY (email) REFERENCES member(email) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE address (
	id			SERIAL,
	street_ln1	VARCHAR NOT NULL,
	street_ln2	VARCHAR,
	city		VARCHAR,
	zip_code	CHAR(5) NOT NULL,
	state_code	CHAR(2) NOT NULL,
	email		VARCHAR(32) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (email) REFERENCES member(email) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE orders (
	order_no		CHAR(8) NOT NULL,
	address			INTEGER,
	cardnum			credit_card_num,
	PRIMARY KEY (order_no),
	FOREIGN KEY (address) REFERENCES address(id) ON DELETE SET NULL,
	FOREIGN KEY (cardnum) REFERENCES credit_card(cardnum) ON DELETE SET NULL
);

CREATE TABLE author (
	id			CHAR(8) NOT NULL,
	fname		VARCHAR,
	minits		VARCHAR,
	lname		VARCHAR,
	title		VARCHAR,
	PRIMARY KEY (id)
);

CREATE TABLE title (
	title		VARCHAR NOT NULL,
	author_id	CHAR(8),
	isbn		isbn_dom NOT NULL,
	PRIMARY KEY (isbn),
	FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE SET NULL
);

CREATE TABLE book (
	isbn		isbn_dom NOT NULL,
	price		NUMERIC(10, 2),
	stock		INTEGER,
	description	TEXT,
	PRIMARY KEY (isbn),
	FOREIGN KEY (isbn) REFERENCES title(isbn) ON DELETE CASCADE
);

CREATE TABLE order_item (
	isbn		isbn_dom NOT NULL,
	order_no	CHAR(8) NOT NULL,
	quantity	INTEGER,
	FOREIGN KEY (isbn) REFERENCES book(isbn) ON DELETE CASCADE,
	FOREIGN KEY (order_no) REFERENCES orders(order_no) ON DELETE CASCADE,
	PRIMARY KEY (isbn, order_no)
);

INSERT INTO member ( email, password, fname, lname )
	VALUES ( 'maskreybe@live.com', '123456', 'Bridger', 'Maskrey' );

INSERT INTO address ( street_ln1, city, zip_code, state_code, email )
	VALUES ( '293 Bonnybrook Road', 'Carlisle', '17015', 'PA', 'maskreybe@live.com' );
	
INSERT INTO author ( id, fname, minits, lname )
	VALUES ( '00000000', 'J.', 'K.', 'Rowling' );
	
INSERT INTO title VALUES ( 'Harry Potter and the Goblet of Fire', '00000000', '978-0439139595' );

INSERT INTO book VALUES ( '978-0439139595', '21.99', 12, '(Hardcover)' );

INSERT INTO author ( id, fname, lname )
	VALUES ( '00000001', 'Lois', 'Lowry' );
	
INSERT INTO title VALUES ( 'The Giver', '00000001', '978-0553473599');

INSERT INTO book VALUES ( '978-0553473599', '18.49', 8, '(Paperback)' );

INSERT INTO address (street_ln1, street_ln2, city, zip_code, state_code, email)
	VALUES ( '1111 McKee Road', 'PTI Box #857', 'Oakdale', '15071', 'PA', 'maskreybe@live.com' );

INSERT INTO member ( email, password, fname, lname )
	VALUES ( 'maskrey@comcast.net', '123456', 'Patricia', 'Maskrey');
	
INSERT INTO address ( street_ln1, city, zip_code, state_code, email )
	VALUES ( '293 Bonnybrook Road', 'Carlisle', '17015', 'PA', 'maskrey@comcast.net');
	
INSERT INTO author ( id, fname, lname )
	VALUES ( '00000002', 'William', 'Goulding' );

INSERT INTO title VALUES ( 'Lord of the Flies', '00000002', '978-0571056866' );
	
INSERT INTO book VALUES ( '978-0571056866', '12.99', 5, '(Paperback)');

INSERT INTO title VALUES ( 'Harry Potter and the Chamber of Secrets', '00000000', '978-0747560722');

INSERT INTO book VALUES ( '978-0747560722', '21.49', 0, '(Hardcover)');

INSERT INTO author (id, fname, lname)
	VALUES ( '00000003', 'Michio', 'Kaku');
	
INSERT INTO title VALUES ('Hyperspace: A Scientific Odyssey Through Parallel Universes, Time Warps, and the 10th Dimension', '00000003', '978-0385477055');

INSERT INTO book VALUES ('978-0385477055', '12.99', 7, '(Paperback)');

INSERT INTO credit_card VALUES ( 'maskrey@comcast.net', '4700-1234-5678-9000', '000' );

INSERT INTO credit_card VALUES ( 'maskreybe@live.com', '1234-5678-9101-1121', '314');

INSERT INTO orders VALUES ('00000000', 1, '1234-5678-9101-1121');
INSERT INTO order_item VALUES ('978-0747560722', '00000000', 1);
/*
CREATE TABLE news (
	article_title	VARCHAR NOT NULL,
	article_body	TEXT,
	article_date	DATE NOT NULL,
	PRIMARY KEY(article_title, article_date)
);
*/
