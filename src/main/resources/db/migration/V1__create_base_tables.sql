-- Create patrol table
CREATE TABLE patrol
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    district       VARCHAR(100),
    contact_person VARCHAR(255),
    contact_email  VARCHAR(255)
);

-- Create allergen table
CREATE TABLE allergen
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) UNIQUE NOT NULL
);

-- Create event table
CREATE TABLE event
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    start_date       DATE         NOT NULL,
    end_date         DATE         NOT NULL,
    location         VARCHAR(255),
    max_participants INT
);

-- Create participant table
CREATE TABLE participant
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100)        NOT NULL,
    last_name  VARCHAR(100)        NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    phone      VARCHAR(20),
    role_group VARCHAR(255),
    patrol_id  BIGINT,
    FOREIGN KEY (patrol_id) REFERENCES patrol (id)
);

-- Create registration table
CREATE TABLE registration
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id          BIGINT NOT NULL,
    participant_id    BIGINT NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status            VARCHAR(20) DEFAULT 'PENDING',
    special_requests  VARCHAR(1000),
    FOREIGN KEY (event_id) REFERENCES event (id),
    FOREIGN KEY (participant_id) REFERENCES participant (id),
    UNIQUE KEY unique_registration (event_id, participant_id)
);

-- Create participant_allergen join table
CREATE TABLE participant_allergen
(
    participant_id BIGINT NOT NULL,
    allergen_id    BIGINT NOT NULL,
    PRIMARY KEY (participant_id, allergen_id),
    FOREIGN KEY (participant_id) REFERENCES participant (id) ON DELETE CASCADE,
    FOREIGN KEY (allergen_id) REFERENCES allergen (id) ON DELETE CASCADE
);