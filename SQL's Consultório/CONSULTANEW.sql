create table consulta(
	codigo numeric(6) NOT NULL DEFAUlT nextval('seq1consulta'),
	codPaciente numeric(6),
	codMedico numeric(6),
	codFuncionario numeric(6),
	codTipoConsulta numeric(6),
	codConvenio numeric(6),
	codExame numeric(6),
	numeroFicha numeric(6),
	sigla varchar(3),
	tipo numeric(3),
	foiChamado boolean,
	concluido boolean,
	valorDesconto float(8),
	horario time,
	dataConsulta date,
	dataLimiteVolta date,
	horaMarcacao time,
	atestadoTexto text,
	atestadoTipo text,
	atestadoDias text,
	diagnostico text,
	prescricao text,
	peso float(10),
	status text,
	pressao float(10),
	altura float(10),
	convenioDesconto float(10),
	valorTotal float(10),
	revisao boolean,
	tentativas numeric(6),
PRIMARY KEY(codigo)
)	
	
	
	
	