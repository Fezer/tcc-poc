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
        headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODE2OTMyODEsImlhdCI6MTY4MTY4OTY4MSwiYXV0aF90aW1lIjoxNjgxNjg5NjgwLCJqdGkiOiJmNDQwZDM1Mi1iYzhkLTQ1ODUtYjQ4Zi1iZjBkMjhkYmY2N2YiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTptb3JhZXMxIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiNTIxZjczZDgtNWQ4MS00Y2NmLTgxM2QtNmI2MDQ1ODliYTEwIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiI1MjFmNzNkOC01ZDgxLTRjY2YtODEzZC02YjYwNDU4OWJhMTAiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJMZW9uYXJkbyBNb3JhZXMiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtb3JhZXMxIiwiZ2l2ZW5fbmFtZSI6Ikxlb25hcmRvIiwiZmFtaWx5X25hbWUiOiJNb3JhZXMiLCJlbWFpbCI6Im1vcmFlczFAdWZwci5iciJ9.dsuhtf2n1V8UnnsOGHLkjdR2gjHLz1XEIwLFyj1J9eyDYmBonyRJUN4SM7CCU05Rtdju9uVxJi9NkRDdLCAXFCJMUvQ1uDEj2exi7XOSPdIdruJ2_sU7CMHa2gVT-u9e1D2dS3vnaPGbep7pBCfS20DpvUg1pQRKdIxIhDKkHsvLpZOvPQRrSOSZTAH6uahY8A2CHDWsnfW6p0w9Rf8cNBlqvSL5ahxyTwEmsay-SgnrCgN9RQSY562Cmruyhi8ke_3BEgB4X1cu_2YI6u0Ojw7_ZNBRqa1yhwRKkQEx8FUS3Hkmsmgxd7guXoWdiGQ1OX9NsHK3jGRHEG1eKe8LzQ");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DiscenteWrapper> discenteWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DiscenteWrapper.class);
        Discente discente = discenteWrapper.getBody().getData().getDiscente();
        //Aluno aluno = sigaApiModuloEstagioMapping.mapearDiscenteEmAluno(discente);
        return discente;
    }
}
