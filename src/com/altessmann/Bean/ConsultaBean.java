/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.Bean;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Guilherme
 */
public class ConsultaBean {

    private int codigo, tentativas, codPaciente, codMedico, codFuncionario, codTipoConsulta, codConvenio, codExame, numeroFicha, tipo;
    private Time horario;
    private Date dtConsulta,dataLimiteVolta;
    private double valorConsulta, peso, pressao, altura, convenioDesconto;
    private String prescricao, diagnostico, atestadoTexto, convenioNome, tipoConsultaNome, medicoNome, funcionarioNome, exameNome, pacienteNome, pacienteSexo, pacienteEmail, pacienteTelefone, pacienteVicio, atestadoTipo, sigla, status, atestadoDias;
    private boolean finalizada, concluida, reqExame, foiChamado;
    private byte[] foto;

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    
    
    

    public Date getDataLimiteVolta() {
        return dataLimiteVolta;
    }

    public void setDataLimiteVolta(Date dataLimiteVolta) {
        this.dataLimiteVolta = dataLimiteVolta;
    }

    public boolean isFoiChamado() {
        return foiChamado;
    }

    public void setFoiChamado(boolean foiChamado) {
        this.foiChamado = foiChamado;
    }

    public boolean isReqExame() {
        return reqExame;
    }

    public void setReqExame(boolean reqExame) {
        this.reqExame = reqExame;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getNumeroFicha() {
        return numeroFicha;
    }

    public void setNumeroFicha(int numeroFicha) {
        this.numeroFicha = numeroFicha;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getTentativas() {
        return tentativas;
    }

    public void setTentativas(int tentativas) {
        this.tentativas = tentativas;
    }

    public int getCodPaciente() {
        return codPaciente;
    }

    public void setCodPaciente(int codPaciente) {
        this.codPaciente = codPaciente;
    }

    public int getCodMedico() {
        return codMedico;
    }

    public void setCodMedico(int codMedico) {
        this.codMedico = codMedico;
    }

    public int getCodFuncionario() {
        return codFuncionario;
    }

    public void setCodFuncionario(int codFuncionario) {
        this.codFuncionario = codFuncionario;
    }

    public int getCodTipoConsulta() {
        return codTipoConsulta;
    }

    public void setCodTipoConsulta(int codTipoConsulta) {
        this.codTipoConsulta = codTipoConsulta;
    }

    public int getCodConvenio() {
        return codConvenio;
    }

    public void setCodConvenio(int codConvenio) {
        this.codConvenio = codConvenio;
    }

    public int getCodExame() {
        return codExame;
    }

    public void setCodExame(int codExame) {
        this.codExame = codExame;
    }

    public String getAtestadoDias() {
        return atestadoDias;
    }

    public void setAtestadoDias(String atestadoDias) {
        this.atestadoDias = atestadoDias;
    }

    public Time getHorario() {
        return horario;
    }

    public void setHorario(Time horario) {
        this.horario = horario;
    }

    public Date getDtConsulta() {
        return dtConsulta;
    }

    public void setDtConsulta(Date dtConsulta) {
        this.dtConsulta = dtConsulta;
    }

    public double getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(double valorConsulta) {
        this.valorConsulta = valorConsulta;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getPressao() {
        return pressao;
    }

    public void setPressao(double pressao) {
        this.pressao = pressao;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getConvenioDesconto() {
        return convenioDesconto;
    }

    public void setConvenioDesconto(double convenioDesconto) {
        this.convenioDesconto = convenioDesconto;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getAtestadoTexto() {
        return atestadoTexto;
    }

    public void setAtestadoTexto(String atestadoTexto) {
        this.atestadoTexto = atestadoTexto;
    }

    public String getConvenioNome() {
        return convenioNome;
    }

    public void setConvenioNome(String convenioNome) {
        this.convenioNome = convenioNome;
    }

    public String getTipoConsultaNome() {
        return tipoConsultaNome;
    }

    public void setTipoConsultaNome(String tipoConsultaNome) {
        this.tipoConsultaNome = tipoConsultaNome;
    }

    public String getMedicoNome() {
        return medicoNome;
    }

    public void setMedicoNome(String medicoNome) {
        this.medicoNome = medicoNome;
    }

    public String getFuncionarioNome() {
        return funcionarioNome;
    }

    public void setFuncionarioNome(String funcionarioNome) {
        this.funcionarioNome = funcionarioNome;
    }

    public String getExameNome() {
        return exameNome;
    }

    public void setExameNome(String exameNome) {
        this.exameNome = exameNome;
    }

    public String getPacienteNome() {
        return pacienteNome;
    }

    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }

    public String getPacienteSexo() {
        return pacienteSexo;
    }

    public void setPacienteSexo(String pacienteSexo) {
        this.pacienteSexo = pacienteSexo;
    }

    public String getPacienteEmail() {
        return pacienteEmail;
    }

    public void setPacienteEmail(String pacienteEmail) {
        this.pacienteEmail = pacienteEmail;
    }

    public String getPacienteTelefone() {
        return pacienteTelefone;
    }

    public void setPacienteTelefone(String pacienteTelefone) {
        this.pacienteTelefone = pacienteTelefone;
    }

    public String getPacienteVicio() {
        return pacienteVicio;
    }

    public void setPacienteVicio(String pacienteVicio) {
        this.pacienteVicio = pacienteVicio;
    }

    public String getAtestadoTipo() {
        return atestadoTipo;
    }

    public void setAtestadoTipo(String atestadoTipo) {
        this.atestadoTipo = atestadoTipo;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

}
