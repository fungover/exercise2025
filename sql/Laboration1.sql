--
-- Skapar databasen och tabellerna för Laboration 1.
--
DROP DATABASE IF EXISTS laboration1;
CREATE DATABASE laboration1;
USE laboration1;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS moon_mission
;
CREATE TABLE account (user_id smallint PRIMARY KEY AUTO_INCREMENT,name VARCHAR(255),password VARCHAR(255),first_name VARCHAR(255),last_name VARCHAR(255),ssn VARCHAR(255));
CREATE TABLE moon_mission
(mission_id smallint PRIMARY KEY AUTO_INCREMENT,spacecraft VARCHAR(255),launch_date DATE,carrier_rocket VARCHAR(255),operator VARCHAR(255),mission_type VARCHAR(255),outcome VARCHAR(255));
--
-- Lägger till data i tabellerna för Laboration 1.
--
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('8j_]xrCfh#t5,vne', 'Alexandra', 'Truby', '930213-1480');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('g`:+W{\%H9UXqGU4', 'Adna', 'Sandberg', '760723-6085');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('4D3ss?-;MY)9S!y{', 'Daniela', 'Petterson', '810809-3405');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('/aszxcvg/d}/%6.P4w', 'Mauritz', 'Samuelsson', '620312-0834');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('hasdfgd}/.rz4w', 'André', 'Lundgren', '550117-2273');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('/Dzxdhg//%6.Prz4w', 'Nicole', 'Gustafsson', '590615-4603');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('>$U7P5C6xnXq!;gA', 'Hamza', 'Lundqvist', '430703-3730');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('MT4x3u6qL;$B2P?]', 'Ayla', 'Mattsson', '890714-4961');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('<MdXx;r-Y{vKx85,', 'Ninni', 'Blomqvist', '500918-6320');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('`MY_M4Sr*fNm<U`Z', 'Pamela', 'Blomqvist', '580306-6926');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('XQ*9&N7wZEr9S(u9', 'Christine', 'Jansson', '700609-2121');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('/YDqX2"mhBen$2H', 'Evelin', 'Söderberg', '490715-4266');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('KE{*3&@)s==:DAvr', 'Atle', 'Fransson', '511030-8755');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('JW?L^8m9f7c8m9_F', 'Peter', 'Sandström', '850513-0794');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('JW?L^8m9f7c8m9_F', 'Petter', 'Sandqvist', '820315-0631');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('%5M*BJK!34ubUm$E', 'Milan', 'Gustafsson', '411120-0095');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('#x=_K*GYu-^HqP6D', 'Isabell', 'Nordström', '621231-8544');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('xq#cJBvS5x*3MQ?j', 'Emely', 'Nilsson', '360303-0085');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('GAsE5pxxas$3t-sm', 'Kelvin', 'Åkesson', '550305-1657');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('t%8vBVV7LEGVG8^q', 'Nelly', 'Eriksson', '901108-2089');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('MB=V4cbAqPz4vqmQ', 'Angela', 'Fransson', '371108-9221');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('$63c-rf8LCJ$&b4g', 'Charles', 'Sjöberg', '550821-8590');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('5Uf6yD*aDr%8wV29', 'Neo', 'Blom', '600309-5178');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('dCx*R-hg2Q%Kf_y5', 'Rim', 'Hellström', '700718-4463');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('f2nq-yWLAsfm$@gL', 'Rufus', 'Åström', '960327-7295');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('*W5vdNhD8%k5bH7p', 'Ängla', 'Svensson', '780305-6683');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('W@xR5ZcMz^-#%mhC', 'Charbel', 'Åström', '720201-5215');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('z6ffT4ZnFvA+J^@#', 'Amy', 'Fransson', '520130-2303');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('qFh&42xAp7Bf9e8T', 'Mira', 'Carlsson', '890613-0268');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('rAh6F$jV=dyPHsZt', 'Isabelle', 'Nordin', '950826-8464');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('N^hMT7Nd6TYYjX!J', 'Mikaela', 'Berggren', '990923-1327');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('g5P9mH!XqKT-_*yv', 'Daniela', 'Ekström', '470720-5722');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('9tw&x%9tR^KB8yxH', 'Amer', 'Lindström', '710117-4196');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('dE&tGhAA=QkW7$z?', 'Elvin', 'Petersson', '610429-2096');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('cs$E4X#4xPkH5H&R', 'Pierre', 'Eklund', '740923-1532');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('j3S=^D8myAtn2tfr', 'Sigge', 'Åström', '520728-9371');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('%d+#fT9Un6t2CRDz', 'Maximiliam', 'Mattsson', '710411-7770');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('%d+#fT9Un6t2CRDz', 'Max', 'Mattsson', '740501-7370');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('%d+#fT9Un6t2CRDz', 'Maximus', 'Mattisson', '340501-7450');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('Qz$J?&vB92eE&T2H', 'Vilmer', 'Axelsson', '880202-4771');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('3P?%EsvRz+2xx!2r', 'Yasmin', 'Lind', '900105-3702');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('fX4RTAEZsd??252v', 'Cesar', 'Axelsson', '720522-8971');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('A3q2?NV?!x-squgH', 'Ben', 'Berglund', '021105-0539');
INSERT INTO account (password, first_name, last_name, ssn)VALUES ('A3q2?NV?!x-squgH', 'Ben', 'Bergsson', '011215-0539');
UPDATE account SET name = CONCAT((SUBSTRING(first_name, 1, 3)), SUBSTRING(last_name, 1, 3))WHERE user_id > 0;
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('Pioneer 0',  '1958-08-17', 'Thor DM-18 Able I', '  U   SAF ', 'Orbiter', 'Launch failure');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('Luna E-1 No.1',  '1958-08-23', 'Luna', 'OK B-1', ' Impactor', 'Launch failure');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('Pioneer 4',  '1959-03-03', 'Juno II', 'NA  SA', 'Flyby', 'Partial failure');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('Luna 2',  '1959-08-17', 'Thor DM-18 Able I', ' US AF', 'Orbiter', 'Successful');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('Luna 3',  '1959-10-04', 'Luna', 'OKB-1', 'Flyby', 'Successful');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('Pioneer P-31',  '1960-12-15', 'Atles-D Able', 'NAS A', 'Orbiter', 'Launch failure');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('Ranger 7',  '1964-07-28', 'Atlas LV-3 Agena-B', ' U SAF', 'Orbiter', 'Successful');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('Lunar Orbiter 4',  '1967-05-04', 'Atlas SLVC-3 Agena-D', 'NA SA', 'Orbiter', 'Successful');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('SELENE',  '2007-09-14', 'H-IIA 2022', ' J AX A', 'Orbiter', 'Successful');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('Hiten',  '1990-01-24', 'Mu-3S-II', 'ISA S', 'Flyby/Orbiter', 'Successful');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('Beresheet',  '2019-02-22', 'Falcon 9', ' Spac eIL', 'Lander', 'Spacecraft landing failure');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('Chandrayaan-2',  '2019-07-22', 'GSLV Mk III', 'IS RO', 'Orbiter', 'Operational');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('TESS',  '2019-04-18', 'Falcon 9 Full Thrust', 'N ASA', 'Gravity assist', 'Successful');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ("Chang'es 5-T1",  '2014-10-23', 'Long March 3C', 'CN SA', 'Flyby', 'Successful');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('Manfred Memorial Moon Mission',  '2014-10-23', 'Long March 3C', 'Lu xSpace', 'Flyby', 'Successful');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('Flow',  '2011-09-10', 'Delta II 7920H', 'NAS A', 'Orbiter', 'Successful');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('LADEE',  '2013-09-07', 'Minotaur V', 'N    ASA', 'Orbiter', 'Successful');
INSERT INTO moon_mission
    (spacecraft, launch_date, carrier_rocket, operator, mission_type, outcome) VALUES ('Luna E-1 No.2',  '1958-10-11', 'Luna', 'OKB-  1', 'Impactor', 'Launch failure');






































