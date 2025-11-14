CREATE TABLE book
(
    book_id     INT AUTO_INCREMENT NOT NULL,
    title       VARCHAR(255) NULL,
    genre       VARCHAR(255) NULL,
    rating      INT          NOT NULL,
    language_id INT          NULL,
    author_id   INT          NULL,
    CONSTRAINT pk_book PRIMARY KEY (book_id)
);