package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;
import java.util.List;

public class CursoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String nome;
	private String matricula;
	private String idPrograma;
	private String nivel;
	private int peridiocidade;
	private String turno;
	private List<CoordenadorDTO> coordenador;
	private List<OrientadorDTO> orientador;
	
	public CursoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CursoDTO(long id, String nome, String matricula, String idPrograma, String nivel, int peridiocidade,
			String turno, List<CoordenadorDTO> coordenador, List<OrientadorDTO> orientador) {
		super();
		this.id = id;
		this.nome = nome;
		this.matricula = matricula;
		this.idPrograma = idPrograma;
		this.nivel = nivel;
		this.peridiocidade = peridiocidade;
		this.turno = turno;
		this.coordenador = coordenador;
		this.orientador = orientador;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(String idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public int getPeridiocidade() {
		return peridiocidade;
	}

	public void setPeridiocidade(int peridiocidade) {
		this.peridiocidade = peridiocidade;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public List<CoordenadorDTO> getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(List<CoordenadorDTO> coordenador) {
		this.coordenador = coordenador;
	}

	public List<OrientadorDTO> getOrientador() {
		return orientador;
	}

	public void setOrientador(List<OrientadorDTO> orientador) {
		this.orientador = orientador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
