package br.ufpr.estagio.modulo.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.DescricaoAjustesDTO;
import br.ufpr.estagio.modulo.dto.JustificativaDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.TermoDeRescisaoDTO;
import br.ufpr.estagio.modulo.enums.EnumTipoDocumento;
import br.ufpr.estagio.modulo.exception.BadRequestException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeRescisao;
import br.ufpr.estagio.modulo.service.AlunoService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeRescisaoService;

@CrossOrigin
@RestController
@RequestMapping("/coordenacao")
public class CoordenacaoREST {

	@Autowired
	private TermoDeEstagioService termoDeEstagioService;
	
	@Autowired
	private TermoDeRescisaoService termoDeRescisaoService;
	
	@Autowired
	private AlunoService alunoService;
		
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/termo/pendenteAprovacaoCoordenacao")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosPendenteAprovacao(){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosDeCompromissoPendenteAprovacaoCoordenacao();
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
	
	@GetMapping("/termo/pendenteAprovacaoCoordenacaoFiltro")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosPendenteAprovacaoPorTipoEstagio(@RequestParam String tipoEstagio){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosDeCompromissoPendenteAprovacaoCoordenacaoPorTipoEstagio(tipoEstagio);
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
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosIndeferidos(){
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
	
	@GetMapping("/termo/pendenteCienciaCoordenacao")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosIndeferidosPendentesCienciaCoordenacao(){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosDeCompromissoIndeferidosPendentesCienciaCoordenacao();
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
	
	@GetMapping("/termoAditivo/pendenteAprovacaoCoordenacao")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosAditivosPendenteAprovacao(){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosAditivosPendenteAprovacaoCoordenacao();
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
	
	@GetMapping("/termoAditivo/pendenteAprovacaoCoordenacaoFiltro")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosAditivosPendenteAprovacaoPorTipoEstagio(@RequestParam String tipoEstagio){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosAditivosPendenteAprovacaoCoordenacaoPorTipoEstagio(tipoEstagio);
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
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosAditivosIndeferidos(){
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
	
	@GetMapping("/termoAditivo/pendenteCienciaCoordenacao")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosAditivosIndeferidosPendentesCienciaCoordenacao(){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosAditivosIndeferidosPendentesCienciaCoordenacao();
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
	
	@PutMapping("/termo/{idTermo}/indeferir")
	public ResponseEntity<TermoDeEstagioDTO> indeferirTermoDeCompromisso(@PathVariable Long idTermo, @RequestBody JustificativaDTO justificativa){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.indeferirTermoDeEstagioCoordenacao(termo, justificativa);
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
			termo = termoDeEstagioService.solicitarAjutesTermoDeEstagioCoordenacao(termo, descricaoAjustes);
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
	
	@PutMapping("/termo/{idTermo}/cienciaFormacaoSupervisor")
	public ResponseEntity<TermoDeEstagioDTO> darCienciaFormacaoSupervisor(@PathVariable Long idTermo){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.darCienciaFormacaoSupervisorCoordenacao(termo);
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
	
	@PutMapping("/termo/{idTermo}/cienciaPlanoAtividades")
	public ResponseEntity<TermoDeEstagioDTO> darCienciaPlanoAtividades(@PathVariable Long idTermo){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.darCienciaPlanoAtividadesCoordenacao(termo);
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
	
	@PutMapping("/termo/{idTermo}/cienciaIra")
	public ResponseEntity<TermoDeEstagioDTO> darCienciaIra(@PathVariable Long idTermo){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.darCienciaIraCoordenacao(termo);
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
	
	@PutMapping("/termo/{idTermo}/cienciaIndeferimento")
	public ResponseEntity<TermoDeEstagioDTO> darCienciaTermoIndeferido(@PathVariable Long idTermo){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.darCienciaIndeferimentoCoordenacao(termo);
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
			termo = termoDeEstagioService.aprovarTermoDeEstagioCoordenacao(termo);
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
	public ResponseEntity<List<TermoDeRescisaoDTO>> listarTermosDeRescisaoPendenteCienciaCoordenacao(){
		try {
			List<TermoDeRescisao> listaTermosDeRescisao = termoDeRescisaoService.listarTermosDeRescisaoPendenteCienciaCoordenacao();
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
			termo = termoDeRescisaoService.darCienciaTermoDeRescisaoCoordenacao(termo);
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
	
	@GetMapping("/{grrAlunoURL}/download-termo")
	public ResponseEntity<Resource> downloadTermo(@PathVariable String grrAlunoURL) {
	    if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
	        throw new BadRequestException("GRR do aluno não informado!");
	    } else {
	        Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
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

}