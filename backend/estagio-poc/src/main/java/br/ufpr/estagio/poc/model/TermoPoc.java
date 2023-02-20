package br.ufpr.estagio.poc.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
public class TermoPoc {
	private long id;
	private String statusTermo;
	private String etapaFluxo;
	private String grrAluno;
	private String tipoEstagio;
	private boolean estagioUfpr;
	private int jornadaDiaria;
	private int jornadaSemanal;
	private Date dataInicio;
	private Date dataTermino;
	private float valorBolsa;
	private float valorTransporte;
	private String nomeSupervisor;
	private String telefoneSupervisor;
	private String formacaoSupervisor;
	private String nomeOrientador;
	private String departamentoOrientador;
	private String atividadesEstagio;
	private String nomeContratante;
	private String tipoContratante;
	private String cnpjContratente;
	private String telefoneContratante;
	private String numeroApolice;
	private String enderecoContratente;
	private String cidadeContratante;
	private String estadoContratante;
}