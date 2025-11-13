-- Create patrol table
CREATE TABLE patrol (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        district VARCHAR(100),
                        contact_person VARCHAR(255),
                        contact_email VARCHAR(255)
);

-- Create allergen table
CREATE TABLE allergen (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) UNIQUE NOT NULL,
                          description VARCHAR(1000),
                          severity VARCHAR(20)
);

-- Create event table
CREATE TABLE event (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       start_date DATE NOT NULL,
                       end_date DATE NOT NULL,
                       location VARCHAR(255),
                       max_participants INT
);

-- Create participant table
CREATE TABLE participant (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,