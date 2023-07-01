package br.ufpr.estagio.modulo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.dto.TermoDeRescisaoDTO;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.model.TermoDeRescisao;
import br.ufpr.estagio.modulo.service.TermoDeRescisaoService;

@CrossOrigin
@RestController
@RequestMapping("/termoDeRescisao")
public class TermoDeRescisaoREST {

	@Autowired
	private TermoDeRescisaoService termoDeRescisaoService;
	@Autowired
	private ModelMapper mapper;

	@PostMapping("")
	public ResponseEntity<Object> inserir(@RequestBody TermoDeRescisaoDTO termo) {
		try {
			TermoDeRescisao newTermo = termoDeRescisaoService.novo(mapper.map(termo, TermoDeRescisao.class));
			return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(newTermo, TermoDeRescisaoDTO.class));
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("")
	public ResponseEntity<Object> listarTodos(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(required = false) Optional<String> grrAluno,
			@RequestParam(required = false) Optional<EnumEtapaFluxo> etapaFluxo,
			@RequestParam(required = false) Optional<Boolean> cienciaCOAFE,
			@RequestParam(required = false) Optional<Boolean> cienciaOrientador,
			@RequestParam(required = false) Optional<Boolean> cienciaCOE,
			@RequestParam(required = false) Optional<Boolean> cienciaCoordenacao) {
		try {
			Optional<String> grrOptional = grrAluno == null ? Optional.empty() : grrAluno;
			Optional<EnumEtapaFluxo> etapaFluxoOptional = etapaFluxo == null ? Optional.empty() : etapaFluxo;
			Optional<Boolean> cienciaCOAFEOptional = cienciaCOAFE == null ? Optional.empty() : cienciaCOAFE;
			Optional<Boolean> cienciaOrientadorOptional = cienciaOrientador == null ? Optional.empty()
					: cienciaOrientador;
			Optional<Boolean> cienciaCOEOptional = cienciaCOE == null ? Optional.empty() : cienciaCOE;
			Optional<Boolean> cienciaCoordenacaoOptional = cienciaCoordenacao == null ? Optional.empty()
					: cienciaCoordenacao;

			Page<TermoDeRescisao> lista = termoDeRescisaoService.listarTodosPaginated(page, grrOptional,
					etapaFluxoOptional, cienciaCOAFEOptional, cienciaCoordenacaoOptional, cienciaOrientadorOptional,
					cienciaCOEOptional);
			if (lista.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
			} else {

				List<TermoDeRescisaoDTO> listaDTO = lista.stream()
						.map(termo -> mapper.map(termo, TermoDeRescisaoDTO.class)).collect(Collectors.toList());

				return ResponseEntity.status(HttpStatus.OK)
						.body(new PageImpl<>(listaDTO, lista.getPageable(), lista.getTotalElements()));

			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não foi possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> listarTermo(@PathVariable String id) {
		try {
			long idLong = Long.parseLong(id);

			if (idLong < 1) {
				throw new InvalidFieldException("Id do certificado de estágio inválido!");
			}

			Optional<TermoDeRescisao> termoOptional = termoDeRescisaoService.buscarPorId(idLong);

			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeRescisao termo = termoOptional.get();
				TermoDeRescisaoDTO termoDTO = mapper.map(termo, TermoDeRescisaoDTO.class);
				termoDTO.setAluno(termo.getEstagio().getAluno().getNome());
				termoDTO.setGrrAluno(termo.getEstagio().getAluno().getMatricula());
				return new ResponseEntity<>(termoDTO, HttpStatus.OK);
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do termo de rescisão deve ser um número!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não foi possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody TermoDeRescisaoDTO termo) {
		try {
			long idLong = Long.parseLong(id);

			if (idLong < 1) {
				throw new InvalidFieldException("Id do certificado de estágio inválido!");
			}

			Optional<TermoDeRescisao> termoFind = termoDeRescisaoService.buscarPorId(idLong);

			if (termoFind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				if (termo.getPeriodoTotalRecesso() < 1) {
					throw new InvalidFieldException("Período total de recesso inválido!");
				}

				if (termo.getDataTermino().before(new Date())) {
					throw new InvalidFieldException("Data de término não pode ser igual ou anterior a hoje!");
				}

				TermoDeRescisao termoAtualizado = termoDeRescisaoService.atualizarDados(termoFind.get(), termo);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeRescisaoDTO.class));
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do termo de rescisão deve ser um número!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não foi possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		try {
			Optional<TermoDeRescisao> termofind = termoDeRescisaoService.buscarPorId(id);
			if (termofind.isEmpty()) {
				throw new NotFoundException("Termo de rescisão não encontrado!");
			} else {
				termoDeRescisaoService.deletar(id);
				return ResponseEntity.status(HttpStatus.OK).body(null);
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
