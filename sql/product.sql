create table product
(
    id           int auto_increment
        primary key,
    name         varchar(50)   not null comment '商品名',
    price        double        not null comment '价格',
    launch_time  date          null comment '上架时间',
    photo        varchar(100)  null comment '商品图片标识符',
    introduction varchar(300)  null comment '商品介绍',
    storage      int default 0 not null comment '商品库存',
    constraint product_id_uindex
        unique (id)
)
    comment '商品表';

INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (26, '古书页款藏书章书签', 19.99, '2018-09-01', 'img/products/product1.jpg', '此款产品以盲盒形式呈现，既可以当挂件，也可以做书签。一德一福两枚书签。人生上上签，德为护身符。每个人都祈愿自己万事顺意，大吉大利。殊不知，一个人的福气都藏在一个人的一言一行当中，德至自然福随。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (27, '德文化书简装签套装', 39.99, '2018-10-01', 'img/products/product2.jpg', '古书页款藏书章书签，这款书签的观赏功能大于其使用价值，以折页形式体现，左侧是书签印章，右侧是印章的古文出处。把文字之美和古书之美相融合。从而实现传播古典之美的理念。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (28, '德字印章书法书签盲盒', 39.99, null, 'img/products/product3.jpg', '以盲盒形式呈现，由红白卡纸组成，即可做书签同时还可做为桌面的装饰画，选取五枚，取五福临门之意。', 2);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (29, '德字印章书法书签盲盒', 39.99, '2019-01-01', 'img/products/product4.jpg', '书签盲盒系列文创，以崇德堂馆内的“百德墙”为灵感，利用收集整理的几百个从甲骨文开始，直到当代伟人书写的“德”为素材，精选出百款做成“德”字书签。以历史为鉴，以汉字为传承，以时代为福田，传播中国之“德”文化。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (30, '种德收福印章书签精装礼盒', 59.99, '2019-09-01', 'img/products/product5.jpg', '种德收福印章书签精装礼盒，以崇德堂馆内收集的几百方印章为灵感素材，字字珠玑的成语，配以精美的包装，方寸间尽显文字之美。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (31, '祥云书签礼盒', 19.99, '2020-05-01', 'img/products/product6.jpg', '祥云图案来源于我国古代的云纹。古人出于对云的敬畏，在纹饰上变化出各种和云有关的图案。云纹有着很多美好的寓意，是人们对万事万物希翼祝福的 心理意愿和生活追求。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (32, '荷花氛围灯', 59.99, '2020-05-01', 'img/products/product7.jpg', '荷花是美好品德的代表之花，出淤泥而不染，灼青莲而不妖。具有忠贞、纯洁、无邪、清正、高尚而谦虚的美德。"灯"是中国传统审美文化不可忽视的组成部分,它有鲜明的审美特征与内涵。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (33, '朱子治家格言灯', 59.99, '2020-05-01', 'img/products/product8.jpg', '《朱子家训》告诫我们如何才能成为一个有道德的人、一个高尚的人、一个有修养的人、一个文明的人。它教导我们的宽容、包容、内敛、内秀及严于律己、宽以待人的美德，彰显了中华文化无比宽广的胸襟和卓尔特立的价值观。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (34, '舍得氛围灯', 59.99, '2020-05-01', 'img/products/product9.jpg', '“舍得”者,实无所舍,亦无所得,是谓“舍得”。“舍得”，最早出自《了凡四训》。佛家认为，万事万物皆在“舍得”之中成就自身。 “舍得”二字，在我国的语言中有着丰富的内涵。佛学认为，舍就是得，得就是舍，如同色即是空、空即是色一样；道家认为，舍就是无为，得就是有为，所谓“无为而无不为”；儒家看来，舍恶以得仁，舍欲而得圣；现代人眼里，“舍”就是付出、是贡献、是投入，“得”是成果、是产出、是认同。所以，“舍得”，就是一种哲学思想的体现，也是人生必然面对的一项选择。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (35, '德福-厚德载福条屏', 39.99, null, 'img/products/product10.jpg', '厚德载福四个字，表面上理解，有德的人有福或有大德，积累德行，就有福报。实际上这句话套用了《易经》坤卦的爻辞“地势坤，君子以厚德载物”。这里说的是大地母亲的品德，她能承载一切，好的不好的、一切灾难、一切垃圾都承载，还能生成一切，为万物生长提供养料、乳汁。是因为大地承载了一切，容忍了一切，滋养了一切，所以才能称得上厚德。', 1);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (36, '德福-杯垫', 29.99, '2021-02-01', 'img/products/product11.jpg', '中国传统文化博大精深充满智慧。荣华富贵不是争来的，是积德来的。人有多少德，就有多少福，无德不得，失德散尽。此款德福杯垫也是在提醒我们每个人明白所有的福气都是从德行中来，种德才能收福。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (37, '千年的祝福', 29.99, '2021-02-01', 'img/products/product12.jpg', '此幅小儿祝寿图由唐代画圣吴道子所创作，后被颜真卿所见题跋：福如东海长流水，寿比南山不老松。而后又有被誉为李斯后小篆第一人的李阳冰看到，并篆刻福乐长寿的印章。到了宋代米芾所见撰写对联：青松鹤舞千载寿，仙桃童乐万年福。后到明代董其昌所见，对以上四位大家进行赞美。此福祝寿图从唐代到明代跨越了825年，距今也有千余年，顾称之为千年的祝福。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (38, '德福门神', 29.99, '2021-05-01', 'img/products/product13.jpg', '德福门神，由“德”与“福”两张组成，内容来源于馆内收藏的藏品——唐代画圣吴道子的墨宝碑拓《祝寿图》及其《老子像碑》以此两幅墨宝为原型，配以祥瑞元素创新重绘，成为寓意深刻且具有当代审美特色的新年画，不仅可在春节张贴悬挂，亦可做为日常家居装饰画。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (39, '种德收福工艺画', 59.99, '2021-05-01', 'img/products/product14.jpg', '王阳明曾说：种树者必培其根，种德者必养其心！一切福田，不离方寸。一个人如果想收获五福圆满人生，就要在心田种下善良的种子，在行动上做一个有社会公德、职业道德、家庭美德、个人品德四德兼备之人，必定会心想事成收获圆满人生。此画由现代3D浮雕，数码打印的工艺，配以金属框制作，不仅具有欣赏价值，同时还具有家风家训的作用。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (40, '当思集纸砖', 19.99, null, 'img/products/product15.jpg', '超厚纸砖，可以满足一年的案头随笔。每页一联。联联有德。愿君以笔代耕，借此以正身心，思责任，思勤奋，思周全.......当思当思', 1);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (41, '当思历', 29.99, '2021-09-01', 'img/products/product16.jpg', '周历一本，寒尽知年。四季轮回，渐远还生。内页图文源于崇德堂的四季风物与李馆长生活点滴感悟，借景观人生，当惜此时景，思美好，思己过，思他恩........当思当思', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (42, '五福红包', 19.99, null, 'img/products/product17.jpg', '第一福是长寿，命不夭折而且福寿绵长；
第二福是富贵，钱财富足而且地位尊贵；
第三福是康宁，身体健康而且心灵安宁；
第四福是好德，生性仁善而且宽厚宁静；
第五福是善终，生命即将结束时，无病无灾，没有牵挂和烦恼，安详自在地离开人间。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (43, '尽管善良檀香线香', 49.99, '2021-10-01', 'img/products/product18.jpg', '以香养性 以馨化德。有文字记载以来，木在我国已经有1500年的历史，它是一种古老、神秘、神奇，具有多种功能的珍稀树种，是大自然恩赐人类的纯天然名贵香料。檀香树，在宗教领域里被誉为“神圣之树”；在风水学里被誉为“招财之树”；在历史上由于象征着权力和地位而被誉为“皇室之树”；在交易市场中由于具有很高的价值被誉为“黄金之树”。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (44, '堂主作品（舍念清净）', 49.99, '2021-10-01', 'img/products/product19.jpg', '舍念并不是让人舍去念头，念头并不舍不掉，而是让自己不加评判，不要对升起的念头有想法，不跟着念头走，保持正念，知道念来念去即可。自性本清净，没有任何多余就能回归清净。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (45, '堂主作品（家有黄金三万两，不敌德育五千言）', 49.99, '2021-10-01', 'img/products/product20.jpg', '古语云：“道德传家，十代以上，耕读传家次之，诗书传家又次之，富贵传家，不过三代。”一个家庭想要长久兴旺，靠的是善良和读书。此幅作品由李陪义馆长撰写此对联，可做为家风家训悬挂在家中的不二选择。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (46, '堂主作品（舍得）', 49.99, '2022-01-01', 'img/products/product21.jpg', '“舍得”者,实无所舍,亦无所得,是谓“舍得”。“舍得”，最早出自《了凡四训》。佛家认为，万事万物皆在“舍得”之中成就自身。 “舍得”二字，在我国的语言中有着丰富的内涵。佛学认为，舍就是得，得就是舍，如同色即是空、空即是色一样；道家认为，舍就是无为，得就是有为，所谓“无为而无不为”；儒家看来，舍恶以得仁，舍欲而得圣；现代人眼里，“舍”就是付出、是贡献、是投入，“得”是成果、是产出、是认同。所以，“舍得”，就是一种哲学思想的体现，也是人生必然面对的一项选择。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (47, '镇尺', 39.99, '2023-07-11', 'img/products/product22.jpg', '南宋李子翚《书斋十咏》所咏镇尺：“抄书防纵逸，界墨作遮阑。妙用谁能识，心端笔自端。”一把镇尺，早已不仅仅是一个镇纸器物那么简单的，更是一件兼具赏玩的艺术品。我们将具有崇德堂匾额博文馆特色的对联刻在了镇尺之上，在实现镇尺基本功能的同时，也赋予了其文化内涵。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (48, '崇德向善T恤', 79.99, '2022-01-01', 'img/products/product23.jpg', '崇德向善T恤，是采用丝网印工艺将崇德堂匾额博物馆的核心宗旨采用印章的图案印到T恤上。其文化意义在于，国无德不兴，人无德不立，大到一个国家，小到每一个人，都要崇尚道德，做一个有道德的人，那么我们的社会必定会是和谐的，国家是昌盛的。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (49, '黄初残碑T恤', 79.99, '2022-01-01', 'img/products/product24.jpg', '《黄初残碑》，三国魏隶书碑刻。此残碑含三块拓片，其中一块被本馆所收藏。因其内容含有“德”与“义”与本馆所传播传承的内容相契合。利用丝网印工艺将此拓片原型印在了T恤上，通过一件T恤就可与古人对话，链接古人的智慧。', 0);
INSERT INTO chongdetang.product (id, name, price, launch_time, photo, introduction, storage) VALUES (50, '丝网印帆布包', 39.99, '2023-07-11', 'img/products/product25.jpg', '大道之行，天下为公是汉语成语，出自西汉·戴圣《礼记·礼运篇》，意思是天下是人们所共有的，把品德高尚的人、有才能的人选出来，(人人)讲求诚信，培养和睦气氛，表达的是一种大同的理想社会。', 0);
