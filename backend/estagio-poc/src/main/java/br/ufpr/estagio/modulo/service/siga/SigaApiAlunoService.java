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
        headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODQ0NDgxMDQsImlhdCI6MTY4NDQ0NDUwNCwiYXV0aF90aW1lIjoxNjg0NDQ0NTA0LCJqdGkiOiIzNTFhM2Q2ZS1kNDU0LTRjMWYtYWNhNS1iMjk1ZDhkMDYwZDQiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTpnYWJyaWVsLm1heWVyIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiNTcwZjE3NzctYTQwNi00NmE0LTlkN2EtMDliMTBhNzUwMzBkIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInNpZCI6IjU3MGYxNzc3LWE0MDYtNDZhNC05ZDdhLTA5YjEwYTc1MDMwZCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IkdhYnJpZWwgTWF5ZXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJnYWJyaWVsLm1heWVyIiwiZ2l2ZW5fbmFtZSI6IkdhYnJpZWwiLCJmYW1pbHlfbmFtZSI6Ik1heWVyIiwiZW1haWwiOiJnYWJyaWVsLm1heWVyQHVmcHIuYnIifQ.sVFqFAL3tqzF8-qXaMxHxtRqOWqTqxp3BrfTjlAgYgPPxA_WfunF8UZO-_bHZ-038EXd38uOJaTF6q8ZDQOaeSawTqUZWBuVgeNMJsdE8kO595uhU-OM7ad7rbs2YhRXo4TZX48Xt6FItB1Vmzf0aUEZr3BTtx6kbFJiOsbresoRAsjOfducwmYfsJPpEyqc7yqdUnWs_QqZffuH-KkDQ6A0xaRv04wt6Cv9wXQ4MKXMoMaq5iB-X5LynP6ZWKFrKh9Ati0GyRWTxucOlQ-oUeahhKbDnHT6Ec7AMnmNglcGBHeRZFApAn1GGR3BtXPJPVpjTJcJGibHLpkEjPwoVQ");
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
