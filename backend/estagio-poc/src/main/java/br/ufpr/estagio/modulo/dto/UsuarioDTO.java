package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;

import br.ufpr.estagio.modulo.enums.EnumTipoUsuario;

public class UsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String login;
	private String senha;
	private EnumTipoUsuario tipoUsuario;

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(long id, String login, String senha, EnumTipoUsuario tipoUsuario) {
		super();
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.tipoUsuario = tipoUsuario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public EnumTipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(EnumTipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}
