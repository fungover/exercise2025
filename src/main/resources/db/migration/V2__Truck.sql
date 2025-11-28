CREATE TABLE truck_brand
(
    id            INT AUTO_INCREMENT NOT NULL,
    brand         VARCHAR(255)       NULL,
    created_at    datetime           NULL,
    CONSTRAINT pk_truck_brand PRIMARY KEY (id)
);

CREATE TABLE truck_size
(
    id            INT AUTO_INCREMENT NOT NULL,
    size          INT                NOT NULL,
    board_width   DOUBLE PRECISION   NOT NULL,
    brand_id      INT                NOT NULL,
    created_at    datetime           NULL,
    CONSTRAINT pk_truck_size PRIMARY KEY (id),
    CONSTRAINT fk_brand_id FOREIGN KEY (brand_id) REFERENCES truck_brand (id)
);