create table orders
(
    id         int auto_increment,
    user_id    int                              not null,
    order_date date                             not null,
    status     varchar(20) default 'PROCESSING' not null,
    province   varchar(50)                      not null,
    city       varchar(50)                      not null,
    detail     varchar(100)                     not null,
    consignee  varchar(50)                      not null,
    phone      varchar(20)                      not null,
    constraint order_pk
        unique (id),
    constraint order_user_id_fk
        foreign key (user_id) references user (id)
);

INSERT INTO chongdetang.orders (id, user_id, order_date, status, province, city, detail, consignee, phone) VALUES (57, 18, '2023-07-07', 'PROCESSING', '四川省', '甘孜藏族自治州', '祁连山1号山洞', '李先森', '13681575260');
INSERT INTO chongdetang.orders (id, user_id, order_date, status, province, city, detail, consignee, phone) VALUES (67, 22, '2023-07-08', 'PROCESSING', '北京市', '北京市', 'gzxl', '刘泽坤', '13661045657');
INSERT INTO chongdetang.orders (id, user_id, order_date, status, province, city, detail, consignee, phone) VALUES (74, 17, '2023-07-09', 'PROCESSING', '北京市', '北京市', '管庄', '刘泽坤', '13661045657');
INSERT INTO chongdetang.orders (id, user_id, order_date, status, province, city, detail, consignee, phone) VALUES (75, 17, '2023-07-09', 'FAIL', '北京市', '北京市', '管庄', '刘泽坤', '13661045657');
INSERT INTO chongdetang.orders (id, user_id, order_date, status, province, city, detail, consignee, phone) VALUES (76, 17, '2023-07-09', 'PROCESSING', '北京市', '北京市', '管庄', '刘泽坤', '13661045657');
INSERT INTO chongdetang.orders (id, user_id, order_date, status, province, city, detail, consignee, phone) VALUES (77, 17, '2023-07-09', 'PROCESSING', '北京市', '北京市', '管庄', '刘泽坤', '13661045657');
INSERT INTO chongdetang.orders (id, user_id, order_date, status, province, city, detail, consignee, phone) VALUES (78, 17, '2023-07-09', 'PROCESSING', '北京市', '北京市', '管庄', '刘泽坤', '13661045657');
INSERT INTO chongdetang.orders (id, user_id, order_date, status, province, city, detail, consignee, phone) VALUES (80, 2, '2023-07-11', 'PROCESSING', '北京市', '北京市', '海淀', 'clc', '18311172099');
