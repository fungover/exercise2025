CREATE TABLE pet
(
    id         INT AUTO_INCREMENT NOT NULL,
    species    VARCHAR(255) NULL,
    name       VARCHAR(255) NULL,
    age        INT NOT NULL,
    created_at datetime NULL,
    CONSTRAINT pk_animal PRIMARY KEY (id)
);