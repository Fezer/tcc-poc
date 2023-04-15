package br.ufpr.estagio.modulo.service;

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
     
    public Discente buscarAlunoPorGrr(String grr) {
    	// TO-DO: colocar dentro de um try-catch e tratar possíveis erros.
		URI uri = UriComponentsBuilder.fromUriString("https://siga.ufpr.br:8380/siga/api/graduacao/discentes").queryParam("grr", grr).build().toUri();
        RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODE2MDM3MDksImlhdCI6MTY4MTYwMDEwOSwiYXV0aF90aW1lIjoxNjgxNjAwMTA5LCJqdGkiOiIwN2ZkZDUyMS1hYjFkLTQyMjEtYjI2Ny1mZGY0NzRmODVlZTciLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTptb3JhZXMxIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiZWY5N2Q3YmQtY2UxYy00YzNlLTliN2QtOTIyM2E1N2M3ODZiIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiJlZjk3ZDdiZC1jZTFjLTRjM2UtOWI3ZC05MjIzYTU3Yzc4NmIiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJMZW9uYXJkbyBNb3JhZXMiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtb3JhZXMxIiwiZ2l2ZW5fbmFtZSI6Ikxlb25hcmRvIiwiZmFtaWx5X25hbWUiOiJNb3JhZXMiLCJlbWFpbCI6Im1vcmFlczFAdWZwci5iciJ9.kxexzR1m6csgdPBdTHZ1HDAwzxh2MMULf0GYVvhLbztxohpCciWb9-QC86TCooudSJlWyPJmemuMNCipXtWFABXSr3qI-C8uGtN6w9dTTx8wo005DZPm1thEfF4xZrhF-kX6B5vpheEV605sUImo10jVwqNoVb9sRKO0h4K_HjT9pQMy5D00n3h-eg8YBigM_uqzjq_RBBV1aq_nr_ym_TH42DAyFqmlzb4xOHaWsOR1LKZR2SIql2t-PUzjbZW7GjLh5-kWmWeJeNBdPh7ECiBqsKkIPCEhA2ZihQJQWbQi1LFHn5DIpv0NKykplmZJfXElDXgwYYZLZmCbgGZ15w");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DiscenteWrapper> discenteWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DiscenteWrapper.class);
        Discente discente = discenteWrapper.getBody().getData().getDiscente();
        return discente;
    }
}
