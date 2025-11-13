CREATE TABLE movie
(
    id              INT AUTO_INCREMENT NOT NULL,
    title           VARCHAR(255)       NULL,
    genre           VARCHAR(255)       NULL,
    `description`   VARCHAR(255)       NULL,
    year            INT                NOT NULL,
    runtime_minutes INT                NOT NULL,
    created_at      datetime           NULL,
    updated_at      datetime           NULL,
    CONSTRAINT pk_movie PRIMARY KEY (id)
);