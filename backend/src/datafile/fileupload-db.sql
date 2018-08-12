use manager;

CREATE TABLE t_code
(
  id INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  type1 VARCHAR(256),
  type2 VARCHAR(256),
  name VARCHAR(256),
  value VARCHAR(512),
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  create_user VARCHAR(256),
  update_time TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL,
  update_user VARCHAR(256)
);
CREATE TABLE t_file
(
  id INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  group_id INT(10) NOT NULL,
  name VARCHAR(1024) NOT NULL,
  path VARCHAR(1024) NOT NULL,
  owner VARCHAR(256) NOT NULL,
  type VARCHAR(128) NOT NULL,
  size DOUBLE NOT NULL,
  pic VARCHAR(256),
  status CHAR(4) NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  create_user VARCHAR(256) NOT NULL
);
CREATE TABLE t_group
(
  id INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(256) NOT NULL,
  status CHAR(1) NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  create_user VARCHAR(128),
  update_time TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL,
  update_user VARCHAR(128)
);
CREATE UNIQUE INDEX uk_gropu_name ON t_group (name);
CREATE TABLE t_log
(
  id INT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  file_name VARCHAR(256) NOT NULL,
  file_path VARCHAR(1024) NOT NULL,
  action VARCHAR(256) NOT NULL,
  account VARCHAR(128) NOT NULL COMMENT '鎵ц浜鸿处鍙�',
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  create_user VARCHAR(256) NOT NULL
);
CREATE TABLE t_menu
(
  id INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(128) NOT NULL,
  type VARCHAR(128) NOT NULL,
  parent_id VARCHAR(128),
  menu_id VARCHAR(128) NOT NULL,
  sort INT(4),
  status INT(1) DEFAULT '0' NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  create_user VARCHAR(128)
);
CREATE UNIQUE INDEX menu_id ON t_menu (menu_id);
CREATE TABLE t_permission
(
  id INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(256) NOT NULL,
  type VARCHAR(128) NOT NULL,
  status CHAR(1) NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  create_user VARCHAR(256),
  update_time TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL,
  update_user VARCHAR(256)
);
CREATE UNIQUE INDEX uk_perm_name ON t_permission (name);
CREATE TABLE t_role
(
  id INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(128) NOT NULL,
  type VARCHAR(128) NOT NULL,
  status CHAR(4) NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  create_user VARCHAR(256) NOT NULL,
  update_time TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL,
  update_user VARCHAR(256)
);
CREATE UNIQUE INDEX t_role_name ON t_role (name);
CREATE TABLE t_role_menu
(
  id INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  role_id INT(10) NOT NULL,
  menu_id INT(10) NOT NULL
);
CREATE UNIQUE INDEX role_id ON t_role_menu (role_id, menu_id);
CREATE TABLE t_role_perm
(
  id INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  role_id INT(10) NOT NULL,
  perm_id INT(10) NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  create_user VARCHAR(256) NOT NULL
);
CREATE UNIQUE INDEX uk_rid_pid ON t_role_perm (role_id, perm_id);
CREATE TABLE t_user
(
  id INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  account VARCHAR(128) NOT NULL,
  password VARCHAR(128) NOT NULL,
  name VARCHAR(128) NOT NULL,
  sex CHAR(1) DEFAULT '0' NOT NULL,
  age INT(4) DEFAULT '1' NOT NULL,
  phone VARCHAR(15),
  email VARCHAR(512),
  status CHAR(1) DEFAULT '0' NOT NULL,
  note TEXT,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  create_user VARCHAR(128) NOT NULL,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  update_user VARCHAR(128) NOT NULL
);
CREATE UNIQUE INDEX uk_user_account ON t_user (account);
CREATE TABLE t_user_role_group
(
  id INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_id INT(10) NOT NULL,
  role_id INT(10),
  group_id INT(10),
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  create_user VARCHAR(256)
);
CREATE UNIQUE INDEX uk_uid_rid_gid ON t_user_role_group (user_id, role_id, group_id);

INSERT INTO manager.t_user (account, password, name, sex, age, phone, email, status, note, create_time, create_user, update_time, update_user) VALUES ('admin', '$2a$10$aalHYXvh1tcNIJoKARxtC.kUUWlKcUys.yJPeA1k/IBJQ1o1agLyq', 'admin', '1', 29, '13888888888', 'admin@tmple.com', '0', 'note', '2018-07-21 16:00:38', 'admin', '2018-07-21 16:00:38', 'admin');