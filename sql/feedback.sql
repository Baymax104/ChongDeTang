create table feedback
(
    id      int auto_increment
        primary key,
    user_id int          not null comment '用户id',
    content varchar(100) not null comment '反馈内容',
    constraint fk_user
        foreign key (user_id) references user (id)
)
    comment '用户反馈信息表';

INSERT INTO chongdetang.feedback (id, user_id, content) VALUES (3, 2, 'test test test');
INSERT INTO chongdetang.feedback (id, user_id, content) VALUES (11, 2, '班级就靠靠');
INSERT INTO chongdetang.feedback (id, user_id, content) VALUES (12, 2, '我也不知道好不好');
INSERT INTO chongdetang.feedback (id, user_id, content) VALUES (13, 2, '看看加载框');
INSERT INTO chongdetang.feedback (id, user_id, content) VALUES (14, 2, '看看这样写怎么样');
INSERT INTO chongdetang.feedback (id, user_id, content) VALUES (15, 2, '太快了没看清');
INSERT INTO chongdetang.feedback (id, user_id, content) VALUES (16, 2, '再试一次');
INSERT INTO chongdetang.feedback (id, user_id, content) VALUES (17, 2, '看看这样写怎么样');
INSERT INTO chongdetang.feedback (id, user_id, content) VALUES (18, 2, '好像还是这样效果好');
INSERT INTO chongdetang.feedback (id, user_id, content) VALUES (20, 2, '三嫂嫂');
INSERT INTO chongdetang.feedback (id, user_id, content) VALUES (21, 2, '现在应该可以了');
INSERT INTO chongdetang.feedback (id, user_id, content) VALUES (22, 15, '有一些小bug');
INSERT INTO chongdetang.feedback (id, user_id, content) VALUES (23, 15, '“我认为这个app功能尚未开发完整，有待改进---mxf_6.28”');
INSERT INTO chongdetang.feedback (id, user_id, content) VALUES (24, 17, '很好');
