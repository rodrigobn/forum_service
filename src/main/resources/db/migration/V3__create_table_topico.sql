create table topico(
    id bigint not null auto_increment primary key,
    titulo varchar(50) not null,
    mensagem varchar(500) not null,
    data_criacao datetime not null,
    status varchar(20) not null,
    curso_id bigint not null,
    autor_id bigint not null,
    foreign key (curso_id) references curso(id),
    foreign key (autor_id) references usuario(id)
);