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
        headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODQwMTkyNDAsImlhdCI6MTY4NDAxNTY0MCwiYXV0aF90aW1lIjoxNjg0MDE1NjM5LCJqdGkiOiJjMWNmYzJlZS01MjFjLTRkNGMtYThhZC01M2IyNjk5YmJkOWUiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTpmZXplciIsInR5cCI6IkJlYXJlciIsImF6cCI6ImVzdGFnaW9zIiwic2Vzc2lvbl9zdGF0ZSI6IjQyMzcxMjAwLTE1ZTUtNGMwNi1iNjAwLTA5YzVlNjhjZjkwMCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDozMDAwIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLW1hc3RlciIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwic2lkIjoiNDIzNzEyMDAtMTVlNS00YzA2LWI2MDAtMDljNWU2OGNmOTAwIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiTHVpcyBGZXplciIsInByZWZlcnJlZF91c2VybmFtZSI6ImZlemVyIiwiZ2l2ZW5fbmFtZSI6Ikx1aXMiLCJmYW1pbHlfbmFtZSI6IkZlemVyIiwiZW1haWwiOiJmZXplckB1ZnByLmJyIn0.iA7at8vH0R-WsaAQATTeA84-42xlrUQ8v5kUFCUClnPWZJitNzRaFMbJiC84X9MQSSkCVCUNIGLkr3VxLnI395zeUMPBj4APvmZI_cSvfnNz3OpzhzdD8mGKq-6jSQ-5MMQtM3vaYARP9WVDCwnYBCKWIt2Mchje2hKs13sTPWxPB4knRMmvW1WnnNevEtHjQSBact7FprFYRAUKMf4KJ0xXJW_yupRJOCs2-2qx0XBNm6jFYCTPSxmdyH_N8KMv5tu2T8mYvwTFSu2GIbswzMwDGE7d9Ga8lFY-Fnpr7U3Kw811iJd6hnQOJc6-pIuQnuDRPCcKETMP8VirwCYujg");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DiscenteWrapper> discenteWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DiscenteWrapper.class);
        Discente discente = discenteWrapper.getBody().getData().getDiscente();
        
        return discente;
    }
}
