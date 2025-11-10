-- Create director table
CREATE TABLE director
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL
);

-- Create movie table
CREATE TABLE movie
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    duration    BIGINT       NOT NULL,
    genre       VARCHAR(255) NOT NULL,
    director_id BIGINT       NOT NULL,
    CONSTRAINT fk_movie_director FOREIGN KEY (director_id) REFERENCES director (id)
);

-- Create user table
CREATE TABLE user
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    authorities VARCHAR(255) NOT NULL,
    api_key     VARCHAR(255)
);