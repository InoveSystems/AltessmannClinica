create table exame(
	codigo numeric(6) NOT NULL,
	nome text,
	codTipoExame numeric(6),
	codMedico numeric(6),
	codPaciente numeric(6),
	descricao text,
	dtRequisicao date,
	dtLaudo date,
	laudo text,
	dtAtualizacao date,
	dtCriacao date,
	maiorNumeroArquivo numeric(6),
	tentativas numeric(6),
	PRIMARY KEY(codigo)
)