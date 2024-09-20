CREATE TABLE usuario_role (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    usuario_id bigint NOT NULL,
    role_id bigint NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

INSERT INTO usuario_role (id, usuario_id, role_id) VALUES (1, 1, 1);
INSERT INTO usuario_role (id, usuario_id, role_id) VALUES (2, 1, 2);
INSERT INTO usuario_role (id, usuario_id, role_id) VALUES (3, 2, 2);