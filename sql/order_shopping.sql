create table order_shopping
(
    id         int auto_increment
        primary key,
    order_id   int           null,
    product_id int           not null,
    number     int default 1 not null,
    constraint order_shopping_order_id_fk
        foreign key (order_id) references orders (id)
            on delete cascade,
    constraint order_shopping_product_id_fk
        foreign key (product_id) references product (id)
);

INSERT INTO chongdetang.order_shopping (id, order_id, product_id, number) VALUES (82, 57, 41, 1);
INSERT INTO chongdetang.order_shopping (id, order_id, product_id, number) VALUES (97, 67, 47, 1);
INSERT INTO chongdetang.order_shopping (id, order_id, product_id, number) VALUES (113, 74, 49, 2);
INSERT INTO chongdetang.order_shopping (id, order_id, product_id, number) VALUES (114, 74, 34, 1);
INSERT INTO chongdetang.order_shopping (id, order_id, product_id, number) VALUES (115, 75, 34, 1);
INSERT INTO chongdetang.order_shopping (id, order_id, product_id, number) VALUES (116, 78, 26, 1);
INSERT INTO chongdetang.order_shopping (id, order_id, product_id, number) VALUES (119, 80, 31, 1);
INSERT INTO chongdetang.order_shopping (id, order_id, product_id, number) VALUES (120, 80, 27, 3);
