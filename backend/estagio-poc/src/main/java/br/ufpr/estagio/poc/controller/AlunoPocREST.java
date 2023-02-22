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
import br.ufpr.estagio.poc.model.Discente;

@CrossOrigin
@RestController
public class AlunoPocREST {
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/aluno/{grr}")
	public ResponseEntity<Discente> listarTermo(@PathVariable String grr){
		try {
			if(grr.isBlank() || grr.isEmpty()) {
				throw new PocException(HttpStatus.BAD_REQUEST, "GRR não informado!");
			} else {
				RestTemplate restTemplate = new RestTemplate();
				URI uri = UriComponentsBuilder.fromUriString("https://siga.ufpr.br:8380/siga/api/graduacao/discentes").queryParam("grr", grr).build().toUri();
	            HttpHeaders headers = new HttpHeaders();
	            headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2NzcwOTE3MTAsImlhdCI6MTY3NzA5MTY1MCwiYXV0aF90aW1lIjoxNjc3MDkxNDY5LCJqdGkiOiI3Y2M5NzgyOS00MjhkLTQ3ZDQtYTE5Zi04Yjc1ZDNmZTBiZTgiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTptb3JhZXMxIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiZGFiMzMzM2EtMjgxYy00YjdjLTkyZTMtZjcwNzJmZTQ4MjM2IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiJkYWIzMzMzYS0yODFjLTRiN2MtOTJlMy1mNzA3MmZlNDgyMzYiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJMZW9uYXJkbyBNb3JhZXMiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtb3JhZXMxIiwiZ2l2ZW5fbmFtZSI6Ikxlb25hcmRvIiwiZmFtaWx5X25hbWUiOiJNb3JhZXMiLCJlbWFpbCI6Im1vcmFlczFAdWZwci5iciJ9.epo9jx4ktQYF_LDT-Jhf_3viHrM_uDBwl2BqVHUBTyvo3NaxViC4Bz9SMRffBDYFyLoomZkTzl39UIGTGkBlUl1PiErzjvP_HhwHuxTzeZi9B0MGHqnIJ_U3QiH1QHqeG-N_Zl8H8bX7FQa6flDJ4_amk22lIbkyo-yQNWYQ57ia4Jh0f_dOV9WnpRuGS54DbK9mTFM9K6OCN7C-wyRsocWK1co5coIDnlIhoT-cv8em99fOuHRhclKD-UnZfWEAkmnt3TTFJnhGWwUWeYnit9HCSVssZlhz6RYPGMReKAU2ALs_fLcSygelsMPSdWpM74YtEw1i0Nniv_YQhMKlaA");
				Discente discente = restTemplate.getForEntity(uri, Discente.class).getBody();
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(discente, Discente.class));
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