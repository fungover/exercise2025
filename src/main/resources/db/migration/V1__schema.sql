-- Users
CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(200) NOT NULL,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Exercises (seedas i V2)
CREATE TABLE exercises (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(100) NOT NULL UNIQUE,
                           muscle_group VARCHAR(100) NULL
);

-- Personal Best (PR)
CREATE TABLE personal_bests (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                user_id BIGINT NOT NULL,
                                exercise_id BIGINT NOT NULL,
                                weight_kg DECIMAL(6,2) NOT NULL,
                                reps INT NOT NULL,
                                achieved_on DATE NOT NULL,
                                CONSTRAINT fk_pr_user FOREIGN KEY (user_id) REFERENCES users(id),
                                CONSTRAINT fk_pr_ex FOREIGN KEY (exercise_id) REFERENCES exercises(id),
                                CONSTRAINT uq_pr_user_ex_reps UNIQUE (user_id, exercise_id, reps)
);

CREATE INDEX idx_pr_user ON personal_bests(user_id);
CREATE INDEX idx_pr_ex ON personal_bests(exercise_id);
