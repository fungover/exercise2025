CREATE TABLE book
(
    id     INT AUTO_INCREMENT NOT NULL,
    title  VARCHAR(255) NULL,
    author VARCHAR(255) NULL,
    genre  SMALLINT NULL,
    rating INT NOT NULL,
    CONSTRAINT pk_book PRIMARY KEY (id)
);