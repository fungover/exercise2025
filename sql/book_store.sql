-- Uppgift Book Store
drop database if exists book_store;
create database if not exists book_store;

use book_store;
create table if not exists author
(
    id         int primary key auto_increment,
    first_name varchar(255),
    last_name  varchar(255),
    birth_date DATE
);
create table if not exists book
(
    isbn             varchar(255) primary key check ( length(isbn) = 13 ),
    title            varchar(255),
    language         varchar(255) default 'english',
    price_kr         decimal,
    publication_date DATE,
    author_id        int,
    foreign key (author_id) references author (id)
);
create table if not exists bookstore
(
    id         int primary key auto_increment,
    store_name varchar(255),
    location   varchar(255)
);
create table if not exists inventory
(
    store_id  int,
    book_isbn varchar(255),
    amount    int,
    foreign key (store_id) references bookstore (id),
    foreign key (book_isbn) references book (isbn),
    primary key (store_id, book_isbn)
);
insert into author
values (1, 'Rick', 'Riordan', '1964-06-05'),
       (2, 'JRR', 'Tolkien', '1892-01-03'),
       (3, 'Conn', 'Iggulden', '1971-02-24'),
       (4, 'Camilla', 'Läckberg', '1974-08-30'),
       (5, 'Viveca', 'Sten', '1959-06-18');
insert into book
values ('9789179793135', 'Percy Jackson: Född till hjälte', 'svenska', 186.95, '2005-06-28', 1),
       ('9789179793128', 'Percy Jackson: Monsterhavet', 'svenska', 195.95, '2006-04-01', 1),
       ('9789113084909', 'Lord of the Rings: Fellowship of the Ring', 'english', 229.95, '1954-07-29', 2),
       ('9789113084916', 'Lord of the Rings: The two Towers', 'english', 229.95, '1954-11-11', 2),
       ('9789113084923', 'Lord of the Rings: The return of the King', 'english', 219.95, '1955-10-20', 2),
       ('9789177956631', 'Atens Portar', 'svenska', 63.95, '2020-08-06', 3),
       ('9789137137797','Häxan', 'svenska', 359.95, '2017-04-07', 4),
       ('9789175037462', 'Sjöjungrun', 'svenska',159.95, '2011-11-01', 4),
       ('9789137161525', 'Gråterskan', 'svenska', 319.95, '2025-09-01', 4),
       ('9789137502526', 'Offermakaren', 'svenska', 125.95, '2021-10-14', 5),
       ('9789137503738', 'Dalskuggan', 'svenska', 169.95, '2022-03-01', 5),
       ('9789137162522', 'Benådaren', 'svenska', 179.95, '2025-03-03', 5);
insert into bookstore
values (1, 'Lustikurres Bokhandel', 'Lund'),
       (2, 'Akademibokhandeln', 'Online'),
       (3, 'Mrs Em\'s Bookstore', 'Central London');
insert into inventory
values (1, '9789179793135', 7),
       (1, '9789179793128', 12),
       (1, '9789177956631', 3),
       (2, '9789113084909', 5),
       (2, '9789113084916', 5),
       (2, '9789113084923', 5),
       (2, '9789137137797', 4),
       (2, '9789175037462', 6),
       (2, '9789137161525', 2),
       (2, '9789137502526', 3),
       (2, '9789137503738', 1),
       (2, '9789137162522', 5),
       (3, '9789137137797', 4),
       (3, '9789175037462', 6),
       (3, '9789137161525', 2),
       (3, '9789137502526', 3),
       (3, '9789137503738', 1),
       (3, '9789137162522', 5),
       (3, '9789113084909', 1);


drop view if exists total_author_book_value;
create view total_author_book_value as
select distinct concat(first_name, ' ', last_name)             as name,
                if(timestampdiff(year, birth_date, curdate()) > 100, 'dead',
                   timestampdiff(year, birth_date, curdate())) as age,
                concat(sum(inventory.amount), ' st')                          as book_title_count,
                concat(sum(book.price_kr * inventory.amount), ' kr')          as inventory_value
from author
         left join book on author.id = book.author_id
         left join inventory on book.isbn = inventory.book_isbn
group by author.id;

select *
from total_author_book_value;
