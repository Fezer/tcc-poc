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
        headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODI2MjU0NzgsImlhdCI6MTY4MjYyMTg3OCwiYXV0aF90aW1lIjoxNjgyNjIxODc2LCJqdGkiOiJhYjlhZDI4MS01OTdkLTRlODktOTBmZS00MzE1NGUyOTRhM2IiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTpnYWJyaWVsLm1heWVyIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiOTM1YmQzNmMtYWZmNi00M2MwLTg4ZWYtZWNmMWZhMzg2MTIyIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInNpZCI6IjkzNWJkMzZjLWFmZjYtNDNjMC04OGVmLWVjZjFmYTM4NjEyMiIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IkdhYnJpZWwgTWF5ZXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJnYWJyaWVsLm1heWVyIiwiZ2l2ZW5fbmFtZSI6IkdhYnJpZWwiLCJmYW1pbHlfbmFtZSI6Ik1heWVyIiwiZW1haWwiOiJnYWJyaWVsLm1heWVyQHVmcHIuYnIifQ.QU6Bn_m8XZ-TYM0cOoMeWu_XTACWKuU_o5JuYhv5RGztUSE1R1H5YuTZgKEGhQw_-MrOtfojdxF9zsYlJeIMIjgult6WOFchTnj1PA88cC9WF8XaxLqzFo79txu4Nn4xV2eCi-Pdriq2a6OtOqFKbWNhHRIJNXRHQRIU_MmRdEDG286EDURmW0fZl0VGe88XSTYDkIfoGenICw7AfPuK6wh-DnEiEPVLeMaMq2HnTYOAAiupKezejDEa9cz55AebRGiGbk2XvLs3iuXWpRLCDc0DCv8YZTVIN_c-oR6W7c7zoOj-NZq3TH0F47tl5ecLFrQ2qDEjHTlEyZ1m25vZ4g");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DocentesWrapper> docentesWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DocentesWrapper.class);
        DocentesData docentes = docentesWrapper.getBody().getData();
        return docentes;
    }
}
