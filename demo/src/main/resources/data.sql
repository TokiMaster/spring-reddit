insert into user (type, username, password, email, avatar, registration_date, description, display_name)
values('admin','Toki', '$2a$12$9vqtik0tstrwzgS20mXpZeM3J4scMaDs3QhI7W9gfhL4Z18ch7BlC', 'toki@gmail.com', 'lol', '2022-05-13 19:42:10', 'Optimistic', 'Toki master lmao');
insert into user (type, username, password, email, avatar, registration_date, description, display_name)
values('user','Vlaki', '$2a$12$EgGKK227HZdPAUSVYFlyfumHsevvCDVBZv/2xKFV6K/2AEokY7mVW', 'vlaki@gmail.com', 'lol', '2022-05-13 19:42:10', 'Complicated ahahahah', 'Vlaki je car');
insert into community(name, description, creation_date, is_suspended)
values('Probni community', 'AAAAAAAAAAAAAAAAAAAA', '2022-05-23 16:42:10', 0);
insert into post (title, text, creation_date, image_path, community_id, user_id)
values('Proba','Lorem ipsum dolor sit amet.', '2022-05-13 19:42:10', 'lol','1', '2');
-- select * from user;
