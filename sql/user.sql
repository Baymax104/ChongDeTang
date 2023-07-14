create table user
(
    id       int auto_increment
        primary key,
    username varchar(40)            null comment '用户名',
    password varchar(100)           null comment '密码',
    photo    varchar(100)           null comment '用户头像标识符',
    mail     varchar(40)            null comment '邮箱',
    phone    varchar(20)            null comment '手机号',
    gender   varchar(4)             null comment '性别',
    birthday date                   null comment '生日',
    admin    varchar(1) default '0' null comment '是否是管理员',
    constraint account_id_uindex
        unique (id)
)
    comment '用户表';

INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (1, '用户1001', '$2a$10$vb8zLuz628h4jpwmGdvmtuFGzFYSiJhfVlyyi19BvaYRjp1pzVcXi', null, null, '18056199338', '男', '2003-01-08', '0');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (2, '一个小偷', '$2a$10$LkALaK45Cznfp7zjvLVqreJh5968OTdyrKWBeSfDHLkWELhf/VOvC', 'img/user_photo/user_2.jpg', null, '13649423971', '女', '2023-04-09', '1');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (3, '用户1003', '$2a$10$9UFqcxo99MyeKNWrISlY/.Ha5i2Ri84TGN1v7m/oxnNo0Squ8hnle', null, null, '17754576235', null, null, '0');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (6, '用户1006', '$2a$10$tpZ/5gZD5A3reANqcLDE9u03YBvgRveVesh8gwDtg3ziQMB7IAx7S', null, null, '15778290518', null, null, '1');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (7, '一个小偷', '$2a$10$zaDX6Wvt43WESTavbvJmdueqCoEKYfBrPQ0I1RzmMpO.9ENAt5gKa', null, null, '13978016281', '男', null, '1');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (8, '用户1008', '$2a$10$d6Y7XNwcT12EOtnlDH1Sh.5Y3bWrvWdNWgaKOQTwm9Bb5wUzZABAS', null, null, '17611390000', null, null, '0');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (10, '用户1010', '$2a$10$ae.oJKTMk2gK0fXEbuj0XOHqwGp8/KhhhcoGG/857ak6vdL17MK.m', null, null, '18056199333', null, null, '0');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (11, '用户1011', '$2a$10$xkBi0k.tPUf0Lradn9HyPO8eVTISDVf8M4BnHjsk/Y0SwUqh82AkO', null, null, '17611395153', null, null, '2');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (12, '用户1012', '$2a$10$ZzeGdNXVT15F.FiUP4kdb.ypNcsvcmWMkAwofLxaBxwdIbVWDSa1y', null, null, '17611395154', null, null, '0');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (13, '用户1013', '$2a$10$8HFWxB6IkN3jP6s5JYtnsupENMXVll9oA1eIWII/1DnXt8FdREonq', null, null, '17611395155', null, null, '1');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (14, '用户1014', '$2a$10$qDNylsWhg/Y3CeVLeaL7yucbiizgqoHaxC.9LoF38Z8WI8yHqizHa', null, null, '13523380331', null, null, '1');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (15, '梦xf1234', '$2a$10$32vGQcxXd5rc4qQfFAUYjeeWI0wXSmnhrSO9LmP8xrFDG6jzzn15C', 'img/user_photo/user_15.jpg', null, '12345678901', '女', '2032-07-06', '0');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (16, 'Postman', '$2a$10$/3q2o7Nj4QSp/gU3grANYetHk4tmPkyYoSR00xj96tPtdrCJcFR2.', 'img/user_photo/user_16.jpg', null, '13978016282', null, null, '1');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (17, '哈哈哈', '$2a$10$iTg1CN8qlxuKSfZetclHxOISVtoTjuaomJtpFbHM65y2lD.31Mjru', 'img/user_photo/user_17.jpg', null, '13661045657', '男', '2016-10-01', '0');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (18, '用户1018', '$2a$10$p/10NIbzPEt7Tm/hD.Tys.cNs2dJnCaBgEJ6BjzeA8UL0O4ZfV/s6', null, null, '13681575260', null, null, '0');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (19, '用户1019', '$2a$10$NZdDNDS2a8YTCx6p3EGNnO24cR64C1cQhZkYVP/VrmJTGNMSTXML.', null, null, '13522380331', null, null, '0');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (20, '用户1020', '$2a$10$ouahpZTwe35Qqbub8XEtOebe8zgaZEefQZpgAP4dKgLTxbaYmmzqi', null, null, '18056199335', null, null, '0');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (21, '用户1021', '$2a$10$hfTb7ed1EEBsiY1fEZj5NeYNym7hEM2EyWfzsg0fLSV4EsotFtwxS', null, null, '18056199331', null, null, '0');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (22, '哈哈哈哈', '$2a$10$2txSdbFwr1PWM8yelVPPXudv4dG3uDmehuE7mmcWUOd/r1DLjt1DG', 'img/user_photo/user_22.jpg', null, '13691164467', '女', '1988-07-08', '0');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (23, '用户1023', '$2a$10$62DJKkFtWH8LMPO6Bm4SsOofGYJeL.TM6fNHw57h9cIzg.pE4mqI2', null, null, '18310762870', null, null, '0');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (24, '用户1024', '$2a$10$apZpX2nZfYSwb4XD71luAOhxgZIYayVBH0kTDyag87MYDfTqq5tx6', null, null, '18310762875', null, null, '0');
INSERT INTO chongdetang.user (id, username, password, photo, mail, phone, gender, birthday, admin) VALUES (25, '用户1025', '$2a$10$kRXwdsFhxecoG4Y8N.D8x.X4Xzc3h1n6Xj53UeWggUhAazIMx8n0i', null, null, '13552985654', null, null, '0');
