CREATE TABLE author
(
    id         INT AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    CONSTRAINT pk_author PRIMARY KEY (id)
);