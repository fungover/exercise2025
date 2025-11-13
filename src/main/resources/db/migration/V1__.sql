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
    first_name    VARCHAR(255)          NOT NULL,
    last_name     VARCHAR(255)          NOT NULL,
    address       VARCHAR(255)          NOT NULL,
    date_of_birth date                  NOT NULL,
    ssn           VARCHAR(255)          NULL,
    CONSTRAINT pk_patient PRIMARY KEY (pat_id)
);

ALTER TABLE admission
    ADD CONSTRAINT FK_ADMISSION_ON_PAT FOREIGN KEY (pat_id) REFERENCES patient (pat_id);