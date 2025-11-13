CREATE TABLE note
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_id    BIGINT                NULL,
    value      VARCHAR(255)          NULL,
    created_at datetime              NULL,
    deleted_at datetime              NULL,
    CONSTRAINT pk_note PRIMARY KEY (id)
);

CREATE TABLE token
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    user_id       BIGINT                NOT NULL,
    token         VARCHAR(255)          NOT NULL,
    refresh_token VARCHAR(255)          NOT NULL,
    counter       BIGINT                NOT NULL,
    last_used_at  datetime              NULL,
    CONSTRAINT pk_token PRIMARY KEY (id)
);

CREATE TABLE user
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255)          NULL,
    password VARCHAR(255)          NULL,
    email    VARCHAR(255)          NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE token
    ADD CONSTRAINT uc_token_refreshtoken UNIQUE (refresh_token);

ALTER TABLE token
    ADD CONSTRAINT uc_token_token UNIQUE (token);

ALTER TABLE token
    ADD CONSTRAINT uc_token_user UNIQUE (user_id);

ALTER TABLE note
    ADD CONSTRAINT FK_NOTE_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE token
    ADD CONSTRAINT FK_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);