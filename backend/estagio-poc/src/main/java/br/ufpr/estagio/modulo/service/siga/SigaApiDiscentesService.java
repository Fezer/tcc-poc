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
     
    public DocentesData buscarDiscentesPorIdPrograma(String idPrograma) {
    	// TO-DO: colocar dentro de um try-catch e tratar possíveis erros.
		URI uri = UriComponentsBuilder.fromUriString("https://siga.ufpr.br:8380/siga/api/graduacao/docentes").queryParam("idPrograma", idPrograma).build().toUri();
        RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoX3JoMTdNUG1rOWlZalZQMElHTnJwSzdsVlczN25GZ1J4TmFPMEcwZkk0In0.eyJleHAiOjE2ODQ2MDIxMDUsImlhdCI6MTY4NDU5ODUwNSwiYXV0aF90aW1lIjoxNjg0NTk4NTA0LCJqdGkiOiIzY2I4M2I0Yi1kZjc3LTQxNDctODRiNS1hMTJkZjVhYjhjNmEiLCJpc3MiOiJodHRwczovL2xvZ2luLnVmcHIuYnIvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmOjRhNTgyMGJjLWQ2MzMtNGMxZS1hYzhjLWRhOWRlNmRkY2I3OTpmZXplciIsInR5cCI6IkJlYXJlciIsImF6cCI6ImVzdGFnaW9zIiwic2Vzc2lvbl9zdGF0ZSI6IjA1Njc2OTQ0LThhYWEtNDY5NC05ZTRhLTMyOTI2OTUzOWQ5YyIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDozMDAwIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLW1hc3RlciIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwic2lkIjoiMDU2NzY5NDQtOGFhYS00Njk0LTllNGEtMzI5MjY5NTM5ZDljIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiTHVpcyBGZXplciIsInByZWZlcnJlZF91c2VybmFtZSI6ImZlemVyIiwiZ2l2ZW5fbmFtZSI6Ikx1aXMiLCJmYW1pbHlfbmFtZSI6IkZlemVyIiwiZW1haWwiOiJmZXplckB1ZnByLmJyIn0.W0-tfCuyNBnbr4PBuU5YhQIOYEH0XTywhh13_ReFgJBOMIy--ikIBAN5t7qPUwJ4KI9lJIH4N5_ppidPAm2FJ2bvn3FnN3dQoIjok5RXjeeN6A4zODE9XKZ9rK1zdpChphERCk7UuLMAywHGK3pa6It-rtQ1tqwirPDu9ZvwTVixWirANAJzvkqstfzhdOyVfKN2LZrcPx7OAzvoJhEJ5WughNEc1dk-wWwSDcOFm-sLUaO4XNeYHQy-APY7icvxqg1Tp9u0kxJ6Oct7mXMp1AsSkySDFrUeKvL5x2ZDLRTQsuwqYEIP2LZ3HPp91ftdoq26mepfCB_oUGBodkppTw");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DocentesWrapper> docentesWrapper = restTemplate.exchange(uri, HttpMethod.GET, entity, DocentesWrapper.class);
        DocentesData docentes = docentesWrapper.getBody().getData();
        return docentes;
    }
}
