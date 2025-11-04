CREATE TABLE pet
(
    id         INT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)       NULL,
    type       VARCHAR(255)       NULL,
    created_at datetime           NULL,
    CONSTRAINT pk_pet PRIMARY KEY (id)
);