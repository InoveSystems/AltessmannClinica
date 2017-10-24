create table atualizacao(
		 codigo numeric(6) NOT NULL,          
		 atualizacao boolean,
		 versao text,
	         PRIMARY KEY (codigo));
insert into atualizacao(codigo,atualizacao,versao) values('1','TRUE','1.0');