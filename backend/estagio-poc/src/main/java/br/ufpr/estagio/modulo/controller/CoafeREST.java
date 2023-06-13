package br.ufpr.estagio.modulo.controller;

import java.io.File;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

import br.ufpr.estagio.modulo.dto.AgenteIntegradorResumidoDTO;
import br.ufpr.estagio.modulo.dto.ApoliceResumidoDTO;
import br.ufpr.estagio.modulo.dto.DescricaoAjustesDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.dto.JustificativaDTO;
import br.ufpr.estagio.modulo.dto.SeguradoraResumidoDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.TermoDeRescisaoDTO;
import br.ufpr.estagio.modulo.enums.EnumTipoDocumento;
import br.ufpr.estagio.modulo.exception.BadRequestException;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeRescisao;
import br.ufpr.estagio.modulo.service.AgenteIntegradorService;
import br.ufpr.estagio.modulo.service.AlunoService;
import br.ufpr.estagio.modulo.service.CertificadoDeEstagioService;
import br.ufpr.estagio.modulo.service.ContratanteService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.GeradorDeExcelService;
import br.ufpr.estagio.modulo.service.GeradorDePdfService;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeRescisaoService;

@CrossOrigin
@RestController
@RequestMapping("/coafe")
public class CoafeREST {

	@Autowired
	private TermoDeEstagioService termoDeEstagioService;
	
	@Autowired
	private TermoDeRescisaoService termoDeRescisaoService;
	
	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private EstagioService estagioService;
	
	@Autowired
	private CertificadoDeEstagioService certificadoDeEstagioService;
	
	@Autowired
	private RelatorioDeEstagioService relatorioDeEstagioService;
	
	@Autowired
	private ContratanteService contratanteService;
	
	@Autowired
	private AgenteIntegradorService agenteIntegradorService;
	
	@Autowired
	private GeradorDeExcelService geradorExcelService;
	
	@Autowired
	private GeradorDePdfService geradorService;
		
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/termo/pendenteAprovacaoCoafe")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosDeCompromissoPendenteAprovacao(){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosDeCompromissoPendenteAprovacaoCoafe();
			if(listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream().map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/termo/pendenteAprovacaoCoafeFiltro")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosDeCompromissoPendenteAprovacaoPorTipoEstagio(@RequestParam String tipoEstagio){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosDeCompromissoPendenteAprovacaoCoafePorTipoEstagio(tipoEstagio);
			if(listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream().map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/termo/indeferido")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosDeCompromissoIndeferidos(){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosDeCompromissoIndeferidos();
			if(listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream().map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/termoAditivo/pendenteAprovacaoCoafe")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosAditivoPendenteAprovacao(){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosAditivosPendenteAprovacaoCoafe();
			if(listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream().map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/termoAditivo/pendenteAprovacaoCoafeFiltro")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosAditivoPendenteAprovacaoPorTipoEstagio(@RequestParam String tipoEstagio){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosAditivosPendenteAprovacaoCoafePorTipoEstagio(tipoEstagio);
			if(listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream().map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/termoAditivo/indeferido")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosAditivoIndeferidos(){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosAditivosIndeferidos();
			if(listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream().map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/termo/{idTermo}/agenteIntegrador")
	public ResponseEntity<AgenteIntegradorResumidoDTO> consultarAgenteIntegradorAssociadoAoTermo(@PathVariable Long idTermo){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
			if(termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				AgenteIntegrador agente = termo.getAgenteIntegrador();
				AgenteIntegradorResumidoDTO agenteDTO = mapper.map(agente, AgenteIntegradorResumidoDTO.class);
				return new ResponseEntity<>(agenteDTO, HttpStatus.OK);
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/termo/{idTermo}/seguradora")
	public ResponseEntity<SeguradoraResumidoDTO> consultarSeguradoraAssociadaAoTermo(@PathVariable Long idTermo){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
			if(termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				Seguradora seguradora = termo.getSeguradora();
				SeguradoraResumidoDTO seguradoraDTO = mapper.map(seguradora, SeguradoraResumidoDTO.class);
				return new ResponseEntity<>(seguradoraDTO, HttpStatus.OK);
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/termo/{idTermo}/apolice")
	public ResponseEntity<ApoliceResumidoDTO> consultarApoliceAssociadaAoTermo(@PathVariable Long idTermo){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
			if(termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				Apolice apolice = termo.getApolice();
				ApoliceResumidoDTO apoliceDTO = mapper.map(apolice, ApoliceResumidoDTO.class);
				return new ResponseEntity<>(apoliceDTO, HttpStatus.OK);
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/termo/{idTermo}/indeferir")
	public ResponseEntity<TermoDeEstagioDTO> indeferirTermoDeCompromisso(@PathVariable Long idTermo, @RequestBody JustificativaDTO justificativa){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.indeferirTermoDeEstagioCoafe(termo, justificativa);
			TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
			return new ResponseEntity<>(termoDTO, HttpStatus.OK);
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/termo/{idTermo}/solicitarAjustes")
	public ResponseEntity<TermoDeEstagioDTO> solicitarAjutesTermoDeCompromisso(@PathVariable Long idTermo, @RequestBody DescricaoAjustesDTO descricaoAjustes){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.solicitarAjutesTermoDeEstagioCoafe(termo, descricaoAjustes);
			TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
			return new ResponseEntity<>(termoDTO, HttpStatus.OK);
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
		
	@PutMapping("/termo/{idTermo}/aprovar")
	public ResponseEntity<TermoDeEstagioDTO> aprovarTermoDeCompromisso(@PathVariable Long idTermo){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.aprovarTermoDeEstagioCoafe(termo);
			TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
			return new ResponseEntity<>(termoDTO, HttpStatus.OK);
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/termoDeRescisao/pendenteCiencia")
	public ResponseEntity<List<TermoDeRescisaoDTO>> listarTermosDeRescisaoPendenteCienciaCoafe(){
		try {
			List<TermoDeRescisao> listaTermosDeRescisao = termoDeRescisaoService.listarTermosDeRescisaoPendenteCienciaCoafe();
			if(listaTermosDeRescisao == null || listaTermosDeRescisao.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermosDeRescisao.stream().map(e -> mapper.map(e, TermoDeRescisaoDTO.class)).collect(Collectors.toList()));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/termoDeRescisao/{idTermo}/darCiencia")
	public ResponseEntity<TermoDeRescisaoDTO> darCienciaTermoDeRescisao(@PathVariable Long idTermo){
		try {
			Optional<TermoDeRescisao> termoOptional = termoDeRescisaoService.buscarPorId(idTermo);
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeRescisao termo = termoOptional.get();
			termo = termoDeRescisaoService.darCienciaTermoDeRescisaoCoafe(termo);
			TermoDeRescisaoDTO termoDTO = mapper.map(termo, TermoDeRescisaoDTO.class);
			return new ResponseEntity<>(termoDTO, HttpStatus.OK);
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	// Não lembro como veio parar aqui e estou no meio de outra coisa. 
	// Mantido para não quebrar algo, mas acho que pode apagar. Vou revisar em breve
	@GetMapping("/{grrAlunoURL}/download-termo")
	public ResponseEntity<Resource> downloadTermo(@PathVariable String grrAlunoURL, @RequestHeader("Authorization") String accessToken) {
	    if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
	        throw new BadRequestException("GRR do aluno não informado!");
	    } else {
	        Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
	        if (aluno == null) {
	            throw new NotFoundException("Aluno não encontrado!");
	        } else {
	        	Path diretorioAtual = Paths.get("").toAbsolutePath();
            	String diretorioDestino = diretorioAtual + "/src/main/resources/arquivos/";
            	
	            String nomeArquivo = grrAlunoURL + "-" + EnumTipoDocumento.TermoDeCompromisso;
	            Path arquivo = Paths.get(diretorioDestino + nomeArquivo);

	            try {
	                Resource resource = new UrlResource(arquivo.toUri());

	                if (resource.exists()) {
	                    return ResponseEntity.ok()
	                    		.contentType(MediaType.APPLICATION_PDF)
	                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                            .body(resource);
	                } else {
	                    throw new NotFoundException("Arquivo não encontrado!");
	                }
	            } catch (MalformedURLException e) {
	                e.printStackTrace();
	                throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter o arquivo!");
	            }
	        }
	    }
	}
	
	@GetMapping("/gerar-relatorio-seguradora-ufpr")
	public ResponseEntity<byte[]> gerarRelatorioSeguradoraUfprPdf() throws IOException, DocumentException {
		
		// TO-DO: Jogar dentro de um try-catch
		
				List<Estagio> estagio = estagioService.buscarEstagioPorSeguradoraUfpr();
				
				 // Alterar para gerar relatórios de N estágios
					byte[] pdf = geradorService.gerarPdfEstagioSeguradoraUfpr(estagio);
					
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_PDF);
					headers.setContentDisposition(ContentDisposition.builder("inline").filename("relatorio-estagios-seguradora-ufpr.pdf").build());
			
					return new ResponseEntity<>(pdf, headers, HttpStatus.OK);

	}
	
	/*@GetMapping("/gerar-relatorio-seguradora-ufpr/excel")
	public ResponseEntity<Resource> gerarRelatorioSeguradoraUfprExcel() throws IOException, DocumentException {
		
		try {
			List<Estagio> estagios = estagioService.buscarEstagioPorSeguradoraUfpr();
			
			Workbook workbook = geradorService.gerarExcelEstagioSeguradoraUfpr(estagios);

	        // Criar arquivo temporário
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        workbook.write(outputStream);
	        byte[] bytes = outputStream.toByteArray();
	        
	        // Salvar o workbook no arquivo temporário
	        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename("relatorio-seguradora-ufpr.xlsx").build());
	
			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		}  catch (PocException e) {
	    	throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
	    }
	}*/
	
	@GetMapping("/gerar-relatorio-seguradora-ufpr-excel")
	public ResponseEntity<Object> gerarRelatorioSeguradoraUfprExcel() throws IOException {
		try {
		    List<Estagio> estagios = estagioService.buscarEstagioPorSeguradoraUfpr();
	
		    if (estagios.isEmpty())
		    	throw new NotFoundException("Não foi encontrado estágio com seguradora UFPR");
		    
		    ByteArrayOutputStream outputStream = geradorExcelService.gerarExcelEstagioSeguradoraUfpr(estagios);
	
		    // Criar um recurso de byte array a partir do fluxo de bytes
		    ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
	
	        // Configurar os cabeçalhos da resposta
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("relatorio-estagios-seguradora-ufpr-excel.xlsx").build());
	        headers.set("Content-Encoding", "UTF-8");
	
	        // Retornar a resposta com o recurso de byte array
	        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (PocException e) {
	    	throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
	    }  
    }
	
	@GetMapping("/gerar-relatorio-empresa/{idContratanteURL}")
	public ResponseEntity<byte[]> gerarPdfEmpresa(@PathVariable String idContratanteURL) throws IOException, DocumentException {
		
		// TO-DO: Jogar dentro de um try-catch
		
		if (idContratanteURL.isBlank() || idContratanteURL.isEmpty()) {
			throw new BadRequestException("GRR do aluno não informado!");
		} else {
			long idInt = Long.parseLong(idContratanteURL);
			
			Optional<Contratante> contratanteFind = contratanteService.buscarPorId(idInt);
			
			if (!contratanteFind.isPresent()) {
				throw new NotFoundException("Contratante não encontrado!");
			} else {
				
				Contratante contratante = contratanteFind.get();
				
				byte[] pdf = geradorService.gerarPdfContratante(contratante);
				
//				byte[] pdf = geradorService.gerarPdfSimples();
					
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_PDF);
				headers.setContentDisposition(ContentDisposition.builder("inline").filename("relatorio-contratante" + contratante.getId() + ".pdf").build());
			
				return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
			}
		}
	}
	
	@GetMapping("/gerar-relatorio-empresa-excel/{idContratanteURL}")
	public ResponseEntity<Object> gerarExcelEmpresa(@PathVariable String idContratanteURL) throws IOException, DocumentException {
		
		try {
		
			if (idContratanteURL.isBlank() || idContratanteURL.isEmpty()) {
				throw new BadRequestException("Id do contratante não informado!");
			} else {
				long idInt = Long.parseLong(idContratanteURL);
				
				Optional<Contratante> contratanteFind = contratanteService.buscarPorId(idInt);
				
				if (!contratanteFind.isPresent()) {
					throw new NotFoundException("Contratante não encontrado!");
				} else {
					
					Contratante contratante = contratanteFind.get();
					
					ByteArrayOutputStream outputStream = geradorExcelService.gerarExcelContratante(contratante);
					
				    // Criar um recurso de byte array a partir do fluxo de bytes
				    ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
			
			        // Configurar os cabeçalhos da resposta
			        HttpHeaders headers = new HttpHeaders();
			        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("relatorio-contratante-" + contratante.getId() + "-excel.xlsx").build());
			        headers.set("Content-Encoding", "UTF-8");
			
			        // Retornar a resposta com o recurso de byte array
			        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
				}
			}
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (PocException e) {
	    	throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
	    }  
	}
	
	@GetMapping("/gerar-relatorio-agenteIntegrador/{idAgenteURL}")
	public ResponseEntity<byte[]> gerarPdfAgenteIntegrador(@PathVariable String idAgenteURL) throws IOException, DocumentException {
		
		// TO-DO: Jogar dentro de um try-catch
		
		if (idAgenteURL.isBlank() || idAgenteURL.isEmpty()) {
			throw new BadRequestException("Id do agente integradornão informado!");
		} else {
			long idInt = Long.parseLong(idAgenteURL);
			
			Optional<AgenteIntegrador> agenteIntegradorFind = agenteIntegradorService.buscarPorId(idInt);
			
			if (!agenteIntegradorFind.isPresent()) {
				throw new NotFoundException("Contratante não encontrado!");
			} else {
				
				AgenteIntegrador agenteIntegrador = agenteIntegradorFind.get();
				
				byte[] pdf = geradorService.gerarPdfAgenteIntegrador(agenteIntegrador);
					
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_PDF);
				headers.setContentDisposition(ContentDisposition.builder("inline").filename("relatorio-agente-integrador-" + agenteIntegrador.getId() + ".pdf").build());
			
				return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
			}
		}
	}
	
	@GetMapping("/gerar-relatorio-agenteIntegrador-excel/{idAgenteURL}")
	public ResponseEntity<Object> gerarExcelAgenteIntegrador(@PathVariable String idAgenteURL) throws IOException, DocumentException {
		
		// TO-DO: Jogar dentro de um try-catch
		
		if (idAgenteURL.isBlank() || idAgenteURL.isEmpty()) {
			throw new BadRequestException("Id do agente integrador não informado!");
		} else {
			long idInt = Long.parseLong(idAgenteURL);
			
			Optional<AgenteIntegrador> agenteIntegradorFind = agenteIntegradorService.buscarPorId(idInt);
			
			if (!agenteIntegradorFind.isPresent()) {
				throw new NotFoundException("Agente integrador não encontrado!");
			} else {
				
				AgenteIntegrador agenteIntegrador = agenteIntegradorFind.get();
				
				ByteArrayOutputStream outputStream = geradorExcelService.gerarExcelAgenteIntegrador(agenteIntegrador);
				
			    // Criar um recurso de byte array a partir do fluxo de bytes
			    ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
		
		        // Configurar os cabeçalhos da resposta
		        HttpHeaders headers = new HttpHeaders();
		        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("relatorio-agente-integrador-" + agenteIntegrador.getId() + "-excel.xlsx").build());
		        headers.set("Content-Encoding", "UTF-8");
		
		        // Retornar a resposta com o recurso de byte array
		        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
			}
		}
	}
	
	@GetMapping("/gerar-relatorio-certificados")
	public ResponseEntity<byte[]> gerarRelatorioCertificadosPdf() throws IOException, DocumentException {
		
		try {
			List<CertificadoDeEstagio> certificados = certificadoDeEstagioService.listarTodosCertificadosDeEstagio();
			
			// Alterar para gerar relatórios de N estágios
			byte[] pdf = geradorService.gerarPdfCertificadosDeEstagio(certificados);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDisposition(ContentDisposition.builder("inline").filename("relatorio-certificados-de-estagio.pdf").build());
	
			return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
		}  catch (PocException e) {
	    	throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar apólice!");
	    }		

	}
	
	@GetMapping("/gerar-relatorio-certificados-excel")
	public ResponseEntity<Object> gerarRelatorioCertificadosExcel() throws IOException, DocumentException {
		
		try {
			List<CertificadoDeEstagio> certificados = certificadoDeEstagioService.listarTodosCertificadosDeEstagio();
			
			if (certificados.isEmpty())
				throw new NotFoundException("Não foram encontrados certificados de estágio.");
			
			// Alterar para gerar relatórios de N estágios
			ByteArrayOutputStream outputStream = geradorExcelService.gerarExcelCertificadosDeEstagio(certificados);
			
		    // Criar um recurso de byte array a partir do fluxo de bytes
		    ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
	
	        // Configurar os cabeçalhos da resposta
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("relatorio-certificados-de-estagio-excel.xlsx").build());
	        headers.set("Content-Encoding", "UTF-8");
	
	        // Retornar a resposta com o recurso de byte array
	        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		}  catch (PocException e) {
	    	throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar certificados!");
	    }		

	}
	
	@GetMapping("/gerar-relatorios-relatorioDeEstagio")
	public ResponseEntity<byte[]> gerarRelatorioRealtoriosDeEstagioPdf() throws IOException, DocumentException {
		
		try {
			List<RelatorioDeEstagio> relatorios = relatorioDeEstagioService.listarTodosRelatorios();
			
			byte[] pdf = geradorService.gerarPdfRelatoriosDeEstagio(relatorios);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDisposition(ContentDisposition.builder("inline").filename("relatorio-relatorios-de-estagio.pdf").build());
	
			return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
		}  catch (PocException e) {
	    	throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar apólice!");
	    }
		
				

	}
	
	@GetMapping("/gerar-relatorios-relatorioDeEstagio-excel")
	public ResponseEntity<Object> gerarRelatorioRealtoriosDeEstagioExcel() throws IOException, DocumentException {
		
		try {
			List<RelatorioDeEstagio> relatorios = relatorioDeEstagioService.listarTodosRelatorios();
			
			// Alterar para gerar relatórios de N estágios
			ByteArrayOutputStream outputStream = geradorExcelService.gerarExcelRelatoriosDeEstagio(relatorios);
			
		    // Criar um recurso de byte array a partir do fluxo de bytes
		    ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
	
	        // Configurar os cabeçalhos da resposta
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("relatorio-relatorios-de-estagio-excel.xlsx").build());
	        headers.set("Content-Encoding", "UTF-8");
	
	        // Retornar a resposta com o recurso de byte array
	        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		}  catch (PocException e) {
	    	throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar apólice!");
	    }		

	}
	
	@GetMapping("/gerar-relatorio-relatorioDeEstagio/{id}")
	public ResponseEntity<Object> gerarRelatorioRealtorioDeEstagioPdf(@PathVariable String id) throws IOException, DocumentException {
		
		try {
			long idLong = Long.parseLong(id);
	    	
	    	if (idLong < 1L)
        		throw new InvalidFieldException("Id inválido.");
			
	    	Optional<RelatorioDeEstagio> relatorio = relatorioDeEstagioService.buscarRelatorioPorId(idLong);
			
	    	if (relatorio.isEmpty()) {
				throw new NotFoundException("Relatório não encontrado!");
			}
	    	
			byte[] pdf = geradorService.gerarPdfRelatorioDeEstagio(relatorio);
			
			RelatorioDeEstagio rel = relatorio.get();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDisposition(ContentDisposition.builder("inline").filename("relatorio-relatorio-de-estagio-" + rel.getId() + ".pdf").build());
	
			return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do relatório de estágio deve ser um inteiro!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (PocException e) {
	    	throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar relatório de estágio!");
	    }
	}
	
	@GetMapping("/gerar-relatorio-relatorioDeEstagio-excel/{id}")
	public ResponseEntity<Object> gerarRelatorioRealtorioDeEstagioExcel(@PathVariable String id) throws IOException, DocumentException {
		
		try {
			long idLong = Long.parseLong(id);
	    	
	    	if (idLong < 1L)
        		throw new InvalidFieldException("Id inválido.");
			
	    	Optional<RelatorioDeEstagio> relatorioFind = relatorioDeEstagioService.buscarRelatorioPorId(idLong);
	    	
	    	if (relatorioFind.isEmpty()) {
				throw new NotFoundException("Relatório não encontrado!");
			}
	    	
	    	RelatorioDeEstagio relatorio = relatorioFind.get();
	    	
	    	// Alterar para gerar relatórios de N estágios
			ByteArrayOutputStream outputStream = geradorExcelService.gerarExcelRelatorioDeEstagio(relatorio);
			
		    // Criar um recurso de byte array a partir do fluxo de bytes
		    ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
	
	        // Configurar os cabeçalhos da resposta
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("relatorio-relatorio-de-estagio-" + relatorio.getId() + "-excel.xlsx").build());
	        headers.set("Content-Encoding", "UTF-8");
	
	        // Retornar a resposta com o recurso de byte array
	        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do relatório de estágio deve ser um inteiro!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (PocException e) {
	    	throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar relatório de estágio!");
	    }
	}

}