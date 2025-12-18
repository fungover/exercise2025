insert into patient (date_of_birth, address, last_name, first_name, ssn)
values ('1978-12-03', 'Rome', 'Vance', 'Liora', '19781203-8912'),
        ('2001-07-13', 'Florence', 'Holt', 'Cassian', '20010713-9721'),
        ('1997-06-13', 'Venice', 'Mendel', 'Tarek', '19970613-1397');

insert into admission (pat_id, diagnosis, department)
values (1, 'Heart Thing', 'HIA'),
       (2, 'Pneumonia', 'Infection'),
       (3, 'Corona', 'Emergency');
