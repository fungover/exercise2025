CREATE TABLE pet
(
    id        INT AUTO_INCREMENT NOT NULL,
    name      VARCHAR(255) NULL,
    species   VARCHAR(255) NULL,
    hunger    INT NOT NULL,
    happiness INT NOT NULL,
    CONSTRAINT pk_pet PRIMARY KEY (id)
);