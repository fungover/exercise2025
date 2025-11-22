USE laboration1;

-- Uppgift 1
-- Skapa en ny tabell "successful_mission" med alla kolumner från moon_mission
-- där utfallet var "Successful outcome"

--  CREATE TABLE successful_mission AS
--  SELECT *
--  FROM moon_mission
--  WHERE outcome = 'Successful';

-- Kontrollera resultatet
-- SELECT * FROM successful_mission;

-- Uppgift 2
-- Gör mission_id till primärnyckel och auto-increment i befintlig tabell

-- ALTER TABLE successful_mission
--    MODIFY COLUMN mission_id SMALLINT AUTO_INCREMENT PRIMARY KEY;



-- Uppgift 3
-- Tar bort alla mellanslag i kolumnen 'operator' i tabellen moon_mission
-- UPDATE moon_mission
-- SET operator = REPLACE(operator, ' ', '');

-- Tar bort alla mellanslag i kolumnen 'operator' i tabellen successful_mission
-- UPDATE successful_mission
-- SET operator = REPLACE(operator, ' ', '');

-- Uppgift 4
-- Ta bort alla uppdrag utförda 2010 eller senare från successful_mission
-- DELETE FROM successful_mission
-- WHERE YEAR(launch_date) >= 2010;

-- Kontrollera resultatet
-- SELECT * FROM successful_mission;

-- Uppgift 5
-- Slår ihop first_name och last_name till name
-- och lägger till kolumnen 'gender' baserat på näst sista siffran i ssn
-- (jämn = female, udda = male)

-- SELECT *,
--       CONCAT_WS(' ', first_name, last_name) AS name,
--       CASE
--           WHEN MOD(
--                        SUBSTRING(REPLACE(ssn, '-', ''), LENGTH(REPLACE(ssn, '-', '')) - 1, 1) + 0,
--                        2
--                ) = 0 THEN 'female'
--           ELSE 'male'
--           END AS gender
-- FROM account;

-- Uppgift 6
-- Ta bort alla kvinnor (jämn näst sista siffra i ssn)
-- som är födda före 1970

-- DELETE FROM account
-- WHERE CAST(SUBSTRING(REPLACE(ssn, '-', ''), 1, 4) AS UNSIGNED) < 1970
--  AND MOD(
--              SUBSTRING(REPLACE(ssn, '-', ''), LENGTH(REPLACE(ssn, '-', '')) - 1, 1) + 0,
--              2
--      ) = 0;

-- Kontrollera resultatet
-- SELECT * FROM account;

-- Uppgift 7
-- Returnerar två kolumner: gender och average_age
-- Beräknar kön via näst sista siffran i ssn (jämn=female, udda=male)
-- och medelålder via de fyra första siffrorna (YYYY) i ssn.

-- SELECT
--    CASE
--        WHEN MOD(
--                     SUBSTRING(REPLACE(ssn, '-', ''), LENGTH(REPLACE(ssn, '-', '')) - 1, 1) + 0,
--                     2
--             ) = 0 THEN 'female'
--        ELSE 'male'
--        END AS gender,
--    ROUND(AVG(YEAR(CURDATE()) - CAST(SUBSTRING(REPLACE(ssn, '-', ''), 1, 4) AS UNSIGNED)), 1) AS average_age
-- FROM account
-- GROUP BY gender;



/*
   BOOK STORE – skapa db, tabeller, data, vy, användare
   Kör allt stegvis. Byt ev. lösenord innan skarp drift.
   */

/*-- 1) Skapa om databasen
DROP DATABASE IF EXISTS bookstore;
CREATE DATABASE bookstore
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_0900_ai_ci;
USE bookstore;


-- 2) Författare
CREATE TABLE author (
                        id          INT AUTO_INCREMENT PRIMARY KEY,
                        first_name  VARCHAR(100) NOT NULL,
                        last_name   VARCHAR(100) NOT NULL,
                        birth_date  DATE         NOT NULL
);

-- 3) Böcker (ISBN13 som PK)
CREATE TABLE book (
                      isbn              CHAR(13)      NOT NULL,
                      title             VARCHAR(255)  NOT NULL,
                      language_id       INT           NULL,
                      price             DECIMAL(10,2) NOT NULL CHECK (price >= 0),
                      publication_date  DATE          NOT NULL,
                      author_id         INT           NOT NULL,
                      CONSTRAINT pk_book PRIMARY KEY (isbn),
                      CONSTRAINT ck_isbn13_digits CHECK (isbn REGEXP '^[0-9]{13}$'),
                      CONSTRAINT fk_book_author   FOREIGN KEY (author_id)   REFERENCES author(id)
                          ON UPDATE CASCADE ON DELETE RESTRICT,
                      CONSTRAINT fk_book_language FOREIGN KEY (language_id) REFERENCES language(id)
                          ON UPDATE CASCADE ON DELETE SET NULL
);

-- 4) Butiker
CREATE TABLE bookstore (
                           id   INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(150) NOT NULL,
                           city VARCHAR(100) NOT NULL
);

-- 5) Lager (komposit-PK)
CREATE TABLE inventory (
                           store_id INT      NOT NULL,
                           isbn     CHAR(13) NOT NULL,
                           amount   INT      NOT NULL CHECK (amount >= 0),
                           CONSTRAINT pk_inventory PRIMARY KEY (store_id, isbn),
                           CONSTRAINT fk_inventory_store FOREIGN KEY (store_id) REFERENCES bookstore(id)
                               ON UPDATE CASCADE ON DELETE CASCADE,
                           CONSTRAINT fk_inventory_book  FOREIGN KEY (isbn)     REFERENCES book(isbn)
                               ON UPDATE CASCADE ON DELETE CASCADE
);

-- 6) Exempeldata
INSERT INTO language (code, name) VALUES
                                      ('en', 'English'),
                                      ('sv', 'Swedish');

INSERT INTO author (first_name, last_name, birth_date) VALUES
                                                           ('Eddie', 'Neumann', DATE_SUB(CURDATE(), INTERVAL 23 YEAR)),
                                                           ('Astrid', 'Lindgren', '1907-11-14'),
                                                           ('Haruki', 'Murakami', '1949-01-12');

INSERT INTO book (isbn, title, language_id, price, publication_date, author_id) VALUES
                                                                                    ('9780000000001', 'Eddies första bok', 2, 199.00, '2023-09-01', 1),
                                                                                    ('9780000000002', 'Eddies andra bok',  2, 149.00, '2024-02-15', 1),
                                                                                    ('9780000000003', 'Eddies tredje bok', 2,  99.00, '2025-05-10', 1),
                                                                                    ('9789129657541', 'Pippi Långstrump',  2, 129.00, '1945-11-24', 2),
                                                                                    ('9780099448761', 'Norwegian Wood',    1, 189.00, '1987-09-04', 3);

INSERT INTO bookstore (name, city) VALUES
                                       ('CityBooks', 'Stockholm'),
                                       ('LitCorner', 'Göteborg');

INSERT INTO inventory (store_id, isbn, amount) VALUES
                                                   (1, '9780000000001', 6),
                                                   (1, '9780000000002', 4),
                                                   (1, '9780000000003', 8),
                                                   (1, '9789129657541', 5),
                                                   (2, '9780000000001', 2),
                                                   (2, '9780000000003', 3),
                                                   (2, '9780099448761', 7);

-- 7) Vy: total_author_book_value
CREATE OR REPLACE VIEW total_author_book_value AS
SELECT
    CONCAT_WS(' ', a.first_name, a.last_name)                               AS name,
    CONCAT(TIMESTAMPDIFF(YEAR, a.birth_date, CURDATE()), ' år')             AS age,
    CONCAT(COUNT(DISTINCT b.isbn), ' st')                                   AS book_title_count,
    CONCAT(COALESCE(ROUND(SUM(b.price * i.amount), 0), 0), ' kr')           AS inventory_value
FROM author a
         LEFT JOIN book b      ON b.author_id = a.id
         LEFT JOIN inventory i ON i.isbn = b.isbn
GROUP BY a.id, a.first_name, a.last_name, a.birth_date;

-- -- Testa vyn:
-- SELECT * FROM total_author_book_value;

-- 8) Användare & behörigheter (BYT lösen i skarp miljö)
DROP USER IF EXISTS 'book_dev'@'%';
DROP USER IF EXISTS 'book_app'@'%';

CREATE USER 'book_dev'@'%' IDENTIFIED BY 'DevL0sen!';
CREATE USER 'book_app'@'%' IDENTIFIED BY 'AppL0sen!';

-- Utvecklare: schema + data på just denna databas
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER, CREATE VIEW, SHOW VIEW, TRIGGER
    ON bookstore.* TO 'book_dev'@'%';

-- Appkonto: endast CRUD på data
GRANT SELECT, INSERT, UPDATE, DELETE
    ON bookstore.* TO 'book_app'@'%';

FLUSH PRIVILEGES;
*/