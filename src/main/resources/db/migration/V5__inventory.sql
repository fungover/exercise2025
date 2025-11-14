CREATE TABLE inventory
(
    amount   INT NOT NULL,
    book_id  INT NOT NULL,
    store_id INT NOT NULL,
    CONSTRAINT pk_inventory PRIMARY KEY (book_id, store_id)
);