create table usuario(
    id bigint not null auto_increment primary key,
    nome varchar(50) not null,
    email varchar(50) not null,
    senha varchar(255) not null
);

insert into usuario (id, nome, email, senha) values (1, 'Admin', 'admin@mail.com', '$2a$06$C26JaGb5korSUE7ecCBMFu1jxSuGZ9gLLrff0wlyT00tzt9iRHrei');
insert into usuario (id, nome, email, senha) values (2, 'Ciclano', 'ciclano@mail.com', '$2a$06$C26JaGb5korSUE7ecCBMFu1jxSuGZ9gLLrff0wlyT00tzt9iRHrei');
insert into usuario (id, nome, email, senha) values (3, 'Beltrano', 'beltrano@mail.com', '$2a$06$C26JaGb5korSUE7ecCBMFu1jxSuGZ9gLLrff0wlyT00tzt9iRHrei');

-- Ajuste do auto incremento para o próximo valor desejado
ALTER TABLE curso AUTO_INCREMENT = 4;