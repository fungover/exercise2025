CREATE TABLE note
(
    id         BIGINT       NOT NULL,
    user_id    BIGINT       NULL,
    value      VARCHAR(255) NULL,
    created_at datetime     NULL,
    CONSTRAINT pk_note PRIMARY KEY (id)
);

CREATE TABLE user
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    name            VARCHAR(255)          NULL,
    password_hashed VARCHAR(255)          NULL,
    email           VARCHAR(255)          NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE note
    ADD CONSTRAINT FK_NOTE_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);