package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "pessoa", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Pessoa implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "telefone")
	private String telefone;
	
	//@OneToOne
	//@JoinColumn(name="endereco")
	@OneToOne(mappedBy="pessoa")
	private Endereco endereco;
}
