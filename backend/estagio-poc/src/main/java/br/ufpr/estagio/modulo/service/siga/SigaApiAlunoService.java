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

import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.wrapper.DiscenteWrapper;


@Service
@Transactional
public class SigaApiAlunoService {
	
	private SigaApiModuloEstagioMapping sigaApiModuloEstagioMapping;
	
	public SigaApiAlunoService(SigaApiModuloEstagioMapping sigaApiModuloEstagioMapping) {
		this.sigaApiModuloEstagioMapping = sigaApiModuloEstagioMapping;
	}
     
    public Discente buscarAlunoPorGrr(String grr) {
    	// TO-DO: colocar dentro de um try-catch e tratar possíveis erros.
		URI uri = UriComponentsBuilder.fromUriString("https://siga.ufpr.br:8380/siga/api/graduacao/discentes").queryParam("grr", grr).build().toUri();
        RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODIxMjM5NTEsImlhdCI6MTY4MjEyMDM1MSwiYXV0aF90aW1lIjoxNjgyMTIwMzUwLCJqdGkiOiI5MzQ5ZDVlOC0zMDJkLTQ0NGYtOTNmMC03YjE1NTU2NzEwMGUiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTptb3JhZXMxIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiNTQ5OGY0YzAtZDg3Yi00NjEyLWExMGUtYTQ2NDcxYzUwYThjIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiI1NDk4ZjRjMC1kODdiLTQ2MTItYTEwZS1hNDY0NzFjNTBhOGMiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJMZW9uYXJkbyBNb3JhZXMiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtb3JhZXMxIiwiZ2l2ZW5fbmFtZSI6Ikxlb25hcmRvIiwiZmFtaWx5X25hbWUiOiJNb3JhZXMiLCJlbWFpbCI6Im1vcmFlczFAdWZwci5iciJ9.Ai3Q8Mq5Byr7wMsSv4-Ah2rHK-vEw0WHR796CyHzo0X5A79vCt6b5OnZfsGz0RMS54IP9BZ_rbP2G7Dnyqbs7d1UoMm8cRMmAU5PxH-LqE1QGal2Pir59ZFY4AnMNyR9gNau7k2JkIjkRJdPz8pRcAwXzWSW_awoxZ9X2lp1QhUhpDlHdKdVHLNojxgi03sz_DYn3iHoG5RmqwulOFgFBI2hsBg5EIzyeY5mKiadx7JaqsJJtLwwifGfSzej54a9iW3IzbcKudLL5tl0veO9aCgHQUGFjArQpHyJuD7gFUotv-sJhVjGnv4ieKuXDbTkl6l6AwV3fcx0N4axXi8pnQ");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DiscenteWrapper> discenteWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DiscenteWrapper.class);
        Discente discente = discenteWrapper.getBody().getData().getDiscente();
        //Aluno aluno = sigaApiModuloEstagioMapping.mapearDiscenteEmAluno(discente);
        return discente;
    }
}
