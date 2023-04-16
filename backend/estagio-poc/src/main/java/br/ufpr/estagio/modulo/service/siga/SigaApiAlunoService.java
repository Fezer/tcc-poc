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
        headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODE2ODgwOTAsImlhdCI6MTY4MTY4NDQ5MCwiYXV0aF90aW1lIjoxNjgxNjg0NDkwLCJqdGkiOiI4N2U2YjI3My1jYzUyLTQ5NDgtOTk3NS0xNTdhMDc0OTlmMTciLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTptb3JhZXMxIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiZThhYjIxMGEtOGRjYS00Yjc1LWI2OTItZTQwOTcyNWU1Y2E3IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiJlOGFiMjEwYS04ZGNhLTRiNzUtYjY5Mi1lNDA5NzI1ZTVjYTciLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJMZW9uYXJkbyBNb3JhZXMiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtb3JhZXMxIiwiZ2l2ZW5fbmFtZSI6Ikxlb25hcmRvIiwiZmFtaWx5X25hbWUiOiJNb3JhZXMiLCJlbWFpbCI6Im1vcmFlczFAdWZwci5iciJ9.MaI-vhLQ4r4rFa01VEB5vVNxBEWqA6OAJc7lTwWekwtnHiZ3rawpT3RX3q4Eyvy7MR_KO-hIpty_UrJoh7FN5UPSn2_7fZP6NAJ8TyKpLG7c36zU2VX-n393iQYGsqQKQE9hRCgzlOl1Vj-VReEQX4TCoeOg0n5kO2ix_ZaCtwSEeaRr6G08VMDiyU2j98se2zNVrXWkG2E9jUN6uL99aU3K6NqMmgIyU5R1XBBTOCKA-PsnYX0mHMCotWUofMBwzL-0mgeITT9UbK1mGOiA8P4pxFdHnrRnZM3Rnm_IklS7B-tnxetDt9-sxM8nTYGHydKtSrkZQk_1-hzJ1Ppzdg");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DiscenteWrapper> discenteWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DiscenteWrapper.class);
        Discente discente = discenteWrapper.getBody().getData().getDiscente();
        Aluno aluno = sigaApiModuloEstagioMapping.mapearDiscenteEmAluno(discente);
        return discente;
    }
}
