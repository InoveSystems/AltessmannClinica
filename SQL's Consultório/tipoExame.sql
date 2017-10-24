create table tipoexame(
	codigo numeric(6) NOT NULL DEFAUlT nextval('seq1tipoExame'),
	nome text,
	descricao text,
	dtCriacao date,
	dtAtualizacao date,
	valor float,
	tentativas numeric(5),
	PRIMARY KEY(codigo)
)