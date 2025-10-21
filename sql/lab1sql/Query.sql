USE laboration1;

-- Uppgift 1
drop table if exists successful_mission;
CREATE TABLE successful_mission AS
select *
from moon_mission
where moon_mission.outcome = 'Successful';

-- Verifiera
select count(*) as rows_in_successful_mission
from successful_mission;
select *
from successful_mission;

-- Uppgift 2
-- Säkerställ rätt typ och att kolumnen inte är NULL
alter table successful_mission
    modify column mission_id smallint not null;

-- Lägg till auto_increment och primary key
alter table successful_mission
    modify column mission_id smallint not null auto_increment,
    add primary key (mission_id);

-- Uppgift 3
-- Ta bort mellanslag i operator-namnen
update moon_mission
set operator = replace(trim(operator), ' ', '')
where operator like '% %'
   or operator != trim(operator);

update successful_mission
set operator = replace(trim(operator), ' ', '')
where operator like '% %'
   or operator != trim(operator);

-- Uppgift 4
-- ta bort uppdrag 2010 eller senare
delete
from successful_mission
where year(launch_date) >= 2010;

-- Verifiera
select max(launch_date) as max_date
from successful_mission;

-- Uppgift 5
-- skapae en ny kolumn för gender
alter table account
    add column gender varchar(10) not null;
select a.*,
       concat(a.first_name, ' ', a.last_name) as name,
       case
           when substring(replace(a.ssn, '-', ''), -2, 1) in ('0', '2', '4', '6', '8') then 'female'
           else 'male'
           end as gender
from account as a;

-- Uppgift 6
-- Kontroller vilka som är kvinnor och födda före 1970
select user_id, first_name, last_name, ssn
from account
where
    substring(replace(ssn,'-',''), -2, 1) in ('0','2','4','6','8')
  and (
         case
              when (substring(replace(ssn,'-',''), 1, 2) + 0) <= 24
                  then 2000 + (substring(replace(ssn,'-',''), 1, 2) + 0)
              else 1900 + (substring(replace(ssn,'-',''), 1, 2) + 0)
              end

          ) < 1970;

-- Ta bort alla kvinnor födda före 1970
delete from account
where
    substring(replace(ssn,'-',''), -2, 1) in ('0','2','4','6','8')
  and (
          case
              when (substring(replace(ssn, '-', ''), 1, 2) + 0) <= 24
                  then 2000 + (substring(replace(ssn, '-', ''), 1, 2) + 0)
              else 1900 + (substring(replace(ssn, '-', ''), 1, 2) + 0)
              end

          ) < 1970;

-- Verifiera
select count(*) as rows_in_account
from account;

-- Uppgift 7
-- räkna ut medelålder per kön
select
    gender,
    round(avg(age), 1) as average_age
from (
         select
             case
                 when substring(replace(a.ssn, '-', ''), -2, 1) in ('0', '2', '4', '6', '8') then 'female'
                 else 'male'
                 end as gender,
             timestampdiff(
                     year ,
                     str_to_date(
                             concat(
                                     case
                                         when (substring(replace(a.ssn, '-', ''), 1, 2) + 0) <= 24 then '20'
                                         else '19'
                                         end,
                                     substring(replace(a.ssn, '-', ''), 1, 6)
                             ),
                             '%Y%m%d'
                     ),
                     curdate()
             ) as age
         from account a
     ) as t
group by gender;


