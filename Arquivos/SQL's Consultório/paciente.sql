﻿create table paciente(
		 codigo numeric(6) NOT NULL DEFAUlT nextval('seq1paciente'),
           nome varchar(200),
          email varchar(200),
        fumante boolean,
	 alcoolatra boolean,
		  ativo boolean,
	codConvenio numeric(6),
 ultimaConsulta date,
   dtNascimento date,
     dtCadastro date,
  dtAtualizacao date,
       telefone varchar(50),
             RG varchar(20),
            CPF varchar(20),
           sexo varchar(200),
       endereco varchar(200),
		 numero varchar(20),
	complemento varchar(100),
			CEP varchar(20), 
		 bairro varchar(100),
		 cidade varchar(100),
			 UF varchar(2),
			obs varchar(1000),
	caminhoFoto varchar(200),
	 tentativas varchar(2),
			PRIMARY KEY (codigo))