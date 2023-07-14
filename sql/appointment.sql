create table appointment
(
    id          int auto_increment
        primary key,
    subscriber  varchar(20) not null comment '预约人姓名',
    phone       varchar(20) not null comment '预约人手机号',
    number      int         not null comment '预约人数',
    date        date        not null comment '下单日期',
    order_time  date        not null comment '预约日期',
    order_money double      null comment '预约金额',
    status      varchar(10) not null comment '预约状态',
    user_id     int         not null comment '用户id',
    constraint fk_appointment_user
        foreign key (user_id) references user (id)
)
    comment '用户预约表';

INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-1887322110, '刚', '13649423971', 2, '2023-07-01', '2020-08-01', null, 'SUCCESS', 2);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-1759014911, 'ljw', '13681575260', 2, '2023-07-06', '2023-07-06', null, 'FAIL', 18);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-1719570430, '塞塞塞', '13649423971', 2, '2023-05-28', '2023-02-28', null, 'FAIL', 7);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-1610498047, 'wzy', '13649423971', 20, '2023-07-01', '2021-07-01', null, 'SUCCESS', 2);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-1442590719, '刚好', '13649423971', 2, '2024-02-19', '2023-02-19', null, 'SUCCESS', 2);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-1405079550, '躺会', '17754576235', 2, '2012-02-12', '2023-02-12', null, 'SUCCESS', 2);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-1392467967, 'lzk3', '13661045657', 3, '2023-07-02', '2023-07-02', null, 'SUCCESS', 17);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-1375682558, '方程', '13649423971', 15, '2023-07-02', '2024-10-02', null, 'FAIL', 2);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-1145004031, '刘泽坤', '13661045657', 6, '2023-07-02', '2023-07-02', null, 'PROCESSING', 17);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-1085263870, '刘泽坤', '13661045657', 3, '2023-07-08', '2023-08-04', null, 'FAIL', 22);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-1044340735, 'lzk1', '13661045657', 3, '2023-07-02', '2023-07-02', null, 'SUCCESS', 17);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-524263422, '张三', '13522380331', 2, '2023-06-30', '2018-06-30', null, 'PROCESSING', 15);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-230670335, 'ccc', '18056199338', 1, '2023-02-20', '2023-02-20', null, 'SUCCESS', 2);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-16736255, 'lzk2', '13661045657', 3, '2023-07-02', '2023-07-02', null, 'SUCCESS', 17);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (-4079615, '刘泽坤', '13661045657', 3, '2023-07-01', '2023-07-01', null, 'SUCCESS', 17);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (231845890, '韦振宇', '13649423971', 2, '2023-07-07', '2023-10-10', null, 'PROCESSING', 2);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (478167042, 'clc', '18518375093', 2, '2023-02-21', '2023-02-20', null, 'SUCCESS', 2);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (592568321, '太让人', '13552985654', 11, '2023-07-11', '2023-07-11', null, 'FAIL', 25);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (1100079106, '太让人', '13552985654', 11, '2023-07-11', '2023-07-11', null, 'SUCCESS', 25);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (1354776578, '我我', '13649423971', 2, '2023-02-20', '2023-02-20', null, 'SUCCESS', 2);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (1360125954, '太让人', '13552985654', 11, '2023-07-11', '2023-07-11', null, 'PROCESSING', 25);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (1363288066, 'wzy', '13649423971', 2, '2023-02-26', '2023-02-22', null, 'PROCESSING', 2);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (1598038017, '沧海', '13649423971', 20, '2023-06-26', '2023-06-26', null, 'PROCESSING', 2);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (2004930562, '古城', '13649423971', 2, '2023-06-26', '2023-04-26', null, 'PROCESSING', 2);
INSERT INTO chongdetang.appointment (id, subscriber, phone, number, date, order_time, order_money, status, user_id) VALUES (2092982273, '张三', '13522380331', 2000, '2023-06-30', '2018-06-30', null, 'PROCESSING', 15);
