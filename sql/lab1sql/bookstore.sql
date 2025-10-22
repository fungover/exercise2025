drop database if exists bookstore;
     create database bookstore
            character set utf8mb4
            collate utf8mb4_0900_ai_ci;
use bookstore;

-- Tabeller
create table language (
    language_code varchar(2) not null primary key,
    language_name varchar(255) not null,
    constraint language_code_unique unique (language_name)
);

create table author (
    id int not null primary key auto_increment,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    birth_date date not null
);

create table book (
    isbn varchar(13) not null,
    title varchar(255) not null,
    language_code varchar(2) not null,
    price decimal(10,2) not null check ( price >= 0 ),
    publication_date date not null,
    author_id int not null,
    constraint pk_book primary key (isbn),
    constraint chk_isbn13 check ( isbn REGEXP '^[0-9]{13}$' ),
    constraint fk_book_author foreign key (author_id) references author(id),
    constraint fk_book_language foreign key (language_code) references language(language_code)
);

create table bookstore (
    id int not null primary key auto_increment,
    store_name varchar(255) not null,
    city varchar(120) not null
);

create table inventory (
  store_id int not null,
  isbn varchar(13) not null,
  amount int not null check ( amount >= 0 ),
  constraint pk_inventory primary key (store_id, isbn),
  constraint fk_inventory_store foreign key (store_id) references bookstore(id),
  constraint fk_inventory_book foreign key (isbn) references book(isbn)
);

-- Data
insert into language (language_code, language_name) values
('sv', 'Svenska'),
('en', 'English'),
('no', 'Norsk');

insert into author (first_name, last_name, birth_date) values
('Astrid', 'Lindgren', '1907-11-14'),
('Haruki', 'Murakami', '1949-01-12'),
('Eddie', 'Neumann', '2002-04-15');

insert into book (isbn, title, language_code, price, publication_date, author_id) values
('9789129658010', 'Pippi Långstrump', 'sv', 149.00, '1945-11-01',(select id from author where last_name='Lindgren' limit 1)),
('9789129695442','Bröderna Lejonhjärta','sv',179.00,'1973-10-01',(select id from author where last_name='Lindgren' limit 1)),
('9780316066525','The Wind-Up Bird Chronicle','en',199.00,'1997-10-01',(select id from author where last_name='Murakami' limit 1)),
('9780000000001','Eddies första bok','sv',129.00,'2024-01-01',(select id from author where last_name='Neumann' limit 1));

insert into bookstore (store_name, city) VALUE
('Bokhandel', 'Stockholm'),
('Campus Bokhandeln', 'Uppsala');

insert into inventory (store_id, isbn, amount) VALUE
((select id from bookstore where store_name='Bokhandel' limit 1), '9789129658010', 10 ),
((select id from bookstore where store_name='Bokhandel' limit 1), '9789129695442', 5),
((select id from bookstore where store_name='Bokhandel' limit 1), '9780316066525', 7),
((select id from bookstore where store_name='Campus Bokhandeln' limit 1), '9789129658010', 3),
((select id from bookstore where store_name='Campus Bokhandeln' limit 1), '9780000000001', 12);

-- View: total_author_book_value
create or replace view total_author_book_value as
select
    concat(a.first_name, ' ', a.last_name) as name,
    concat(timestampdiff(YEAR, a.birth_date, curdate()), ' år') as age,
    concat(count(distinct b.title), ' st') as book_title_count ,
    concat(coalesce(round(sum(b.price * i.amount), 0), 0), ' kr') as inventory_value
from author a
    left join book b on b.author_id = a.id
    left join inventory i on i.isbn = b.isbn
group by a.id;

-- Skapa användare
drop user if exists 'dev_user'@'localhost';
drop user if exists 'web_user'@'localhost';
create user 'dev_user'@'localhost' identified by 'dev_user';
create user 'web_user'@'localhost' identified by 'web_user';

-- rättigheter
grant select, insert, update, delete, create,
    alter, create view, show view, trigger, references on bookstore.* to 'dev_user'@'localhost';
grant drop on bookstore.author to 'dev_user'@'localhost';
grant drop on bookstore.book to 'dev_user'@'localhost';
grant drop on bookstore.bookstore to 'dev_user'@'localhost';
grant drop on bookstore.inventory to 'dev_user'@'localhost';
grant drop on bookstore.language to 'dev_user'@'localhost';
grant select, insert, update, delete on bookstore.* to 'web_user'@'localhost';

-- Verifiera
show grants for 'dev_user'@'localhost';
show grants for 'web_user'@'localhost';