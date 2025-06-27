INSERT INTO users(username, email, password, created_at) values ('admin', 'admin@novprod.it', 'gwsurg2u62372ibxq', '20250507');

INSERT INTO roles(name) values ('ROLE_ADMIN');
INSERT INTO roles(name) values ('ROLE_REVISOR');
INSERT INTO roles(name) values ('ROLE_WRITER');
INSERT INTO roles(name) values ('ROLE_USER');

INSERT INTO users_roles(user_id, role_id) values (1, 1);

INSERT INTO categories(name) values ('politica'), ('economia'), ('food&drink'), ('sport'), ('intrattenimento'), ('tecnologia');
