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
	
	public SigaApiDiscentesService() {
	}
     
    public DocentesData buscarDiscentesPorIdPrograma(String idPrograma) {
    	// TO-DO: colocar dentro de um try-catch e tratar possíveis erros.
		URI uri = UriComponentsBuilder.fromUriString("https://siga.ufpr.br:8380/siga/api/graduacao/docentes").queryParam("idPrograma", idPrograma).build().toUri();
        RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODI3MjE3MTYsImlhdCI6MTY4MjcxODExNiwiYXV0aF90aW1lIjoxNjgyNzE4MTE1LCJqdGkiOiI3Yjc5ZWNiOC05ZjgxLTQwN2ItYTNkOC0xMjgyY2M1ZTQzZDQiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTpnYWJyaWVsLm1heWVyIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiMzVjMmQzMWQtMmJhOC00YTFlLWIxZmItMGE0Y2RhYWFlYWE0IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInNpZCI6IjM1YzJkMzFkLTJiYTgtNGExZS1iMWZiLTBhNGNkYWFhZWFhNCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IkdhYnJpZWwgTWF5ZXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJnYWJyaWVsLm1heWVyIiwiZ2l2ZW5fbmFtZSI6IkdhYnJpZWwiLCJmYW1pbHlfbmFtZSI6Ik1heWVyIiwiZW1haWwiOiJnYWJyaWVsLm1heWVyQHVmcHIuYnIifQ.sHsw6V4PxsdIlba8Of0sAILngHPbTnV86MKy9EcuXKsLYk2FRneSlue1-dYJYg7ahzwMa8ZmyIlRP6oZUP0CEDae5bxgWPxDMCjslAYxTxouywdD5T7JskyZ2ozoB3m1VhPNVB6Tg3bsqxIz9L6bAw9wsvQekRWV3CmtzwXwFKh3jgyHLWL28VlRZ_ZIr0raG77jCKJmH_DKDIDVSZgf5odvPhpqXAvbuKix4aJK53aqoL7b1M2VE30MgtiQU9FjhfCl6lsOnvXANyRolzqKzGJpEjzHtVJgPbyFc6iXPwyr2XwJ7s0Y0GFuI5WqSwcFzX9ctnxP388lFlzf55xajA");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DocentesWrapper> docentesWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DocentesWrapper.class);
        DocentesData docentes = docentesWrapper.getBody().getData();
        return docentes;
    }
}
