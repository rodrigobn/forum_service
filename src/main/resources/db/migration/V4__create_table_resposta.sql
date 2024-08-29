create table resposta(
    id bigint not null auto_increment primary key,
    mensagem varchar(500) not null,
    data_criacao datetime not null,
    topico_id bigint not null,
    autor_id bigint not null,
    solucao boolean not null,
    foreign key (topico_id) references topico(id),
    foreign key (autor_id) references usuario(id)
);