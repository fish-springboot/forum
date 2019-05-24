-- 这是一个测试数据集合

-- 有一个id为1的用户
-- 有个id为1的版块，版块管理员的id是1
-- 有个id为1的文章，文章所在的版块的id为1，文章的创建者的id为1


insert into user (id, email, name, password, token)
values (1, 'hello@gmail.com', 'Jon', '1234567890', 'hello_token');


insert into plate (admin_id, icon, info, title, id)
values (1, 'icon', 'info', 'koa', 1);

insert into article (id, author_id, content, plate_id, state, title)
values (1, 1, 'hello world', 1, 3, 'Spring天下无敌')
