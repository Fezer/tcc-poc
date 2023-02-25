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
import br.ufpr.estagio.poc.model.Discente;
import br.ufpr.estagio.poc.model.TermoPoc;
import br.ufpr.estagio.poc.repository.TermoPocRepository;
import br.ufpr.estagio.poc.wrapper.DataWrapper;

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
		            headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2NzczNDkxNDAsImlhdCI6MTY3NzM0NTU0MCwiYXV0aF90aW1lIjoxNjc3MzQ1NTQwLCJqdGkiOiI0NTYzMTM4Yi0wYzUzLTQ4MDAtYmJmNi0zNmU2ZjkwYTU2YmYiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTptb3JhZXMxIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiNzM2OGZlMWEtOGQ4YS00Y2VlLTk5NzAtNmQ4ZGI0MjExMDY5IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiI3MzY4ZmUxYS04ZDhhLTRjZWUtOTk3MC02ZDhkYjQyMTEwNjkiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJMZW9uYXJkbyBNb3JhZXMiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtb3JhZXMxIiwiZ2l2ZW5fbmFtZSI6Ikxlb25hcmRvIiwiZmFtaWx5X25hbWUiOiJNb3JhZXMiLCJlbWFpbCI6Im1vcmFlczFAdWZwci5iciJ9.lG8Ox7B9CDXHKd8r0exFmRyTQp5AebVDdvx_HsBuvJuO_MlM9oQZxZ2oJMsXIvNaBlJdEmXyeOelDN5VjfXZb1OmW7b8gBj8occWUeJrmn9kenMJ-6WNj3XswHgQL4I5wrYvkblGt7in66EXglwLaMtq2DsEXa87VWvDwDZkYYgUWVvwddadN2df-Y8BUtoGJw99WieN-PeCcox3e3ZzsAKUlg3fvRNOUqZy0inZWQPXSS-VhOLj4gxAZ9MywwQ_kSqX5zOf4DWhZsgKciZbMSLJ-un7x68tEwX2eoFjMKY79kxXyl-SE65hyH8VAfx97yS8YcW_-ZFAhmge_2I8cA");
		            HttpEntity<String> header = new HttpEntity<>(headers);
		            ResponseEntity<DataWrapper> response = restTemplate.exchange(uri, HttpMethod.GET, header, DataWrapper.class);
		            Discente discente = response.getBody().getData().getDiscente();
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