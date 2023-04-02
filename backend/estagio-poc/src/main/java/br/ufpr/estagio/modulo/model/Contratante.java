package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

import br.ufpr.estagio.modulo.enums.EnumTipoContratante;

public class Contratante extends Pessoa implements Serializable {
	
	private long id;
	private EnumTipoContratante tipo;
	private String cnpj;
	private String cpf;
	private String representanteEmpresa;
	private ArrayList<Estagio> estagio;

}
