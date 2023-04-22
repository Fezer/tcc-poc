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

import br.ufpr.estagio.modulo.mapper.SigaApiModuloEstagioMapper;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.model.DocentesData;
import br.ufpr.estagio.modulo.wrapper.DiscenteWrapper;
import br.ufpr.estagio.modulo.wrapper.DocentesWrapper;


@Service
@Transactional
public class SigaApiDiscentesService {
	
	private SigaApiModuloEstagioMapper sigaApiModuloEstagioMapping;
	
	public SigaApiDiscentesService(SigaApiModuloEstagioMapper sigaApiModuloEstagioMapping) {
		this.sigaApiModuloEstagioMapping = sigaApiModuloEstagioMapping;
	}
     
    public DocentesData buscarDiscentesPorIdPrograma(String idPrograma) {
    	// TO-DO: colocar dentro de um try-catch e tratar possíveis erros.
		URI uri = UriComponentsBuilder.fromUriString("https://siga.ufpr.br:8380/siga/api/graduacao/docentes").queryParam("idPrograma", idPrograma).build().toUri();
        RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODIyMDAyMDEsImlhdCI6MTY4MjE5NjYwMSwiYXV0aF90aW1lIjoxNjgyMTk2NTkxLCJqdGkiOiI0ODc0MTEzMS01OGIzLTRkMGMtOGY4OS00OTU1ZjJjNWQxMjQiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTptb3JhZXMxIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiYzBmYTA5OTQtMWQ3OS00MWM1LTlkNzktYTZjNWIwZjg0Y2Q0IiwiYWNyIjoiMCIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiJjMGZhMDk5NC0xZDc5LTQxYzUtOWQ3OS1hNmM1YjBmODRjZDQiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJMZW9uYXJkbyBNb3JhZXMiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtb3JhZXMxIiwiZ2l2ZW5fbmFtZSI6Ikxlb25hcmRvIiwiZmFtaWx5X25hbWUiOiJNb3JhZXMiLCJlbWFpbCI6Im1vcmFlczFAdWZwci5iciJ9.CGbBCkwZufw-Q0HWUss9JN8pQhgo5iAm2igKFm3k_qeP0QEHijXpmlT_kpcPaOn7A89PO_o1lhU3zLdo7WuADawL4rI8WgbWT0n8z6zOIG4lvu4oU3K4aZQ549v_f58fzg8i6sj_MB42FRk9WVJd56L7DMfeXh4nHoONAk0d4Hoa5PA8L9NQ8b_IUbwsK3EuGHsIdvKinNrKN_ZSZYYvwcLuYO7_0d7ab7sVwPRF-0YAleNN5D5nPnuVS9OyFWF8Xs9GkQ6M91XAcF6ex2NaSTYi_8cScW8kWhkFzn4U5IxzGFDwFyPHvD_Ixc36fOiJVd_2KPUxfg3Z80luwXBv7A");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DocentesWrapper> docentesWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DocentesWrapper.class);
        DocentesData docentes = docentesWrapper.getBody().getData();
        return docentes;
    }
}
