create table address
(
    id        int auto_increment
        primary key,
    user_id   int          not null comment '用户id',
    province  varchar(20)  not null comment '省份',
    city      varchar(20)  not null comment '城市',
    detail    varchar(100) not null comment '详细地址',
    consignee varchar(20)  not null comment '收货人',
    phone     varchar(20)  not null comment '手机号',
    constraint fk_address_user
        foreign key (user_id) references user (id)
)
    comment '用户收货地址表';

INSERT INTO chongdetang.address (id, user_id, province, city, detail, consignee, phone) VALUES (11, 1, '北京市', '北京市', 'bjut', 'clb', '18056199338');
INSERT INTO chongdetang.address (id, user_id, province, city, detail, consignee, phone) VALUES (16, 15, '北京市', '北京市', '平乐园100号', '孟祥芾', '13522380331');
INSERT INTO chongdetang.address (id, user_id, province, city, detail, consignee, phone) VALUES (26, 18, '四川省', '甘孜藏族自治州', '祁连山1号山洞', '李先森', '13681575260');
INSERT INTO chongdetang.address (id, user_id, province, city, detail, consignee, phone) VALUES (32, 17, '北京市', '北京市', '管庄', '刘泽坤', '13661045657');
INSERT INTO chongdetang.address (id, user_id, province, city, detail, consignee, phone) VALUES (33, 17, '北京市', '北京市', '管庄西里', '刘泽坤', '13661045657');
INSERT INTO chongdetang.address (id, user_id, province, city, detail, consignee, phone) VALUES (34, 22, '北京市', '北京市', 'gzxl', '刘泽坤', '13661045657');
INSERT INTO chongdetang.address (id, user_id, province, city, detail, consignee, phone) VALUES (35, 2, '北京市', '北京市', '海淀', 'clc', '18311172099');
