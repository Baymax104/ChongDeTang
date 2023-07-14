create table user_collection
(
    id            int auto_increment
        primary key,
    user_id       int not null comment '用户id',
    collection_id int not null comment '藏品id',
    constraint user_collection_collection_id_fk
        foreign key (collection_id) references collection (id)
            on delete cascade,
    constraint user_collection_user_id_fk
        foreign key (user_id) references user (id)
)
    comment '用户收藏藏品表';

INSERT INTO chongdetang.user_collection (id, user_id, collection_id) VALUES (28, 2, 2);
INSERT INTO chongdetang.user_collection (id, user_id, collection_id) VALUES (30, 2, 5);
INSERT INTO chongdetang.user_collection (id, user_id, collection_id) VALUES (41, 15, 4);
INSERT INTO chongdetang.user_collection (id, user_id, collection_id) VALUES (42, 15, 51);
INSERT INTO chongdetang.user_collection (id, user_id, collection_id) VALUES (43, 15, 37);
INSERT INTO chongdetang.user_collection (id, user_id, collection_id) VALUES (44, 15, 49);
INSERT INTO chongdetang.user_collection (id, user_id, collection_id) VALUES (45, 15, 52);
INSERT INTO chongdetang.user_collection (id, user_id, collection_id) VALUES (46, 18, 29);
INSERT INTO chongdetang.user_collection (id, user_id, collection_id) VALUES (47, 18, 2);
INSERT INTO chongdetang.user_collection (id, user_id, collection_id) VALUES (48, 18, 52);
INSERT INTO chongdetang.user_collection (id, user_id, collection_id) VALUES (50, 2, 4);
INSERT INTO chongdetang.user_collection (id, user_id, collection_id) VALUES (62, 2, 1);
