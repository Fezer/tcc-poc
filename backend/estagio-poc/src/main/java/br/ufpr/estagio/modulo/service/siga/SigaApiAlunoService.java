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
     
    public Discente buscarAlunoPorGrr(String grr) {
    	// TO-DO: colocar dentro de um try-catch e tratar possíveis erros.
		URI uri = UriComponentsBuilder.fromUriString("https://siga.ufpr.br:8380/siga/api/graduacao/discentes").queryParam("grr", grr).build().toUri();
        RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODM0NzIzMTMsImlhdCI6MTY4MzQ2ODcxMywiYXV0aF90aW1lIjoxNjgzNDY4NzEyLCJqdGkiOiJlMjA4Y2ZjMC1lNjRmLTRiNGItYjY1Yy1lOGI2Zjc5N2Q2OGYiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTptb3JhZXMxIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiYTk1YjkzNTYtNDM5NC00ZTFiLWFjYTctZTFiM2RlYzhhZDdjIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiJhOTViOTM1Ni00Mzk0LTRlMWItYWNhNy1lMWIzZGVjOGFkN2MiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJMZW9uYXJkbyBNb3JhZXMiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtb3JhZXMxIiwiZ2l2ZW5fbmFtZSI6Ikxlb25hcmRvIiwiZmFtaWx5X25hbWUiOiJNb3JhZXMiLCJlbWFpbCI6Im1vcmFlczFAdWZwci5iciJ9.P9rPxh1TfERq3ZCCpid24xf5RlWOheD8yenYn-aMUOqwi7RQHZjZR3tJ3fosf2k360vmF_3BfRylQGzVJ_3bTpUNNDKpEnROod0WmOReBM1EnxftxHjTbbZqSnpsirJ0ehiaK3-idH41sAmh8RJrUQDaivLonStLugPXI0dxGvzWGusaTD9xofWeYr1q0AfJOHNTOr6TNbHg5XaNc7-SK9I3K7pOewV7SJse_yFkpkOmmpsY5F1J1LBa6xYnYU4n9kx4nf9OOfoHkWDb7_TL-5xIWI2vbob82p7hY_n35qJWBYLoHhdGHC7dNoEfibN7E7U3mdr64xAYbQIDXr-y2Q");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DiscenteWrapper> discenteWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DiscenteWrapper.class);
        Discente discente = discenteWrapper.getBody().getData().getDiscente();
        
        return discente;
    }
}
