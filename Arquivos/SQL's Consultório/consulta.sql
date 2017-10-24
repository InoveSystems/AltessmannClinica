create table consulta(
	codigo numeric(6) NOT NULL DEFAUlT nextval('seq1consulta'),
	codPaciente numeric(6),
	codTipoConsulta numeric(4),
	horario time,
	valorConsulta float,
	dtConsulta date,
	prescricao varchar(2000),
	diagnostico varchar(2000),
	dtCriacao date,
	dtAtualizacao date,
	finalizada boolean,
	formaPagamento varchar(2),
	tentativas varchar(2),
	PRIMARY KEY(codigo)
)