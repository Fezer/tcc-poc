//package br.ufpr.estagio.modulo.model;
//
//import java.io.Serializable;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import jakarta.persistence.UniqueConstraint;
//
//
//@Entity
//@Table(name = "TipoSiga", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
//public class TipoSiga implements Serializable{
//	
//	private static final long serialVersionUID = 1L;
//	
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//    
//    @Column(name = "tipo")
//    private String tipo;
//
//	public TipoSiga() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	public TipoSiga(Long id, String tipo) {
//		super();
//		this.id = id;
//		this.tipo = tipo;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getTipo() {
//		return tipo;
//	}
//
//	public void setTipo(String tipo) {
//		this.tipo = tipo;
//	}
//
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
//
//    
//}