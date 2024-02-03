create table pacientes(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    telefone varchar(100) not null,
    cns varchar(15) not null unique,

    primary key(id)

);