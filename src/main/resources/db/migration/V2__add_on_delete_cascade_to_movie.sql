ALTER TABLE movie
 DROP FOREIGN KEY fk_movie_director;

ALTER TABLE movie
    ADD CONSTRAINT fk_movie_director FOREIGN KEY (director_id) REFERENCES director(id) ON DELETE CASCADE;