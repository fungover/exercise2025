SELECT store_name FROM store
                           JOIN books.inventory i on store.store_id = i.store_id
                           JOIN books.book b on b.book_id = i.book_id
                           JOIN books.author a on a.id = b.author_id
WHERE a.first_name = 'Sofia' GROUP BY store_name;
