CREATE TABLE cat_favorites
(
    cat_id       INT    NOT NULL,
    favorites_id BIGINT NOT NULL
);

ALTER TABLE cat_favorites
    ADD CONSTRAINT uc_cat_favorites_favorites UNIQUE (favorites_id);

ALTER TABLE cat_favorites
    ADD CONSTRAINT fk_catfav_on_cat FOREIGN KEY (cat_id) REFERENCES cat (id);

ALTER TABLE cat_favorites
    ADD CONSTRAINT fk_catfav_on_food FOREIGN KEY (favorites_id) REFERENCES food (id);