package br.ufpr.estagio.modulo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.EnderecoDTO;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Endereco;
import br.ufpr.estagio.modulo.service.EnderecoService;

@CrossOrigin
@RestController
@RequestMapping("/endereco")
public class EnderecoREST {
	
	@Autowired
    private EnderecoService enderecoService;

	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/")
	public ResponseEntity<EnderecoDTO> inserir(@RequestBody EnderecoDTO enderecoDTO){
		try {
		Endereco newEndereco = enderecoService.criarEndereco(mapper.map(enderecoDTO, Endereco.class));
		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(newEndereco, EnderecoDTO.class));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
	    } catch(Exception e) {
			e.printStackTrace();
			throw new NotFoundException("Não foi possível criar um novo endereço.");
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<EnderecoDTO>> listarTodos(){
		
		// o try estava sobrescrevendo o lançamento de NotFoundException. por algum motivo,
		// a mesma estrutura, em outra classe, funciona mesmo com o try. vou verificar depois
		//try {
			List<Endereco> lista = enderecoService.listarEnderecos();
			if(lista.isEmpty()) {
				throw new NotFoundException("Nenhum endereço encontrado!");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(lista.stream().map(e -> mapper.map(e, EnderecoDTO.class)).collect(Collectors.toList()));
			}
		/*}catch(Exception e) {
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}*/
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EnderecoDTO> listarEndereco(@PathVariable Long id){
		try {
			Optional<Endereco> enderecoFind = enderecoService.buscarEndercoPorId(id);
		if(enderecoFind.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Endereço não encontrado!");
		} else {
			Endereco endereco = enderecoFind.get();
			EnderecoDTO enderecoDTO = mapper.map(endereco, EnderecoDTO.class);
			return new ResponseEntity<>(enderecoDTO, HttpStatus.OK);
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EnderecoDTO> atualizar(@PathVariable Long id, @RequestBody EnderecoDTO endereco){
		try {
			Optional<Endereco> enderecoFind = enderecoService.buscarEndercoPorId(id);
		if(enderecoFind.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Endereço não encontrado!");
		} else {
			Endereco enderecoAtualizado = enderecoService.atualizarEndereco(enderecoFind.get(), endereco);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(enderecoAtualizado, EnderecoDTO.class));
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<EnderecoDTO> delete(@PathVariable Long id){
		try {
			Optional<Endereco> enderecoFind = enderecoService.buscarEndercoPorId(id);
		if(enderecoFind.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Endereço não encontrado!");
		} else {
			enderecoService.excluirEndereco(enderecoFind.get());
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
}
