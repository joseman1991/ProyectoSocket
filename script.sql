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

select * from usuarios;

select * from perfiles;


create table categorias
(
  codcategoria integer not null auto_increment,
  descripcion  character varying(40) not null UNIQUE,
  constraint pkey_codcategoria primary key(codcategoria)  
);


insert into categorias values(default,'MEDICINA');
insert into categorias values(default,'VITUALLAS');
insert into categorias values(default,'EMBUTIDOS');

create table subcategorias
(
  codcategoria integer not null ,
  codsubcategoria integer not null auto_increment,
  descripcion  character varying(40) not null UNIQUE,
  constraint pkey_codsubcategoria primary key(codsubcategoria),
  constraint fkey_codcategoria foreign key(codcategoria) references categorias(codcategoria) on update cascade on delete restrict
);

insert into subcategorias values (1,default,'VITAMINAS');
insert into subcategorias values (1,default,'ANTIGRIPALES');
insert into subcategorias values (2,default,'PROVISIONES');
insert into subcategorias values (3,default,'CARNES');
insert into subcategorias values (3,default,'JAMON');

create table articulos
(
  codcategoria integer not null,
  codsubcategoria integer not null,
  codarticulo character varying(6) not null,
  descripcion  character varying(40) not null,
  grabaiva character default 'N',
  costo float default 0,
  pvp float default 0,
  stock float default 0,
  estado char default 'A',
  constraint pkey_codarticulo primary key(codarticulo),
  constraint fkey_codcategoria2 foreign key(codcategoria) references categorias(codcategoria) on update cascade on delete restrict,
  constraint fkey_codsubcategoria foreign key(codsubcategoria) references subcategorias(codsubcategoria) on update cascade on delete restrict 
);

