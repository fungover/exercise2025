ALTER TABLE users
    MODIFY user_name VARCHAR(255) NOT NULL,
    MODIFY password VARCHAR(255) NOT NULL,
    MODIFY `role` VARCHAR(255) NOT NULL;

ALTER TABLE users
    ADD CONSTRAINT uk_users_username UNIQUE (user_name);
