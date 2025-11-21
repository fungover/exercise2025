CREATE TABLE customized_user
(
    user_id   INT AUTO_INCREMENT NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_customizeduser PRIMARY KEY (user_id)
);

CREATE TABLE `role`
(
    role_id   INT AUTO_INCREMENT NOT NULL,
    authority VARCHAR(255) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (role_id)
);

CREATE TABLE user_roles
(
    roles_role_id INT NOT NULL,
    user_user_id  INT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (roles_role_id, user_user_id)
);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_customized_user FOREIGN KEY (user_user_id) REFERENCES customized_user (user_id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_role_id) REFERENCES `role` (role_id);