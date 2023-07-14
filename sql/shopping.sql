create table shopping
(
    id         int auto_increment
        primary key,
    user_id    int null comment '用户id',
    product_id int null comment '商品id',
    number     int not null comment '商品数量',
    constraint fk_shopping_product
        foreign key (product_id) references product (id),
    constraint fk_shopping_user
        foreign key (user_id) references user (id)
)
    comment '购物车表';

INSERT INTO chongdetang.shopping (id, user_id, product_id, number) VALUES (52, 13, 34, 1);
INSERT INTO chongdetang.shopping (id, user_id, product_id, number) VALUES (53, 13, 36, 1);
INSERT INTO chongdetang.shopping (id, user_id, product_id, number) VALUES (54, 13, 39, 1);
INSERT INTO chongdetang.shopping (id, user_id, product_id, number) VALUES (83, 18, 41, 1);
INSERT INTO chongdetang.shopping (id, user_id, product_id, number) VALUES (86, 15, 27, 1);
INSERT INTO chongdetang.shopping (id, user_id, product_id, number) VALUES (88, 15, 32, 4);
INSERT INTO chongdetang.shopping (id, user_id, product_id, number) VALUES (100, 21, 38, 1);
INSERT INTO chongdetang.shopping (id, user_id, product_id, number) VALUES (108, 17, 26, 1);
