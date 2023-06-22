package br.ufpr.estagio.modulo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.LoginDTO;
import br.ufpr.estagio.modulo.dto.UsuarioDTO;
import br.ufpr.estagio.modulo.enums.EnumTipoUsuario;
import br.ufpr.estagio.modulo.model.Usuario;
import br.ufpr.estagio.modulo.service.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AutenticacaoREST {

    @Autowired
    private UsuarioService usuarioService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generateToken(Long id, EnumTipoUsuario tipo) {
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        Key key = Keys.hmacShaKeyFor(keyBytes);

        Date expiration = new Date(System.currentTimeMillis() + 86400000); // 24 hours from now

        return Jwts.builder()
                .claim("id", id)
                .claim("tipo", tipo)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    @PostMapping("/criarUsuario")
    public ResponseEntity<Object> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        String login = usuarioDTO.getLogin();
        String senha = usuarioDTO.getSenha();
        EnumTipoUsuario tipoUsuario = usuarioDTO.getTipoUsuario();
        String identifier = usuarioDTO.getIdentifier();

        if (login == null || senha == null || tipoUsuario == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados obrigatórios não foram preenchidos.");
        }

        Optional<Usuario> usuarioExiste = usuarioService.buscarUsuarioPorLogin(login);
        if (!usuarioExiste.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Usuário já cadastrado ou já existe outro usuário cadastrado com o mesmo login.");
        }

        String hashedPassword = hashPassword(senha);

        Usuario usuarioNovo = new Usuario();

        usuarioNovo.setLogin(login);
        usuarioNovo.setSenha(hashedPassword);
        usuarioNovo.setTipoUsuario(tipoUsuario);
        usuarioNovo.setIdentifier(identifier);

        usuarioNovo = usuarioService.criarUsuario(usuarioNovo);

        return ResponseEntity.status(HttpStatus.CREATED).body("Novo usuario cadastrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UsuarioDTO usuarioDTO) {
        String login = usuarioDTO.getLogin();
        String senha = usuarioDTO.getSenha();

        if (login != null && !login.isEmpty()) {
            Optional<Usuario> usuarioFind = usuarioService.buscarUsuarioPorLogin(login);
            if (usuarioFind.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum usuário encontrado com esse login");
            } else {
                Usuario usuario = usuarioFind.get();
                if (passwordEncoder.matches(senha, usuario.getSenha())) {
                    String token = generateToken(usuario.getId(), usuario.getTipoUsuario());
                    LoginDTO loginSucesso = new LoginDTO();
                    loginSucesso.setId(usuario.getId());
                    loginSucesso.setLogin(usuario.getLogin());
                    loginSucesso.setTipoUsuario(usuario.getTipoUsuario());
                    loginSucesso.setToken(token);
                    loginSucesso.setIdentifier(usuario.getIdentifier());
                    return ResponseEntity.status(HttpStatus.CREATED).body(loginSucesso);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados obrigatórios não foram preenchidos.");
        }
    }

}
