-- create table user (
--   id integer not null,
--   avatar varchar(200),
--   email varchar(50) not null,
--   name varchar(20) not null,
--   password varchar(30) not null,
--   token varchar(255) not null,
--   primary key (id)
-- );

insert into user (id, email, name, password, token)
values (1, 'sf@gmail.com', 'Jon', '1234567890', 'dfs');


insert into plate (admin_id, icon, info, title, id)
values (1, 'icon', 'info', 'koa', 1);

insert into article (id, author_id, content, plate_id, state, title)
values (1, 1, 'hello world', 1, 3, 'Spring天下无敌')
