create table tb_user
(
    id          int8         not null primary key comment '主键',
    password    char(60)     not null comment '密码',
    phone       char(11)     not null unique comment '电话',
    nickname    varchar(255) not null comment '昵称',
    role        int1 check ( role in (0, 1, 2))
                             not null default 0 comment '0 for root, 1 for normal, 2 for blocked',
    create_time datetime     not null default NOW() comment '创建时间',
    update_time datetime     not null default NOW() on update NOW() comment '更新时间'
) comment '用户表';

-- 下面是预备的, 主要是积分兑换奖品系统

alter table tb_user
    add points int4 not null check ( points >= 0 ) default 0 comment '积分';


create table tb_consultation_content
(
    user_id             int8 primary key                not null,
    lower_bound         int4 check ( lower_bound >= 0 ) not null default 0,
    upper_bound         int4                            not null default 2147483647 comment 'check ( upper_bound >= lower_bound )',
    preferred_car_model varchar(63)                     not null default '任意',
    main_use_case       varchar(63)                     not null default '任意',
    preferred_fuel_type varchar(63)                     not null default '任意',
    preferred_brand     varchar(63)                     not null default '任意',
    other_requirements  varchar(255)                    not null default '',
    constraint tb_consultation_content_user_id_fk foreign key (user_id) references tb_user (id)
) comment '咨询内容';


create table tb_feedback
(
    id          int8 primary key not null,
    user_id     int8             not null default 0,
    text        varchar(255)     not null default '',
    `read`      bool             not null default false comment '已读',
    create_time datetime         not null default NOW(),
    constraint tb_feedback_user_id_fk foreign key (user_id) references tb_user (id)
) comment '反馈';

create table tb_hot_word
(
    id        int8 primary key not null,
    word      varchar(63)      not null,
    frequency int4             not null default 1
) comment '热词';


create table tb_user_action_log
(
    id                int8 primary key not null,
    user_id           int8             not null,
    ip_address        char(32)         not null,
    request_url       varchar(255)     not null,
    request_method    char(8) check ( request_method in ('GET', 'POST', 'PUT', 'DELETE'))
                                       not null,
    request_time      datetime         not null,
    request_time_cost int4             not null comment '单位ms',
    constraint tb_user_action_log_user_id_fk foreign key (user_id) references tb_user (id)
) comment '用户行为日志';


create table tb_gift
(
    id           int8 primary key not null,
    cost         int4             not null,
    title        varchar(255)     not null default '',
    description  TEXT             not null,
    storage      int4             not null default 0,
    picture_url1 varchar(255)     not null,
    picture_url2 varchar(255)     not null,
    picture_url3 varchar(255)     not null
) comment '礼品表';
-- 下面的不启用
create table tb_item
(
    id          int8                    not null primary key comment '主键',
    name        varchar(63)             not null comment '商品',
    description varchar(255)            not null unique comment '商品描述',
    picture_url varchar(255)            not null comment '商品图片URL',
    cost        int4 check ( cost >= 0) not null comment '花费的积分',
    create_time datetime                not null default NOW() comment '创建时间',
    update_time datetime                not null default NOW() on update NOW() comment '更新时间'
) comment '商品表';

create table tb_point_record
(
    id          int8         not null primary key comment '主键',
    user_id     int8         not null comment '用户id',
    obtain      int4         not null comment '获取到的积分, 可正可负数,order导致的支出, 值就是负数',
    description varchar(255) not null comment '对获取积分方式的描述, 比如可能是order导致的支出, 可能',
    create_time datetime     not null default NOW() comment '创建时间',
    update_time datetime     not null default NOW() on update NOW() comment '更新时间',
    constraint tb_point_record_user_id_fk foreign key (user_id) references tb_user (id)
) comment '获取积分的记录';

create table tb_order
(
    id              int8     not null primary key comment '主键',
    item_id         int8     not null comment '商品id, 没错, 一个订单只能对应一个商品',
    count           int4     not null check ( count > 0 ) comment '商品数量',
    point_record_id int8     not null comment '对应的积分消费日志的id',
    create_time     datetime not null default NOW() comment '创建时间',
    update_time     datetime not null default NOW() on update NOW() comment '更新时间',
    constraint tb_order_item_id_fk foreign key (item_id) references tb_item (id),
    constraint tb_order_point_record_id_fk foreign key (point_record_id) references tb_point_record (id)
) comment '兑换记录(订单)';
/*一次消费:
  1. 生成tb_point_record
  2. 生成order
  3. 从user那里扣积分
  */