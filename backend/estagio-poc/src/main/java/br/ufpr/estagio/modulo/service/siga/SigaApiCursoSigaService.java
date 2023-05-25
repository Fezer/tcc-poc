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
		headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODUwNDIxODcsImlhdCI6MTY4NTAzODU4NywiYXV0aF90aW1lIjoxNjg1MDM4NTg1LCJqdGkiOiJlZDllZTU5MC1hOGQwLTRhN2MtYWY0Ny02ZmZkNjYxNGE4NWIiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTpnYWJyaWVsLm1heWVyIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiMzQzYmZhZWQtY2I0ZC00ZDE3LTk5YzQtMmQzYTA0OGUwNTQzIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInNpZCI6IjM0M2JmYWVkLWNiNGQtNGQxNy05OWM0LTJkM2EwNDhlMDU0MyIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IkdhYnJpZWwgTWF5ZXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJnYWJyaWVsLm1heWVyIiwiZ2l2ZW5fbmFtZSI6IkdhYnJpZWwiLCJmYW1pbHlfbmFtZSI6Ik1heWVyIiwiZW1haWwiOiJnYWJyaWVsLm1heWVyQHVmcHIuYnIifQ.w0lFUZB426TSNxTH0b8zEhURw9LCxW8Zg2_02Zub3JmA3YGZjNIi5glQK2dMifG6EM-W-LWJPvqMJUcZwUAY63JxbLEbcmRtS0Nsu0EuooHso6MXdMsMYOLxS9HdY1jsrU_1ejS33O7WV4nmTQPvfk303rg1MGqwVGcbndvHl96qGBAfbcr6zwlEnpOGG19Vfiv_dNiqjZOhO8BAed6U38R6Dnvhai1rd51RBFnldMuG_f71F0UbFDuJqWizKTuuYGvf7XdDQlcKhcxg04uo66IVCj9YstnpK8MLn5BVxWkYT-JG65fHtm986c1OxRL26IPMVsW-sRlG28L-4g3Adw");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<CursoSigaWrapper> cursoSigaWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, CursoSigaWrapper.class);
		CursoSiga cursoSiga = cursoSigaWrapper.getBody().getData().getCurso();
		
		//cursoSiga = cursoSigaService.salvarCursoSiga(cursoSiga);
		
		return cursoSiga;
	}
}
