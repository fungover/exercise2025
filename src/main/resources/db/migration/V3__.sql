CREATE TABLE language
(
    id            INT AUTO_INCREMENT NOT NULL,
    text_language VARCHAR(255) NULL,
    CONSTRAINT pk_language PRIMARY KEY (id)
);

ALTER TABLE book
    ADD author_id INT NULL;

ALTER TABLE book
    ADD language_id INT NULL;

ALTER TABLE book
    ADD CONSTRAINT FK_BOOK_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES author (id);

ALTER TABLE book
    ADD CONSTRAINT FK_BOOK_ON_LANGUAGE FOREIGN KEY (language_id) REFERENCES language (id);

ALTER TABLE book
DROP
COLUMN author;