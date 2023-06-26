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

import br.ufpr.estagio.modulo.model.DocentesData;
import br.ufpr.estagio.modulo.wrapper.DocentesWrapper;


@Service
@Transactional
public class SigaApiDiscentesService {
	
	public SigaApiDiscentesService() {
	}
     
    public DocentesData buscarDiscentesPorIdPrograma(String idPrograma, String accessToken) {
    	// TO-DO: colocar dentro de um try-catch e tratar possíveis erros.
		URI uri = UriComponentsBuilder.fromUriString("https://siga-hml.teste.ufpr.br/siga/api/graduacao/docentes").queryParam("idPrograma", idPrograma).build().toUri();
        RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        //headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODU2NDM1MjcsImlhdCI6MTY4NTYzOTkyNywiYXV0aF90aW1lIjoxNjg1NjM5OTI2LCJqdGkiOiIwOWM5ZmVjZS0xZjJlLTQ3M2YtOWNjNS02MjUwZmUwNTA5NDAiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTpnYWJyaWVsLm1heWVyIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZXN0YWdpb3MiLCJzZXNzaW9uX3N0YXRlIjoiOTI4NjgyZDMtM2ZhMS00YjdlLTg3ZTctYTRjN2Y0YmVmODY5IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInNpZCI6IjkyODY4MmQzLTNmYTEtNGI3ZS04N2U3LWE0YzdmNGJlZjg2OSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IkdhYnJpZWwgTWF5ZXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJnYWJyaWVsLm1heWVyIiwiZ2l2ZW5fbmFtZSI6IkdhYnJpZWwiLCJmYW1pbHlfbmFtZSI6Ik1heWVyIiwiZW1haWwiOiJnYWJyaWVsLm1heWVyQHVmcHIuYnIifQ.w6JElsq1EFbIvcdEQqFdkHTHti1oUTnQO9GNvXBGLjkSlWj7XyfHNnSCntBz_u9U2YbW4mbgrWGG1D4NblmpQxQUKB1W0TxlutJK6ypg5mtqUtdLGypXMzzkHz8Z4wKHm3PNFjpzYA1LNtpB-eRoJv58SpKNCuXnCIIRkF3bvuRDeRX-64iKh0CH7CHR2mAA7zFmlOhXjto9_wNapTQHXT00JzH-TFgexa06ZcGMBtp6JvzfRXA412I90UsE6ZhUuQgG04icIsICkumfFr31rXUoVr9RM1iRzpuI4zac6AYuOmCGpN_Vs5UuUuLva7eWcTvDrHLnkleiCVYmqgXyiw");
        headers.set("Authorization", accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DocentesWrapper> docentesWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DocentesWrapper.class);
        DocentesData docentes = docentesWrapper.getBody().getData();
        return docentes;
    }
}
