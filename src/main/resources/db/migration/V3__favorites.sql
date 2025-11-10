CREATE TABLE pet_favorite
(
    pet_id      INT    NOT NULL,
    favorite_id BIGINT NOT NULL
);

ALTER TABLE pet_favorite
    ADD CONSTRAINT uc_pet_favorite_favorite UNIQUE (favorite_id);

ALTER TABLE pet_favorite
    ADD CONSTRAINT fk_petfav_on_food FOREIGN KEY (favorite_id) REFERENCES food (id);

ALTER TABLE pet_favorite
    ADD CONSTRAINT fk_petfav_on_pet FOREIGN KEY (pet_id) REFERENCES pet (id);