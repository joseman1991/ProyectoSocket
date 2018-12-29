drop database if exists socket_app;

create database socket_app; 

use socket_app;

create table perfiles(
idperfil int primary key not null,
nombreperfil character varying(13) not null,
puerto int

);
insert into perfiles values(1,'ADMINISTRADOR',6500);
insert into perfiles values(2,'USUARIO',5000);


create table usuarios(
nombreusuario varchar(30) primary key not null,
clave varchar(16) not null,
nombres varchar(25) not null,
apellidos varchar(25) not null,
idperfil int not null,
constraint fk_pe_u foreign key (idperfil) references perfiles(idperfil)  on update cascade on delete restrict
);

insert into usuarios values('kevin','123456','KEVIN','MEJÍA',1);
