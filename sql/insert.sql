INSERT INTO users(username, email, password, created_at) values ('admin', 'admin@novprod.it', '$2a$10$oMiUOq5ToRfUI/Zprg5nE.qt8nT9KKJZoDBu1SIWuj.UGx8aRHwxS', '20250507');

INSERT INTO roles(name) values ('ROLE_ADMIN');
INSERT INTO roles(name) values ('ROLE_REVISOR');
INSERT INTO roles(name) values ('ROLE_WRITER');
INSERT INTO roles(name) values ('ROLE_USER');

INSERT INTO users_roles(user_id, role_id) values (1, 1);

INSERT INTO categories(name) values ('politica'), ('economia'), ('food&drink'), ('sport'), ('intrattenimento'), ('tecnologia');
