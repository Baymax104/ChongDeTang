create table collection
(
    id       int auto_increment
        primary key,
    title    varchar(100)           null comment '藏品名',
    url      varchar(1000)          null comment '藏品介绍页网址',
    type     varchar(50)            null comment '藏品类型',
    photo    varchar(1000)          null comment '藏品图片标识符',
    selected varchar(1) default '0' null comment '是否为精选藏品'
);

INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (1, '夏湘平书法作品 行好积德', 'https://www.cdtde.com/zszx/shufa/248.html', 'sf', 'http://cdtde.com/uploads/210526/1-210526150HC01.jpg', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (2, '董辰生书法作品 同德协力', 'https://www.cdtde.com/zszx/shufa/245.html', 'sf', 'http://cdtde.com/uploads/210526/1-21052615063G42.jpg', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (4, '李铎书法作品 厚德载物', 'https://www.cdtde.com/zszx/shufa/247.html', 'sf', 'http://cdtde.com/uploads/210526/1-210526150542b8.jpg', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (5, '李光曦书法作品 遗德休烈', 'https://www.cdtde.com/zszx/shufa/192.html', 'sf', 'http://cdtde.com/uploads/allimg/210331/1-2103311H0100-L.jpg', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (7, '朱鹤亭书法作品 同心同德', 'https://www.cdtde.com/zszx/shufa/191.html', 'sf', 'http://cdtde.com/uploads/allimg/210331/1-2103311G0370-L.jpg', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (8, '王超尘书法作品 以德报德', 'https://www.cdtde.com/zszx/shufa/187.html', 'sf', 'http://cdtde.com/uploads/allimg/210331/1-210331163J60-L.jpg', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (9, '传印法师书法作品 积德累善', 'https://www.cdtde.com/zszx/shufa/188.html', 'sf', 'http://cdtde.com/uploads/allimg/210331/1-2103311A2140-L.jpg', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (10, '王乃壮书法作品 名德重望', 'https://www.cdtde.com/zszx/shufa/189.html', 'sf', 'http://cdtde.com/uploads/allimg/210331/1-2103311A5570-L.jpg', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (11, '欧阳中石书法作品 上德若谷', 'https://www.cdtde.com/zszx/shufa/190.html', 'sf', 'http://cdtde.com/uploads/allimg/210331/1-2103311F2400-L.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (12, '廖静文书法作品 厚德载福', 'https://www.cdtde.com/zszx/shufa/104.html', 'sf', 'http://cdtde.com/uploads/allimg/201116/1-201116211KO04-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (13, '苏园书法作品 德不孤必有邻', 'https://www.cdtde.com/zszx/shufa/103.html', 'sf', 'http://cdtde.com/uploads/allimg/201116/1-20111621164I49-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (14, '王琦书法作品 明德惟馨', 'https://www.cdtde.com/zszx/shufa/101.html', 'sf', 'http://cdtde.com/uploads/allimg/201116/1-20111621144L15-lp.JPG', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (15, '贾若愚书法作品 俭以养德', 'https://www.cdtde.com/zszx/shufa/100.html', 'sf', 'http://cdtde.com/uploads/allimg/201116/1-20111621132W03-lp.jpg', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (16, '王定国书法作品 一心一德', 'https://www.cdtde.com/zszx/shufa/99.html', 'sf', 'http://cdtde.com/uploads/allimg/201116/1-20111621092Dc-lp.JPG', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (17, '邵华泽书法作品', 'https://www.cdtde.com/zszx/shufa/87.html', 'sf', 'http://cdtde.com/uploads/allimg/201105/1-20110522493c00.jpg', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (18, '任法融书法作品', 'https://www.cdtde.com/zszx/shufa/86.html', 'sf', 'http://cdtde.com/uploads/allimg/201105/1-20110523012M47.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (19, '传印法师书法作品', 'https://www.cdtde.com/zszx/shufa/85.html', 'sf', 'http://cdtde.com/uploads/allimg/201105/1-20110523003T46.jpg', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (20, '李铎书法作品', 'https://www.cdtde.com/zszx/shufa/84.html', 'sf', 'http://cdtde.com/uploads/allimg/201105/1-201105225925559.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (21, '张海书法作品', 'https://www.cdtde.com/zszx/shufa/82.html', 'sf', 'http://cdtde.com/uploads/allimg/201105/1-20110522564R17.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (22, '欧阳中石书法作品', 'https://www.cdtde.com/zszx/shufa/81.html', 'sf', 'http://cdtde.com/uploads/allimg/201105/1-20110522540a08.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (23, '苏士澍书法作品', 'https://www.cdtde.com/zszx/shufa/83.html', 'sf', 'http://cdtde.com/uploads/allimg/201105/1-201105225I3E0.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (24, '沈鹏书法作品', 'https://www.cdtde.com/zszx/shufa/80.html', 'sf', 'http://cdtde.com/uploads/allimg/201105/1-2011052253254E.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (25, '李力生书法作品', 'https://www.cdtde.com/zszx/shufa/91.html', 'sf', 'http://cdtde.com/uploads/allimg/201105/1-2011052252424S-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (26, '李洪海书法作品', 'https://www.cdtde.com/zszx/shufa/90.html', 'sf', 'http://cdtde.com/uploads/allimg/201105/1-20110522515V16-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (27, '王祥之书法作品', 'https://www.cdtde.com/zszx/shufa/89.html', 'sf', 'http://cdtde.com/uploads/allimg/201105/1-20110522510X19-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (28, '林凡书法作品', 'https://www.cdtde.com/zszx/shufa/88.html', 'sf', 'http://cdtde.com/uploads/allimg/201105/1-201105225029434-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (29, '牌坊匾 德崇杖国', 'https://www.cdtde.com/zszx/paibian/258.html', 'pb', 'http://cdtde.com/uploads/allimg/210526/1-210526154625I1-lp.jpg', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (30, '牌坊匾 德隆望重', 'https://www.cdtde.com/zszx/paibian/257.html', 'pb', 'http://cdtde.com/uploads/allimg/210526/1-210526154543D4-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (31, '牌坊匾 世德继美', 'https://www.cdtde.com/zszx/paibian/256.html', 'pb', 'http://cdtde.com/uploads/allimg/210526/1-2105261544594U-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (32, '牌坊匾 德孚闾里', 'https://www.cdtde.com/zszx/paibian/255.html', 'pb', 'http://cdtde.com/uploads/allimg/210526/1-210526154410E7-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (33, '牌坊匾 旌德', 'https://www.cdtde.com/zszx/paibian/254.html', 'pb', 'http://cdtde.com/uploads/allimg/210526/1-21052615424YT-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (34, '牌坊匾 淑德稀龄', 'https://www.cdtde.com/zszx/paibian/253.html', 'pb', 'http://cdtde.com/uploads/allimg/210526/1-210526154145U7-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (35, '功德匾 德媲敬姜', 'https://www.cdtde.com/zszx/paibian/65.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-201105221233204-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (36, '功德匾 祖德流芳', 'https://www.cdtde.com/zszx/paibian/64.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-2011052211594O-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (37, '功德匾 懿德永贞', 'https://www.cdtde.com/zszx/paibian/63.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-201105221119106-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (38, '功德匾 德音莫忘', 'https://www.cdtde.com/zszx/paibian/62.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-201105221044938-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (39, '功德匾 淑德并昭', 'https://www.cdtde.com/zszx/paibian/61.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-201105221004L4-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (40, '功德匾 坤德无疆', 'https://www.cdtde.com/zszx/paibian/60.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-20110522092GF-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (41, '功德匾 德著家乡', 'https://www.cdtde.com/zszx/paibian/59.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-201105220U2201-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (42, '功德匾 德重桑梓', 'https://www.cdtde.com/zszx/paibian/58.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-201105220PX23-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (43, '功德匾 德并孟梁', 'https://www.cdtde.com/zszx/paibian/57.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-201105220H5A5-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (44, '功德匾 厚德载福', 'https://www.cdtde.com/zszx/paibian/54.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-201105220643Y0-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (45, '功德匾 懿德延厘', 'https://www.cdtde.com/zszx/paibian/55.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-201105220554240-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (46, '堂号匾 慎德堂', 'https://www.cdtde.com/zszx/paibian/54.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-20110522043C48-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (47, '堂号匾 明德堂', 'https://www.cdtde.com/zszx/paibian/53.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-201105220314160-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (48, '堂号匾 至德堂', 'https://www.cdtde.com/zszx/paibian/52.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-201105220225491-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (49, '堂号匾 崇德堂', 'https://www.cdtde.com/zszx/paibian/51.html', 'pb', 'http://cdtde.com/uploads/allimg/201105/1-201105220114558-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (50, '刘锋篆刻作品 文德武功', 'https://www.cdtde.com/zszx/zhuanke/252.html', 'zk', 'http://cdtde.com/uploads/allimg/210526/1-21052615262S94-lp.jpg', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (51, '焦亮篆刻作品 德尊望重', 'https://www.cdtde.com/zszx/zhuanke/251.html', 'zk', 'http://cdtde.com/uploads/allimg/210526/1-21052615254L24-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (52, '温廷哲篆刻作品 洽博德闻', 'https://www.cdtde.com/zszx/zhuanke/250.html', 'zk', 'http://cdtde.com/uploads/allimg/210526/1-210526152422951-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (53, '黄宁篆刻作品 尊贤尚德', 'https://www.cdtde.com/zszx/zhuanke/249.html', 'zk', 'http://cdtde.com/uploads/allimg/210526/1-210526152330456-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (54, '杨曰修篆刻作品 公修公德 婆修婆德', 'https://www.cdtde.com/zszx/zhuanke/196.html', 'zk', 'http://cdtde.com/uploads/allimg/210331/1-2103311P2280-L.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (55, '赵我将篆刻作品 功德无量', 'https://www.cdtde.com/zszx/zhuanke/195.html', 'zk', 'http://cdtde.com/uploads/allimg/210331/1-2103311KR50-L.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (56, '高氏熊篆刻作品 硕望宿德', 'https://www.cdtde.com/zszx/zhuanke/194.html', 'zk', 'http://cdtde.com/uploads/allimg/210331/1-2103311K0280-L.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (57, '李岚清篆刻作品 自强不息 厚德载物', 'https://www.cdtde.com/zszx/zhuanke/193.html', 'zk', 'http://cdtde.com/uploads/allimg/210331/1-2103311J4130-L.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (58, '胡钦佩篆刻作品', 'https://www.cdtde.com/zszx/zhuanke/77.html', 'zk', 'http://cdtde.com/uploads/allimg/201105/1-201105222455306-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (59, '李向阳篆刻作品', 'https://www.cdtde.com/zszx/zhuanke/78.html', 'zk', 'http://cdtde.com/uploads/allimg/201105/1-201105222534915-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (60, '杨曰修篆刻作品', 'https://www.cdtde.com/zszx/zhuanke/72.html', 'zk', 'http://cdtde.com/uploads/allimg/201105/1-20110522215MM-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (61, '宋致中篆刻作品', 'https://www.cdtde.com/zszx/zhuanke/79.html', 'zk', 'http://cdtde.com/uploads/allimg/201105/1-201105222609192-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (62, '王士忠篆刻作品', 'https://www.cdtde.com/zszx/zhuanke/76.html', 'zk', 'http://cdtde.com/uploads/allimg/201105/1-20110522241bE-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (63, '焦亮篆刻作品', 'https://www.cdtde.com/zszx/zhuanke/75.html', 'zk', 'http://cdtde.com/uploads/allimg/201105/1-2011052223402X-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (64, '温廷哲篆刻作品', 'https://www.cdtde.com/zszx/zhuanke/74.html', 'zk', 'http://cdtde.com/uploads/allimg/201105/1-201105222303B4-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (65, '黄宁篆刻作品', 'https://www.cdtde.com/zszx/zhuanke/73.html', 'zk', 'http://cdtde.com/uploads/allimg/201105/1-201105222233V2-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (66, '朱增泉篆刻作品', 'https://www.cdtde.com/zszx/zhuanke/71.html', 'zk', 'http://cdtde.com/uploads/allimg/201105/1-201105222112235-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (67, '崔志强篆刻作品', 'https://www.cdtde.com/zszx/zhuanke/70.html', 'zk', 'http://cdtde.com/uploads/allimg/201105/1-201105221955533-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (68, '李羊民篆刻作品2', 'https://www.cdtde.com/zszx/zhuanke/69.html', 'zk', 'http://cdtde.com/uploads/allimg/201105/1-201105221920629-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (69, '李羊民篆刻作品', 'https://www.cdtde.com/zszx/zhuanke/68.html', 'zk', 'http://cdtde.com/uploads/allimg/201105/1-201105221R1X3-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (70, '高式熊篆刻作品', 'https://www.cdtde.com/zszx/zhuanke/67.html', 'zk', 'http://cdtde.com/uploads/allimg/201105/1-201105221H62Q-lp.jpg', '0');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (71, '李岚清篆刻作品', 'https://www.cdtde.com/zszx/zhuanke/66.html', 'zk', 'http://cdtde.com/uploads/allimg/201105/1-20110522160EJ-lp.jpg', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (85, '彭勃书法作品 年高德邵', 'https://www.cdtde.com/zszx/shufa/102.html', 'sf', 'https://www.cdtde.com/uploads/allimg/210331/1-2103311GF3956.png', '1');
INSERT INTO chongdetang.collection (id, title, url, type, photo, selected) VALUES (86, '佟韦书法作品 硕望宿德', 'https://www.cdtde.com/zszx/shufa/246.html', 'sf', 'https://www.cdtde.com/uploads/allimg/210526/1-210526144442508.jpg', '1');
