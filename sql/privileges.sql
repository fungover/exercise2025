drop user if exists 'dev'@'localhost';
drop user if exists 'webdev'@'%';

create user 'dev'@'localhost' identified by 'dev-password';
create user 'webdev'@'%' identified by 'web-dev-password';

grant create,
    update,
    select,
    delete,
    insert,
    alter,
    create view on book_store.* to 'dev'@'localhost';

grant insert, update, select, delete on book_store.* to 'webdev'@'%';