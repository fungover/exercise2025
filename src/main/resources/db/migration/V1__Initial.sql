CREATE TABLE cat
(
    id         INT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)       NULL,
    age        INT                NOT NULL,
    created_at datetime           NULL,
    CONSTRAINT pk_cat PRIMARY KEY (id)
);