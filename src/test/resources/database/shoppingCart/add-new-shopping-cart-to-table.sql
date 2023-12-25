INSERT INTO users (id, email, password, first_name, last_name)
VALUES (1, 'testUser', 'testPassword', 'testName', 'testLastName');
INSERT INTO users (id, email, password, first_name, last_name)
VALUES (2, 'testAdmin', 'testPasswordAdmin', 'testNameAdmin', 'testLastNameAdmin');
INSERT INTO users_roles (user_id, role_id)
VALUES (1, 2);
INSERT INTO users_roles (user_id, role_id)
VALUES (2, 1);
INSERT INTO books (id, title, author, isbn, price, description, cover_image)
VALUES (1, 'testTitle', 'testAuthor', 'testISBN', 5, 'testDescription', 'testImage');
INSERT INTO shopping_carts (id, user_id)
VALUES (1, 1);
INSERT INTO cart_items (shopping_cart_id, book_id, quantity)
VALUES (1, 1, 1);
