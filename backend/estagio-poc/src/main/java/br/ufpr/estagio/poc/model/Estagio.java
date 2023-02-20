package br.ufpr.estagio.poc.model;

import java.util.ArrayList;
import java.util.Date;

public class Estagio {
	
	private long id;
	private String tipoEstagio;
	private String statusEstagio;
	private boolean estagioUfpr;
	private Aluno aluno;
	private Contratante contratante;
	private Seguradora seguradora;
	private AgenteIntegrador agenteIntegrador;
	private Orientador orientador;
	private Supervisor supervisor;
	private PlanoDeAtividade planoAtividade;
	private Date dataInicio;
	private Date dataTermino;
	private int jornadaDiaria;
	private int jornadaSemanal;
	private float valorBolsa;
	private float valorTransporte;
	private TermoDeEstagio termoDeCompromiss;
	private ArrayList<TermoDeEstagio> termoAdivito;
	private TermoDeRescisao termoDeRescisao;
	private ArrayList<RelatorioDeEstagio> relatorioDeEstagio;
	private FichaDeAvaliacao fichaDeAvaliacao;
	private CertificadoDeEstagio certificadoDeEstagio;
	
}
