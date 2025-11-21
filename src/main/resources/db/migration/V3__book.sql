CREATE TABLE book
(
    book_id     INT AUTO_INCREMENT NOT NULL,
    title       VARCHAR(255) NOT NULL,
    genre       VARCHAR(255) NOT NULL,
    rating      INT NOT NULL,
    language_id INT NOT NULL,
    author_id   INT NOT NULL,
    CONSTRAINT pk_book PRIMARY KEY (book_id)
);