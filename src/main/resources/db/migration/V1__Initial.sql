CREATE TABLE pet
(
    id           INT NOT NULL,
    name         VARCHAR(255) NULL,
    age          INT NOT NULL,
    species      VARCHAR(255) NULL,
    hunger_level INT NOT NULL,
    happiness    INT NOT NULL,
    created_at   datetime NULL,
    CONSTRAINT pk_pet PRIMARY KEY (id)
);