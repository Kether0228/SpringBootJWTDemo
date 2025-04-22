CREATE TABLE IF NOT EXISTS `user`(
                     user_id INT primary key AUTO_INCREMENT,
                     `user` VARCHAR(64),
                     password VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS user_role(
                          user_role_id INT primary key AUTO_INCREMENT,
                          user_id INT,
                          role_id INT
);

CREATE TABLE IF NOT EXISTS role(
                     role_id INT primary key AUTO_INCREMENT,
                     role VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS role_permission (
                                 role_permission INT primary key AUTO_INCREMENT,
                                 role_id INT,
                                 permission_id INT
);

CREATE TABLE IF NOT EXISTS permission (
                            permission_id INT primary key AUTO_INCREMENT,
                            permission_name VARCHAR(32)
);