
INSERT INTO role (role)  VALUES ('admin');
INSERT INTO role (role)  VALUES ('user');

INSERT INTO user_role(user_id, role_id) VALUES (1,1);

INSERT INTO permission(permission_name) VALUES ('user:overview');
INSERT INTO permission(permission_name) VALUES ('user:update');
INSERT INTO permission(permission_name) VALUES ('user:delete');
INSERT INTO permission(permission_name) VALUES ('page:admin');
INSERT INTO permission(permission_name) VALUES ('page:user');

INSERT INTO role_permission(role_id, permission_id) VALUES (1,1);
INSERT INTO role_permission(role_id, permission_id) VALUES (1,2);
INSERT INTO role_permission(role_id, permission_id) VALUES (1,3);
INSERT INTO role_permission(role_id, permission_id) VALUES (1,4);
INSERT INTO role_permission(role_id, permission_id) VALUES (1,5);
INSERT INTO role_permission(role_id, permission_id) VALUES (2,5);

--password is test
INSERT INTO `user`(`user`, password) VALUES ('admin', '$2a$10$rzsv.dtRmy7b8oyQNGuVnOIO8SsNQbUN9mpoELF5JnhBVm5hyhXh2');