CREATE TABLE recipe
(
    id           INT AUTO_INCREMENT NOT NULL,
    title        VARCHAR(255) NOT NULL,
    instructions LONGTEXT     NOT NULL,
    CONSTRAINT pk_recipe PRIMARY KEY (id)
);

CREATE TABLE recipe_item
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    name      VARCHAR(255) NOT NULL,
    amount DOUBLE NOT NULL,
    unit      VARCHAR(255) NOT NULL,
    recipe_id INT          NOT NULL,
    CONSTRAINT pk_recipeitem PRIMARY KEY (id)
);

ALTER TABLE recipe_item
    ADD CONSTRAINT FK_RECIPEITEM_ON_RECIPE FOREIGN KEY (recipe_id) REFERENCES recipe (id);