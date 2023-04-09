package br.ufpr.estagio.modulo.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.model.TermoPoc;
import br.ufpr.estagio.modulo.repository.TermoPocRepository;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;
import br.ufpr.estagio.modulo.wrapper.DiscenteWrapper;

@CrossOrigin
@RestController
@RequestMapping("/aluno")
public class AlunoREST {
	
    private EstagioService estagioService;
    private TermoDeEstagioService termoDeEstagioService;
    private RelatorioDeEstagioService relatorioDeEstagioService;
    
    public AlunoREST(EstagioService estagioService, 
    		TermoDeEstagioService termoDeEstagioService, 
    		RelatorioDeEstagioService relatorioDeEstagioService) {
        this.estagioService = estagioService;
        this.termoDeEstagioService = termoDeEstagioService;
        this.relatorioDeEstagioService = relatorioDeEstagioService;
    }
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private TermoPocRepository repo;
	
	@GetMapping("/{termoIdURL}")
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
		            headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2Nzc0MjYwNTksImlhdCI6MTY3NzQyMjQ1OSwiYXV0aF90aW1lIjoxNjc3NDIyNDU4LCJqdGkiOiI2OWJhNTNlYi0yNDliLTQ5MWMtODYzZi01YzY2ZTc5OTQxYWYiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTptb3JhZXMxIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiNWIyZTE3ZmUtMWJiNi00MmEyLWEzNmYtNzY3NGQzNzg5ZjY3IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiI1YjJlMTdmZS0xYmI2LTQyYTItYTM2Zi03Njc0ZDM3ODlmNjciLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJMZW9uYXJkbyBNb3JhZXMiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtb3JhZXMxIiwiZ2l2ZW5fbmFtZSI6Ikxlb25hcmRvIiwiZmFtaWx5X25hbWUiOiJNb3JhZXMiLCJlbWFpbCI6Im1vcmFlczFAdWZwci5iciJ9.ueypAUp6uFh150D9EOKhpY6Y0i26_4VxAngdlcQDt90hAMghSSR_J7fP-2rO5CBhl8Zqs8kQxO84wzNhULlYnE0AP0EKy3AcATYeNLuTdTNmyzbB2BtYeMznUmntAuPh8cL5Xt_3vuamj_bGMD42lj6RXHm9hdC8BPGWmwLSdlRXy0m9S0XpcA9N5iIXmp7Uynq1ChHlm6k0YQ-LzQ_0bO0w5Am8dkpUgLKgzhahvjvc4Mtc3SjhRJmfNtI8hlKstFJPWmXNBVJbVHEOOoRU_KuNQhki8Rb25g_HzMnkFUHGUJ9SiD3SPbZOQo_e2kkdaWEtRw7yq0edXwLOYHeTrg");
		            HttpEntity<String> entity = new HttpEntity<>(headers);
		            ResponseEntity<DiscenteWrapper> discenteWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DiscenteWrapper.class);
		            Discente discente = discenteWrapper.getBody().getData().getDiscente();
					return ResponseEntity.status(HttpStatus.OK).body(mapper.map(discente, Discente.class));
				}
			}
		}catch (NumberFormatException e) {
			throw new PocException(HttpStatus.BAD_REQUEST, "O ID informado para o termo não é do tipo de dado esperado!");
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
}