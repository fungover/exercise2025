CREATE TABLE users
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    user_name VARCHAR(255) NULL,
    password  VARCHAR(255) NULL,
    `role`    VARCHAR(255) NULL,
    must_change_password BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT pk_users PRIMARY KEY (id)
);