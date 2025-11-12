CREATE TABLE admission
(
    admission_id BIGINT AUTO_INCREMENT NOT NULL,
    date_in      datetime              NULL,
    date_out     datetime              NULL,
    diagnosis    VARCHAR(255)          NULL,
    department   VARCHAR(255)          NULL,
    pat_id       BIGINT                NULL,
    CONSTRAINT pk_admission PRIMARY KEY (admission_id)
);

CREATE TABLE patient
(
    pat_id        BIGINT AUTO_INCREMENT NOT NULL,
    first_name    VARCHAR(255)          NULL,
    last_name     VARCHAR(255)          NULL,
    address       VARCHAR(255)          NULL,
    date_of_birth datetime              NULL,
    CONSTRAINT pk_patient PRIMARY KEY (pat_id)
);

ALTER TABLE admission
    ADD CONSTRAINT uc_admission_pat UNIQUE (pat_id);

ALTER TABLE admission
    ADD CONSTRAINT FK_ADMISSION_ON_PAT FOREIGN KEY (pat_id) REFERENCES patient (pat_id);