-- Create pet table
CREATE TABLE pet
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(20) NOT NULL,
    species VARCHAR(20) NOT NULL,
    hunger_level INT NOT NULL DEFAULT 5 CHECK ( hunger_level >= 0 AND hunger_level <= 10 ),
    happiness_level INT NOT NULL DEFAULT 5 CHECK ( happiness_level >= 0 AND happiness_level <= 10 ),
    created_at DATETIME NOT NULL,
    CONSTRAINT pk_pet PRIMARY KEY (id)
);

-- Insert sample data
INSERT INTO pet (id, name, species, hunger_level, happiness_level, created_at) VALUES
(1, 'Rex', 'Dog', 6, 9, NOW()),
(2, 'Garfield', 'Cat', 10, 2, NOW()),
(3, 'Missy', 'Cat', 8, 5, NOW()),
(4, 'Quack', 'Bird', 1, 3, NOW()),
(5, 'Chewy', 'Hamster', 3, 5, NOW()),
(6, 'Leonardo', 'Turtle', 10, 4, NOW()),
(7, 'Ricky', 'Dolphin', 1, 7, NOW());