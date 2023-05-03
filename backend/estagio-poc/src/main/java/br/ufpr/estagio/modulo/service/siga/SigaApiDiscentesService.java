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
        headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODMxNDYwMjksImlhdCI6MTY4MzE0MjQyOSwiYXV0aF90aW1lIjoxNjgzMTQyNDI4LCJqdGkiOiIwY2IwMTk5Ny1iMDZjLTRmNGUtOWRmMS1jZWQwNGU1YjQ5ZTkiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTpnYWJyaWVsLm1heWVyIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiMWJjOGY1NjgtMGE2ZS00MTljLTgxNzgtNmU1YTBjNTljZmQwIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInNpZCI6IjFiYzhmNTY4LTBhNmUtNDE5Yy04MTc4LTZlNWEwYzU5Y2ZkMCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IkdhYnJpZWwgTWF5ZXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJnYWJyaWVsLm1heWVyIiwiZ2l2ZW5fbmFtZSI6IkdhYnJpZWwiLCJmYW1pbHlfbmFtZSI6Ik1heWVyIiwiZW1haWwiOiJnYWJyaWVsLm1heWVyQHVmcHIuYnIifQ.VgflQvDpBiFNnlBy-rj9YsHZScxhE2RRYVa3_khtLWVbg0TSaRdXFQyazFjhWx0DBdzrKxMXfxBp_w54ldfPu8NkyRARRlU9tMhyk_Je0NLUs77bBOk0otjFhG84hE87TY6zl--uXNjvIFcz41IrgU_RhlhDO3LO89zixVTyRFRixHyC7vTqpd-PU5tyzvyxGonE8ZmFCCbvcHs9-hFhOeZ_TZIDwvulJM-UgOSjW1xU-gi_v5RPyIPCcFc9ffpnaSXA5qdsv4eRizYOxfrbS3nmkkX2lCkFgllR9wQ6TWXNj4qt9yHAHB7NaNynh7B_pjsBXL4g_iQWqwedcdJ90g");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DocentesWrapper> docentesWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DocentesWrapper.class);
        DocentesData docentes = docentesWrapper.getBody().getData();
        return docentes;
    }
}
