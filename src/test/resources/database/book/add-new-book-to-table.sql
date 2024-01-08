INSERT INTO categories (id, name, description)
VALUES (1, 'testName', 'testDescription');
INSERT INTO books (id, title, author, isbn, price, description, cover_image)
VALUES (1, 'testTitle', 'testAuthor', 'testISBN', 5, 'testDescription', 'testImage');
INSERT INTO books_categories (book_id, category_id)
VALUES (1, 1)