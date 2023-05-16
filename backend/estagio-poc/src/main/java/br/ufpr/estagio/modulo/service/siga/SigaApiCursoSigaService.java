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
		headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODQxOTE2MjAsImlhdCI6MTY4NDE4ODAyMCwiYXV0aF90aW1lIjoxNjg0MTg4MDIwLCJqdGkiOiI3YmFjYzRkNy05OWIzLTQ1ZGEtYTY3Ni03ZGY1NmYxZjYxZTQiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTpmZXplciIsInR5cCI6IkJlYXJlciIsImF6cCI6ImVzdGFnaW9zIiwic2Vzc2lvbl9zdGF0ZSI6IjRlZjZlNjU0LWMwNTYtNDQwOS1iMGFjLWNmM2Q5MjU1NjUxOCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDozMDAwIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLW1hc3RlciIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwic2lkIjoiNGVmNmU2NTQtYzA1Ni00NDA5LWIwYWMtY2YzZDkyNTU2NTE4IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiTHVpcyBGZXplciIsInByZWZlcnJlZF91c2VybmFtZSI6ImZlemVyIiwiZ2l2ZW5fbmFtZSI6Ikx1aXMiLCJmYW1pbHlfbmFtZSI6IkZlemVyIiwiZW1haWwiOiJmZXplckB1ZnByLmJyIn0.y49Fci0RIadFcurtUe0YlZbNjwkSvm76_X_u054NyuMUQQz1FVf3s0_QR7x0eqGgfg2bMje0xZPiDVt0QZ2XD40huOanhl4JE0Xsf4QUBBJWX3TqrIFBvuD9p8shN1jfhhms-Vf26IRlO7dn0Lq1oG-jVHXRoGl2YgKbySB0DZJlKwyUcqwo4TeJct14F-s_NHNaYHJ8888cXs3_9dHm_6TcDZQZhShiss4Ohg3ui6wU2am-zSkx6h0XzfvYzotMch9UTSFyS9_5gSkDdjomxk33G0qnp1LnWC7jSE_H9FMsP_4yQUL9aSiCbMVbx9K1QYgSuG11gd36Yj3tCo48hw");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<CursoSigaWrapper> cursoSigaWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, CursoSigaWrapper.class);
		CursoSiga cursoSiga = cursoSigaWrapper.getBody().getData().getCurso();
		
		//cursoSiga = cursoSigaService.salvarCursoSiga(cursoSiga);
		
		return cursoSiga;
	}
}
