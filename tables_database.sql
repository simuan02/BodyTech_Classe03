drop database if exists BodyTech;
create database BodyTech;
use BodyTech;


create table utente(
	codiceFiscale char(16) primary key,
    nome varchar(40) not null,
    cognome varchar(40) not null,
	pass varchar(32) not null
);

create table istruttore(
	matricolaIstruttore char(10) primary key,
    nome varchar(40) not null,
    cognome varchar(40) not null,
	pass varchar(32) not null,
    specializzazione char(30)
);

create table amministratore(
	codice int(32) primary key,
	nome varchar(40) not null,
    cognome varchar(40) not null,
	pass varchar(32) not null
);

create table schedaAllenamento(
	idScheda int(32) primary key auto_increment,
    dataInizio datetime not null,
    dataCompletamento datetime not null,
    tipo char(30) not null,
    utente char(16) not null,
    foreign key(utente) references utente(codiceFiscale)
);

create table esercizio(
	nomeEsercizio varchar(30) primary key,
    descrizione varchar(245)
);

create table esercizioAllenamento(
	schedaAllenamento int(32) not null auto_increment,
    esercizio varchar(30) not null,
    volume char(30) not null,
    foreign key(schedaAllenamento) references schedaAllenamento(idScheda),
    foreign key(esercizio) references esercizio(nomeEsercizio),
    primary key(schedaAllenamento, esercizio)
);

create table richiestaModificaScheda(
	idRichiesta int(32) primary key auto_increment,
    messaggio varchar(254) not null,
    utente char(16) not null,
    esito bool not null,
    foreign key(utente) references utente(codiceFiscale)
);
