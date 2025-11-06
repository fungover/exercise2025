CREATE TABLE users
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    user_name VARCHAR(255) NULL,
    password  VARCHAR(255) NULL,
    `role`    VARCHAR(255) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);