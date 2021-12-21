# mall

> mall 电商系统 ， jsp + servlet + mysql 作为主要技术的 java 项目， 内含手写的 mybatis 和 springmvc 框架，仅供参考，小白勿入。

## 在线网站参考
如果 404 代表不对外开放     
[mall.enncy.cn](mall.enncy.cn) 

## 技术选型

- 前端 ： bootstrap + jq
- 后端 ：tomcat + jsp + servlet + mysql

## 数据库设计

用户表

```mysql
create table user(
    id                 int auto_increment primary key,
    create_time        mediumtext                  not null comment '创建时间',
    update_time        mediumtext                  not null comment '更新时间',
    nickname           varchar(20)                 null comment '昵称',
    account            varchar(20)                 not null comment '账号',
    password           varchar(20)                 not null comment '密码',
    role               varchar(20)                 not null comment '权限',
    email              varchar(255)                not null comment '邮箱',
    active             tinyint(1)     default 0    null comment '是否激活',
    avatar             varchar(255)                null comment '头像',
    profile            varchar(255)                null comment '个人简介',
    balance            decimal(20, 2) default 0.00 null comment '余额',
    default_address_id int            default 0    null comment '默认地址id',
    constraint user_account_uindex
        unique (account),
    constraint user_email_uindex
        unique (email)
) comment '用户表';

```

地址表

```mysql
create table address
(
    id          int auto_increment primary key,
    user_id     int          not null comment '用户id',
    phone       varchar(20)  not null comment '电话号码',
    alias       varchar(20)  not null comment '别名',
    receiver    varchar(20)  not null comment '收件人名称',
    detail      varchar(255) not null comment '详细地址',
    zip_code    varchar(20)  not null comment '邮编地址',
    create_time mediumtext   not null comment '创建时间',
    update_time mediumtext   not null comment '更新时间',
    constraint address_alias_id_uindex
        unique (alias, id)
) comment '地址';
```

商品表

```mysql
create table goods(
    id             int auto_increment          primary key,
    price          decimal(10, 2)              not null comment '价格',
    description    text                        not null comment '商品描述',
    img            text                        null comment '图片',
    selling        tinyint        default 0    not null comment '是否上架',
    stock          int            default 0    not null comment '库存',
    tag_id         int                         not null comment '标签id',
    discount_price decimal(10, 2) default 0.00 null comment '折扣价',
    views          int            default 0    null comment '浏览次数',
    update_time    mediumtext                  not null,
    create_time    mediumtext                  not null
)  comment '商品表';
```

评论表

```mysql
create table comment
(
    id          int auto_increment
        primary key,
    user_id     int        not null comment '用户id',
    goods_id    int        not null comment '商品id',
    parent_id   int        null comment '回复id',
    content     text       not null comment '评论',
    create_time mediumtext not null,
    update_time mediumtext not null
)
    comment '评论表';
```

标签表

```mysql
create table tag(
    id          int auto_increment primary key,
    count       int default 0 null comment '被引用的次数',
    name        varchar(20)   not null comment '标签名',
    create_time mediumtext    not null comment '更新时间',
    update_time mediumtext    not null comment '更新时间',
    constraint tag_name_uindex
        unique (name)
) comment '标签';
```

购物车表

```mysql
create table cart(
    id          int auto_increment primary key,
    user_id     int        not null comment '用户id',
    goods_id    int        not null comment '商品id',
    count       int        not null comment '数量',
    create_time int        not null,
    update_time mediumtext not null
) comment '购物车表';
```

订单表

```mysql
create table `order`(
    id             int auto_increment primary key,
    uid            varchar(255)   not null comment '订单编号',
    user_id        int            not null comment '用户id',
    address_detail varchar(255)   not null comment '地址详情',
    status         varchar(20)    not null comment '状态',
    total_price    decimal(10, 2) not null comment '总付款',
    update_time    mediumtext     not null comment '更新时间',
    create_time    mediumtext     not null comment '创建时间',
    constraint order_uid_uindex
        unique (uid)
) comment '订单表';
```

订单详情表

```mysql
create table order_details(
    id          int auto_increment  primary key,
    order_uid   varchar(255)        not null comment '订单编号',
    goods_id    int                 not null comment '商品id',
    description varchar(255)        not null comment '商品名',
    price       decimal(10, 2)      not null comment '商品单价',
    img         text                null comment '商品图片',
    count       int                 not null comment '数量',
    update_time mediumtext          not null,
    create_time mediumtext          not null
)  comment '订单详情表';
```
