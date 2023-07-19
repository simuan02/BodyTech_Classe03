drop schema if exists bodytechdb;

create schema bodytechdb;

use bodytechdb;

create table Utente (
nome char(50),
cognome char(50),
codicefiscale char(16) Primary Key);

insert into Utente values(
"ABC", "DEF", "SBISBIQ");