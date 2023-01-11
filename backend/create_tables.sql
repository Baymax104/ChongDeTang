create table brand
(
    id         int auto_increment
        primary key,
    brand_name varchar(50) not null,
    constraint brand_id_uindex
        unique (id)
);

create table product
(
    id            int auto_increment
        primary key,
    brand_id      int          not null,
    product_name  varchar(50)  not null,
    price         float        not null,
    launch_time   date         null,
    product_photo varchar(100) null comment '鍟嗗搧鍥剧墖',
    constraint product_id_uindex
        unique (id),
    constraint product_brand_id_fk
        foreign key (brand_id) references brand (id)
);

create table `order`
(
    id         int auto_increment
        primary key,
    user_id    int           not null,
    product_id int           not null,
    count      int default 0 null,
    constraint order_id_uindex
        unique (id),
    constraint order_product_id_fk
        foreign key (product_id) references product (id)
);

create index order_user_id_fk
    on `order` (user_id);

create table user
(
    id       int auto_increment
        primary key,
    username varchar(40)  null comment '鐢ㄦ埛鏄电О',
    password varchar(100) null comment '瀵嗘枃瀛樺偍',
    photo    varchar(100) null comment '瀛樺偍鐢ㄦ埛澶村儚url',
    mail     varchar(40)  null,
    phone    varchar(20)  null,
    sex      varchar(10)  null comment '性别',
    age      int          null,
    constraint account_id_uindex
        unique (id)
);





