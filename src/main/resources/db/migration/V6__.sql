CREATE TABLE author
(
    id         INT AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(255)       NULL,
    last_name  VARCHAR(255)       NULL,
    CONSTRAINT pk_author PRIMARY KEY (id)
);

CREATE TABLE book
(
    book_id     INT AUTO_INCREMENT NOT NULL,
    title       VARCHAR(255) NULL,
    genre       SMALLINT     NULL,
    rating      INT          NOT NULL,
    language_id INT          NULL,
    author_id   INT          NULL,
    CONSTRAINT pk_book PRIMARY KEY (book_id)
);

CREATE TABLE inventory
(
    amount   INT NOT NULL,
    book_id  INT NOT NULL,
    store_id INT NOT NULL,
    CONSTRAINT pk_inventory PRIMARY KEY (book_id, store_id)
);

CREATE TABLE language
(
    id            INT AUTO_INCREMENT NOT NULL,
    text_language VARCHAR(255)       NULL,
    CONSTRAINT pk_language PRIMARY KEY (id)
);

CREATE TABLE store
(
    store_id   INT AUTO_INCREMENT NOT NULL,
    store_name VARCHAR(255) NULL,
    CONSTRAINT pk_store PRIMARY KEY (store_id)
);

ALTER TABLE book
    ADD CONSTRAINT FK_BOOK_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES author (id);

ALTER TABLE book
    ADD CONSTRAINT FK_BOOK_ON_LANGUAGE FOREIGN KEY (language_id) REFERENCES language (id);

ALTER TABLE inventory
    ADD CONSTRAINT FK_INVENTORY_ON_BOOK FOREIGN KEY (book_id) REFERENCES book (book_id);

ALTER TABLE inventory
    ADD CONSTRAINT FK_INVENTORY_ON_STORE FOREIGN KEY (store_id) REFERENCES store (store_id);