insert into user (type, username, password, email, avatar, registration_date, description, display_name) values('admin','Toki', 'toki', 'example@gmail.com', 'lol', '2022-05-13 19:42:10', 'Optimistic', 'Toki master lmao');
insert into post (title, text, creation_date, image_path) values('Proba','Lorem ipsum dolor sit amet.', '2022-05-13 19:42:10', 'lol');
insert into user (type, username, password, email, avatar, registration_date, description, display_name) values('user','Vlaki', 'vlaki', 'example@gmail.com', 'lol', '2022-05-13 19:42:10', 'Complicated ahahahah', 'Vlaki je car');
insert into user_posts (user_id, posts_id) values (1, 1);
