create table user_product
(
    id         int auto_increment
        primary key,
    user_id    int not null comment '用户id',
    product_id int not null comment '商品id',
    constraint user_product_product_id_fk
        foreign key (product_id) references product (id)
            on delete cascade,
    constraint user_product_user_id_fk
        foreign key (user_id) references user (id)
)
    comment '用户收藏商品表';

INSERT INTO chongdetang.user_product (id, user_id, product_id) VALUES (52, 18, 32);
INSERT INTO chongdetang.user_product (id, user_id, product_id) VALUES (53, 18, 50);
INSERT INTO chongdetang.user_product (id, user_id, product_id) VALUES (55, 21, 38);
INSERT INTO chongdetang.user_product (id, user_id, product_id) VALUES (68, 2, 27);
INSERT INTO chongdetang.user_product (id, user_id, product_id) VALUES (70, 25, 39);
