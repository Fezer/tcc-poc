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

import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.wrapper.DiscenteWrapper;


@Service
@Transactional
public class SigaApiAlunoService {
		
	public SigaApiAlunoService() {
	}
     
    public Discente buscarAlunoPorGrr(String grr, String accessToken) {
    	// TO-DO: colocar dentro de um try-catch e tratar possíveis erros.
		URI uri = UriComponentsBuilder.fromUriString("https://siga-hml.teste.ufpr.br/siga/api/graduacao/discentes").queryParam("grr", grr).build().toUri();
        RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DiscenteWrapper> discenteWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DiscenteWrapper.class);
        Discente discente = discenteWrapper.getBody().getData().getDiscente();
        
        return discente;
    }

    public String buscarGrrPorEmail(String accessToken) {
        URI uri = UriComponentsBuilder.fromUriString("https://siga-hml.teste.ufpr.br/siga/api/graduacao/discente").build().toUri();
        RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> string = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        String grr = string.getBody();
        
        return grr;
    }
}
