CREATE TABLE auth.note
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_id    BIGINT                NULL,
    value      VARCHAR(255)          NULL,
    created_at datetime              NULL,
    CONSTRAINT pk_note PRIMARY KEY (id)
);

CREATE TABLE auth.user
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255)          NULL,
    password VARCHAR(255)          NULL,
    email    VARCHAR(255)          NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE auth.user_api
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    user_id      BIGINT                NOT NULL,
    api_key      VARCHAR(255)          NOT NULL,
    counter      BIGINT                NOT NULL,
    last_used_at datetime              NULL,
    CONSTRAINT pk_user_api PRIMARY KEY (id)
);

ALTER TABLE auth.user_api
    ADD CONSTRAINT uc_user_api_apikey UNIQUE (api_key);

ALTER TABLE auth.user_api
    ADD CONSTRAINT uc_user_api_user UNIQUE (user_id);

ALTER TABLE auth.note
    ADD CONSTRAINT FK_NOTE_ON_USER FOREIGN KEY (user_id) REFERENCES auth.user (id);

ALTER TABLE auth.user_api
    ADD CONSTRAINT FK_USER_API_ON_USER FOREIGN KEY (user_id) REFERENCES auth.user (id);