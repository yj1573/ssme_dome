-- 用户表
drop table if EXISTS users;
create table users(
id int(11) UNSIGNED not NULL AUTO_INCREMENT,
user_name varchar(20) DEFAULT '' COMMENT '用户名称',
passwd varchar(20) DEFAULT '' COMMENT '密码',
gmt_create datetime DEFAULT null COMMENT '创建时间',
UNIQUE key user_name (user_name) ,
PRIMARY key (id)
)ENGINE=INNODB charset=utf8mb4;

-- 权限表
drop table if EXISTS authorization;
create table authorization(
id int(11) UNSIGNED not NULL AUTO_INCREMENT,
authorization_name varchar(20) DEFAULT '' COMMENT '权限名称',
pid int(11) UNSIGNED DEFAULT '0' COMMENT '父权限id',
description varchar(100) DEFAULT '' COMMENT '权限说明',
PRIMARY key (id)
)ENGINE=INNODB charset=utf8mb4;

-- 角色表
drop table if EXISTS role;
create table role(
id int(11) UNSIGNED not NULL AUTO_INCREMENT,
role_name varchar(20) DEFAULT '' COMMENT '角色名称',
pid int(11) UNSIGNED DEFAULT '0' COMMENT '父角色id',
gmt_create datetime DEFAULT null COMMENT '创建时间',
PRIMARY key (id)
)ENGINE=INNODB charset=utf8mb4;

-- 分组
drop table if EXISTS groupings;
create table groupings(
id int(11) UNSIGNED not NULL AUTO_INCREMENT,
group_name varchar(20) DEFAULT '' COMMENT '分组名称',
pid int(11) UNSIGNED DEFAULT '0' COMMENT '父组id',
description varchar(100) DEFAULT '' COMMENT '分组说明',
gmt_create datetime DEFAULT null COMMENT '创建时间',
PRIMARY key (id)
)ENGINE=INNODB charset=utf8mb4;


-- 资源表
drop table if EXISTS resource;
create table resource(
id int(11) UNSIGNED not NULL AUTO_INCREMENT,
resource_name varchar(20) DEFAULT '' COMMENT '资源名称',
type tinyint(2) UNSIGNED DEFAULT '0' COMMENT '资源类型',
pid int(11) UNSIGNED DEFAULT '0' COMMENT '父资源id',
description varchar(100) DEFAULT '' COMMENT '资源说明',
gmt_create datetime DEFAULT null COMMENT '创建时间',
PRIMARY key (id)
)ENGINE=INNODB charset=utf8mb4;


-- 关联信息表
drop table if EXISTS authorization_related_info;
create table authorization_related_info(
id int(11) UNSIGNED not null AUTO_INCREMENT,
user_id int(11) UNSIGNED UNSIGNED DEFAULT '0' COMMENT '用户id',
role_id int(11) UNSIGNED UNSIGNED DEFAULT '0' COMMENT '角色id',
authorization_id int(11) UNSIGNED UNSIGNED DEFAULT '0' COMMENT '权限id',
group_id int(11) UNSIGNED UNSIGNED DEFAULT '0' COMMENT '分组id',
related_type tinyint(2) UNSIGNED DEFAULT '0' COMMENT '关联类型',
type tinyint(2) UNSIGNED DEFAULT '0' COMMENT '本条数据类型',
PRIMARY key (id)
)ENGINE=INNODB charset=utf8mb4;





