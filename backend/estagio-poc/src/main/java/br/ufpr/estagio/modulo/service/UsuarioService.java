package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.dto.EnderecoDTO;
import br.ufpr.estagio.modulo.model.Endereco;
import br.ufpr.estagio.modulo.model.Usuario;
import br.ufpr.estagio.modulo.repository.EnderecoRepository;
import br.ufpr.estagio.modulo.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	public Usuario criarUsuario(Usuario usuario) {
		return usuarioRepo.save(usuario);
	}

	public Optional<Usuario> buscarUsuarioPorId(Long id) {
		return usuarioRepo.findById(id);
	}
	
	public Optional<Usuario> buscarUsuarioPorLogin(String login) {
		return usuarioRepo.findByLogin(login);
	}

	public Usuario atualizarUsuario(Usuario usuario) {
        return usuarioRepo.save(usuario);
	}

	public void excluirUsuario(Usuario usuario) {
		usuarioRepo.delete(usuario);
	}
	
}
