CREATE Table usuarios(
    id bigint not null auto_increment,
    login varchar(100) not null,
    clave varchar(300) not null,
    
    PRIMARY KEY(id)
);