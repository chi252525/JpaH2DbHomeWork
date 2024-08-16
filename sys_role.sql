
DROP TABLE IF EXISTS `user`;
CREATE TABLE user
  (
     id                BIGINT PRIMARY KEY auto_increment,
     create_time_stamp TIMESTAMP,
     update_time_stamp TIMESTAMP,
     name              VARCHAR(255) NOT NULL,
     password          VARCHAR(255) NOT NULL,
     email_address     VARCHAR(255)
  );

INSERT INTO user
            (create_time_stamp,
             update_time_stamp,
             name,
             password,
             email_address)
VALUES      (Now(),
             Now(),
             'admin',
             '1234',
             'chi252525@hotmail.com.tw');

INSERT INTO user
            (create_time_stamp,
             update_time_stamp,
             name,
             password,
             email_address)
VALUES      (Now(),
             Now(),
             'user',
             '1234',
             'chi2525252@hotmail.com.tw');


DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE sys_role
  (
     `id`   BIGINT(20) NOT NULL,
     `name` CHAR(50) NOT NULL
  );

INSERT INTO sys_role
            (id,
             name)
VALUES     (1,
            'ROLE_ADMIN');

INSERT INTO sys_role
            (id,
             name)
VALUES     (2,
            'ROLE_USER');
DROP TABLE IF EXISTS `sys_role_privilege_mapping`;
CREATE TABLE sys_role_privilege_mapping
  (
     `id`          BIGINT(20) NOT NULL,
     `sys_role_id` BIGINT(20) NOT NULL,
     `user_id`     BIGINT(20) NOT NULL
  );

INSERT INTO sys_role_privilege_mapping
            (id,
             sys_role_id,
             user_id)
VALUES     (1,
            1,
            1);

INSERT INTO sys_role_privilege_mapping
            (id,
             sys_role_id,
             user_id)
VALUES     (2,
            2,
            2); 