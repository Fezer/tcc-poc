package br.ufpr.estagio.poc.controller;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

@CrossOrigin
@RestController
public class AlunoPocREST {
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/aluno/{grr}")
	public ResponseEntity<AlunoPocDTO> listarTermo(@PathVariable String grr){
		try {
		if(grr.isBlank() || grr.isEmpty()) {
			throw new PocException(HttpStatus.BAD_REQUEST, "GRR não informado!");
		} else {
			RestTemplate restTemplate = new RestTemplate();
			URI uri = UriComponentsBuilder.fromUriString("https://siga.ufpr.br:8380/siga/api/graduacao/discentes").queryParam("grr", grr).build().toUri();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "AquiVaiOToken");
			AlunoPocDTO aluno = restTemplate.getForEntity(uri, AlunoPocDTO.class).getBody();
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(aluno, AlunoPocDTO.class));
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