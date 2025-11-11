ALTER TABLE recipe
    ADD CONSTRAINT uq_recipe_title UNIQUE (title);