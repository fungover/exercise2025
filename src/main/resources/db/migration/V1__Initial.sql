CREATE TABLE deck
(
    id            INT AUTO_INCREMENT NOT NULL,
    brand         VARCHAR(255)       NULL,
    board_width   DOUBLE PRECISION   NOT NULL,
    created_at    datetime           NULL,
    updated_at    datetime           NULL,
    CONSTRAINT pk_deck PRIMARY KEY (id)
);