package br.ufpr.estagio.poc.controller;

import java.net.URI;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.ufpr.estagio.poc.exception.PocException;
import br.ufpr.estagio.poc.model.AlunoPocDTO;
import br.ufpr.estagio.poc.model.Discente;
import br.ufpr.estagio.poc.model.TermoPoc;
import br.ufpr.estagio.poc.repository.TermoPocRepository;
import br.ufpr.estagio.poc.wrapper.DiscenteWrapper;

@CrossOrigin
@RestController
public class AlunoPocREST {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private TermoPocRepository repo;
	
	@GetMapping("/aluno/{termoIdURL}")
	public ResponseEntity<Discente> listarTermo(@PathVariable String termoIdURL){
		try {
			if(termoIdURL.isBlank() || termoIdURL.isEmpty()) {
				throw new PocException(HttpStatus.BAD_REQUEST, "ID do termo não informado!");
			} else {
				Long termoId = Long.parseLong(termoIdURL);
				Optional<TermoPoc> termo = repo.findById(termoId);
				if(termo.isEmpty()) {
					throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
				} else {
					String grr = termo.get().getGrrAluno();
					URI uri = UriComponentsBuilder.fromUriString("https://siga.ufpr.br:8380/siga/api/graduacao/discentes").queryParam("grr", grr).build().toUri();
		            RestTemplate restTemplate = new RestTemplate();
					HttpHeaders headers = new HttpHeaders();
		            headers.set("Authorization", "tokenaqui");
		            HttpEntity<String> entity = new HttpEntity<>(headers);
		            ResponseEntity<DiscenteWrapper> discenteWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DiscenteWrapper.class);
		            Discente discente = discenteWrapper.getBody().getData().getDiscente();
					return ResponseEntity.status(HttpStatus.OK).body(mapper.map(discente, Discente.class));
				}
			}
		}catch (NumberFormatException e) {
			throw new PocException(HttpStatus.BAD_REQUEST, "O ID informado para o termo é o tipo de dado esperado!");
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
}