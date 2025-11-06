ALTER TABLE auth.user_api
    ADD last_used_at datetime NULL;

ALTER TABLE auth.user_api
    DROP COLUMN submitted_at;