package br.ufpr.estagio.modulo.service.siga;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.ufpr.estagio.modulo.model.CursoSiga;
import br.ufpr.estagio.modulo.wrapper.CursoSigaWrapper;

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
		headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODM4MjU0MjEsImlhdCI6MTY4MzgyMTgyMSwiYXV0aF90aW1lIjoxNjgzODIxODIwLCJqdGkiOiJmNTVlNjE2My1iZTM0LTQyZjktYmI4ZC1lOGMwZGRjZDhjZGYiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTpnYWJyaWVsLm1heWVyIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiN2JkZjIyM2EtZTRmMi00NDgyLTlkYzItMzU4ZWZjODQzMTkyIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInNpZCI6IjdiZGYyMjNhLWU0ZjItNDQ4Mi05ZGMyLTM1OGVmYzg0MzE5MiIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IkdhYnJpZWwgTWF5ZXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJnYWJyaWVsLm1heWVyIiwiZ2l2ZW5fbmFtZSI6IkdhYnJpZWwiLCJmYW1pbHlfbmFtZSI6Ik1heWVyIiwiZW1haWwiOiJnYWJyaWVsLm1heWVyQHVmcHIuYnIifQ.cR0YjBXxuuGt06Hkfp1IW0adp-DJQuUtgzHGFIp1B14jIGBsk9SnKOSkgtP7lK3eQzEXJK8hqm9AGy_e6Z8ZM6Qg3kAAbuZImmT1sMI55LFFTh6kIFqykdtoP-NAQVyKq4AYGISvu_ZiBuguFtYU37eNmKo-JDQtEYPYQeALw5v1MiQvmE4OrNOvZZ05AIIaeZm48hV_BAOJIHgkGXLSI88eQOSwGR1yOWT_sYlc7IFGUhB4jYUbUu0YWjfXYB5HUDF2ibG6-O8j-71l7snwAoCVl1xZ75U3Jg7bcVMC00u2yzOniMkDTDK_BT7R1JckX-SdVm1VevmxipgryOeXCA");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<CursoSigaWrapper> cursoSigaWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, CursoSigaWrapper.class);
		CursoSiga cursoSiga = cursoSigaWrapper.getBody().getData().getCurso();
		
		//cursoSiga = cursoSigaService.salvarCursoSiga(cursoSiga);
		
		return cursoSiga;
	}
}
