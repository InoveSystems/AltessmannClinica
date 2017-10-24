create table convenio(
	codigo numeric(6) NOT NULL DEFAUlT nextval('seq1convenio'),
	nome varchar(200),
	porcentagem float(10),
	dtCriacao date,
	dtAtualizacao date,
	tentativas varchar(2),
	PRIMARY KEY(codigo))