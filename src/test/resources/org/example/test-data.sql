INSERT INTO deck (brand, board_width)
VALUES ("BAKER", 8.5);

INSERT INTO truck_brand (brand)
VALUES ("ACE");

INSERT INTO truck_size (size, board_width, brand_id)
VALUES (55, 8.5, (SELECT id FROM truck_brand WHERE brand = "ACE" LIMIT 1));