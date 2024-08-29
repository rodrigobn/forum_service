create table usuario(
    id bigint not null auto_increment primary key,
    nome varchar(50) not null,
    email varchar(50) not null,
    senha varchar(50) not null
);

insert into usuario (id, nome, email, senha) values (1, 'Fulano', 'fulano@mail.com', '123456');
insert into usuario (id, nome, email, senha) values (2, 'Ciclano', 'ciclano@mail.com', '123456');
insert into usuario (id, nome, email, senha) values (3, 'Beltrano', 'beltrano@mail.com', '123456');
insert into usuario (id, nome, email, senha) values (4, 'João', 'joao@mail.com', '123456');
insert into usuario (id, nome, email, senha) values (5, 'Maria', 'maria@mail.com', '123456');
insert into usuario (id, nome, email, senha) values (6, 'José', 'jose@mail.com', '123456');
insert into usuario (id, nome, email, senha) values (7, 'Ana', 'ana@mail.com', '123456');
insert into usuario (id, nome, email, senha) values (8, 'Paulo', 'paulo@mail.com', '123456');
insert into usuario (id, nome, email, senha) values (9, 'Pedro', 'pedro@mail.com', '123456');
insert into usuario (id, nome, email, senha) values (10, 'Lucas', 'lucas@mail.com', '123456');