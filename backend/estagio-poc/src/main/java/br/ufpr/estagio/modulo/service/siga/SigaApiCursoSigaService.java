package br.ufpr.estagio.modulo.service.siga;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.ufpr.estagio.modulo.model.CursoSiga;
import br.ufpr.estagio.modulo.model.CursoSigaData;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.service.CursoSigaService;
import br.ufpr.estagio.modulo.wrapper.CursoSigaWrapper;
import br.ufpr.estagio.modulo.wrapper.DiscenteWrapper;

@Service
@Transactional
public class SigaApiCursoSigaService {

	//@Autowired
	//CursoSigaService cursoSigaService;
	
	public SigaApiCursoSigaService() {
	}
	
	public CursoSiga buscarCursoSigaPorIdCurso(Long idCurso) {
		// TO-DO: colocar dentro de um try-catch e tratar possíveis erros.
		URI uri = UriComponentsBuilder.fromUriString("https://siga.ufpr.br:8380/siga/api/graduacao/cursos").queryParam("idCurso", idCurso).build().toUri();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODMzODM1NDAsImlhdCI6MTY4MzM3OTk0MCwiYXV0aF90aW1lIjoxNjgzMzc5OTM5LCJqdGkiOiI4OWZkYWQ1NS1jOGIzLTQ1ODQtOTk1Yi01MDkwNDMzMjQzOWEiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTptb3JhZXMxIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiZTgyODcyNzktYzAzYi00OWI0LThjOTMtZDk3OGE5NDhlZWU1IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiJlODI4NzI3OS1jMDNiLTQ5YjQtOGM5My1kOTc4YTk0OGVlZTUiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJMZW9uYXJkbyBNb3JhZXMiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtb3JhZXMxIiwiZ2l2ZW5fbmFtZSI6Ikxlb25hcmRvIiwiZmFtaWx5X25hbWUiOiJNb3JhZXMiLCJlbWFpbCI6Im1vcmFlczFAdWZwci5iciJ9.bom6lY3wRwXvJ7pKXHznYejLfPckKjTCZ6Lw9YFjkqDfTDSWkwCD7PPkZ3V1S5HBqy6dMsKW7lzjGJrWCTJ4NH-geB6klyS5kcbRJa_P57t4dGHnb7OvWvwogJkJlSujFntjp1Oeu6FNxcdKrHqk3V24laRqtJMLDJZhem2r6X4gf7PqjWCvUx0zxGYyysHeTGJsXHkkk1Oy0MZaQsyJemjPD33DX2z9U1dro4dengjRAwSdbTwkt5bsneUAa14Hv3534G1_RSWarnemoJkw-88odVYTp3zi4sZTKgPoVgCS39NJFohMqyTRDY3ZU9zVNe8E7vLB5Nn6irbt9CG_cQ");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<CursoSigaWrapper> cursoSigaWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, CursoSigaWrapper.class);
		CursoSiga cursoSiga = cursoSigaWrapper.getBody().getData().getCurso();
		
		//cursoSiga = cursoSigaService.salvarCursoSiga(cursoSiga);
		
		return cursoSiga;
	}
}
