-- Uppgift Moon Missions

drop table if exists successful_mission;

create table successful_mission as
select *
from moon_mission
where outcome = 'successful';

alter table successful_mission
    modify mission_id SMALLINT primary key auto_increment;

update successful_mission
set operator = replace(operator, ' ', '')
where operator is not null;

update moon_mission
set operator = replace(operator, ' ', '')
where operator is not null;

delete
from successful_mission
where launch_date > '2010-01-01';

select user_id,
       name                                                               as username,
       concat(first_name, ' ', last_name)                                 as name,
       IF(cast(substr(ssn, 10, 1) as unsigned) % 2 = 0, 'female', 'male') as gender,
       ssn
from account;

delete
from account
where cast(substr(ssn, 10, 1) as unsigned) % 2 = 0
  and ssn like '70%';

select distinct
    if(cast(substr(ssn, 10, 1) as unsigned) % 2 = 0, 'female', 'male') as gender,
    truncate(avg(timestampdiff(year, concat(if(cast(substr(ssn, 1, 2) as unsigned) > cast(substr(year(curdate()), 1, 2) as unsigned), '19', '20'), substr(ssn, 1, 6)), curdate())), 1) as avg_age
from account
group by gender;