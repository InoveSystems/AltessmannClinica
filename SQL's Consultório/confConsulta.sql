create table confconsulta(
	codigo numeric(6) NOT NULL DEFAUlT nextval('seq1confconsulta'),
	nome varchar(300),
	valor float(10),
	dtAtualizacao date,
	dtCriacao date,
	tentativas varchar(2),

PRIMARY KEY(codigo))