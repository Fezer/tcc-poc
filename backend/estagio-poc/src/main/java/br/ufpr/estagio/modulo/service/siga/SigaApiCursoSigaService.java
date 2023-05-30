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
	
	public CursoSiga buscarCursoSigaPorIdCurso(Long idCurso, String accessToken) {
		// TO-DO: colocar dentro de um try-catch e tratar possíveis erros.
		URI uri = UriComponentsBuilder.fromUriString("https://siga.ufpr.br:8380/siga/api/graduacao/cursos").queryParam("idCurso", idCurso).build().toUri();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", accessToken);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<CursoSigaWrapper> cursoSigaWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, CursoSigaWrapper.class);
		CursoSiga cursoSiga = cursoSigaWrapper.getBody().getData().getCurso();
		
		//cursoSiga = cursoSigaService.salvarCursoSiga(cursoSiga);
		
		return cursoSiga;
	}
}
