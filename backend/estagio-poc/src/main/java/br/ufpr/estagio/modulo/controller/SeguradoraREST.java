package br.ufpr.estagio.modulo.controller;

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

import br.ufpr.estagio.modulo.dto.ApoliceDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.dto.SeguradoraDTO;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.service.ApoliceService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.SeguradoraService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/seguradora")
public class SeguradoraREST {

	@Autowired
	private ApoliceService apoliceService;

	@Autowired
	private EstagioService estagioService;

	@Autowired
	private TermoDeEstagioService termoDeEstagioService;

	@Autowired
	private SeguradoraService seguradoraService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/")
	public ResponseEntity<Object> criarSeguradora(@RequestBody SeguradoraDTO seguradoraDTO) {
		try {
			Seguradora seguradora = mapper.map(seguradoraDTO, Seguradora.class);

			if (seguradoraDTO.getNome() == null || seguradoraDTO.getNome().isEmpty())
				throw new InvalidFieldException("Nome inválido.");

			if (seguradoraDTO.isSeguradoraUfpr() != true && seguradoraDTO.isSeguradoraUfpr() != false)
				throw new InvalidFieldException("Selecione se a seguradora é uma seguradora UFPR.");

			if (seguradoraDTO.isAtiva() != true && seguradoraDTO.isAtiva() != false)
				seguradoraDTO.setAtiva(true);

			seguradora = seguradoraService.criarSeguradora(seguradora);
			seguradoraDTO = mapper.map(seguradora, SeguradoraDTO.class);
			return new ResponseEntity<>(seguradoraDTO, HttpStatus.CREATED);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		/**
		 * catch (InvalidFieldException ex) {
		 * throw new InvalidFieldException("Nome inválido.");
		 * }
		 * Caso o front não queira verificar o tipo de objeto retornado, a exceção
		 * deverá ser lançada da maneira acima
		 */

		/**
		 * catch (Exception ex) {
		 * ErrorResponse response = new ErrorResponse("Erro interno no servidor.");
		 * return
		 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); //
		 * Resposta de erro interno do servidor com a mensagem de erro no corpo
		 * }
		 */
	}

	@PostMapping("/{idSeguradora}/apolice")
	public ResponseEntity<Object> criarApolice(@PathVariable String idSeguradora, @RequestBody ApoliceDTO apolice) {
		try {
			int idSeguradoraInt = Integer.parseInt(idSeguradora);

			Optional<Seguradora> seguradoraFind = seguradoraService.buscarSeguradoraPorId(idSeguradoraInt);

			if (!seguradoraFind.isPresent())
				throw new NotFoundException("Não foi encontrada uma seguradora com o id informado.");

			Seguradora seguradora = seguradoraFind.get();

			if (apolice.getNumero() < 1)
				throw new InvalidFieldException("Número inválido.");

			if (apolice.getDataInicio() == null)
				throw new InvalidFieldException("Insira uma data de início.");

			if (apolice.getDataFim() == null)
				throw new InvalidFieldException("Insira uma data de fim.");

			if (apolice.getDataInicio().after(apolice.getDataFim()))
				throw new InvalidFieldException("Data de início não pode ser superior à data de fim");

			Apolice apoliceNovo = mapper.map(apolice, Apolice.class);
			apoliceNovo = apoliceService.criarApolice(apoliceNovo);
			apoliceNovo = apoliceService.associarSeguradoraApolice(apoliceNovo, seguradora);
			apolice = mapper.map(apoliceNovo, ApoliceDTO.class);
			return new ResponseEntity<>(apolice, HttpStatus.CREATED);
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id da seguradora deve ser um inteiro!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarPorId(@PathVariable String id) {
		try {
			int idInt = Integer.parseInt(id);

			if (idInt < 1) {
				throw new InvalidFieldException("Id da seguradora inválido!");
			}

			Optional<Seguradora> seguradora = seguradoraService.buscarSeguradoraPorId(idInt);

			if (seguradora.isEmpty()) {
				throw new NotFoundException("Seguradora não encontrada!");
			}

			SeguradoraDTO seguradoraDTO = mapper.map(seguradora, SeguradoraDTO.class);
			return ResponseEntity.status(HttpStatus.OK).body(seguradoraDTO);
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id da seguradora deve ser um inteiro!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	@GetMapping("/")
	public ResponseEntity<Object> listarSeguradoras(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(required = false) Optional<String> nome,
			@RequestParam(required = false) Optional<Boolean> ativo,
			@RequestParam(required = false) Optional<Boolean> seguradoraUFPR

	) {
		Optional<String> nomeOptional = nome == null ? Optional.empty() : nome;
		Optional<Boolean> ativoOptional = ativo == null ? Optional.empty() : ativo;
		Optional<Boolean> ufprOptional = seguradoraUFPR == null ? Optional.empty() : seguradoraUFPR;

		try {
			Page<Seguradora> seguradoras = seguradoraService.listarSeguradorasPaginated(
					page,
					nomeOptional,
					ativoOptional,
					ufprOptional);

			if (seguradoras.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(seguradoras);
			}

			List<SeguradoraDTO> seguradoraDTOs = seguradoras.stream()
					.map(ap -> mapper.map(ap, SeguradoraDTO.class))
					.collect(Collectors.toList());

			return ResponseEntity.status(HttpStatus.OK).body(
					new PageImpl<>(seguradoraDTOs, seguradoras.getPageable(),
							seguradoras.getTotalElements()));

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarSeguradora(@PathVariable String id,
			@RequestBody SeguradoraDTO seguradoraDTO) {
		try {
			int idInt = Integer.parseInt(id);

			if (idInt < 1) {
				throw new InvalidFieldException("Id da seguradora inválido!");
			}

			Optional<Seguradora> seguradora = seguradoraService.buscarSeguradoraPorId(idInt);

			if (seguradora.isPresent()) {

				/*
				 * if (seguradoraDTO.getNome() == null && (seguradoraDTO.isSeguradoraUfpr() !=
				 * false && seguradoraDTO.isSeguradoraUfpr() != true)) {
				 * Seguradora seguradoraAtualizada = mapper.map(seguradoraDTO,
				 * Seguradora.class);
				 * seguradoraAtualizada.setId(id);
				 * System.out.println(seguradoraAtualizada.isAtiva());
				 * seguradoraAtualizada =
				 * seguradoraService.ativarDesativarSeguradora(seguradoraAtualizada);
				 * SeguradoraDTO seguradoraDTOAtualizada = mapper.map(seguradoraAtualizada,
				 * SeguradoraDTO.class);
				 * return ResponseEntity.ok().body(seguradoraDTOAtualizada);
				 * }
				 */

				if (seguradoraDTO.getNome().isEmpty())
					throw new InvalidFieldException("Nome inválido!");

				Seguradora seguradoraAtualizada = mapper.map(seguradoraDTO, Seguradora.class);
				seguradoraAtualizada.setId(idInt);
				seguradoraAtualizada = seguradoraService.atualizarSeguradora(seguradoraAtualizada);
				SeguradoraDTO seguradoraDTOAtualizada = mapper.map(seguradoraAtualizada, SeguradoraDTO.class);
				return ResponseEntity.ok().body(seguradoraDTOAtualizada);

			} else {
				throw new NotFoundException("Seguradora não encontrada!");
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id da seguradora deve ser um inteiro!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	@PutMapping("/ativar-desativar/{id}")
	public ResponseEntity<Object> ativarDesativarSeguradora(@PathVariable String id,
			@RequestBody SeguradoraDTO seguradoraDTO) {

		try {
			int idInt = Integer.parseInt(id);

			if (idInt < 1) {
				throw new InvalidFieldException("Id da seguradora inválido!");
			}

			Optional<Seguradora> seguradora = seguradoraService.buscarSeguradoraPorId(idInt);

			if (seguradora.isPresent()) {
				Seguradora seguradoraAtualizada = mapper.map(seguradoraDTO, Seguradora.class);

				seguradoraAtualizada.setId(idInt);
				seguradoraAtualizada = seguradoraService.ativarDesativarSeguradora(seguradoraAtualizada);

				// Lança a exceção caso tente ativar uma seguradora já ativa e vice-versa
				if (seguradoraAtualizada.getError() != null)
					throw new InvalidFieldException(seguradoraAtualizada.getError());

				SeguradoraDTO seguradoraDTOAtualizada = mapper.map(seguradoraAtualizada, SeguradoraDTO.class);
				return ResponseEntity.ok().body(seguradoraDTOAtualizada);
			} else {
				throw new NotFoundException("Seguradora não encontrada!");
			}

		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id da seguradora deve ser um inteiro!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirSeguradora(@PathVariable String id) {
		try {
			int idInt = Integer.parseInt(id);

			if (idInt < 1) {
				throw new InvalidFieldException("Id da seguradora inválido!");
			}

			Optional<Seguradora> seguradoraFind = seguradoraService.buscarSeguradoraPorId(idInt);

			if (seguradoraFind.isPresent()) {
				Seguradora seguradora = seguradoraFind.get();

				boolean presenteEmTermosDeEstagio = termoDeEstagioService
						.listarTermosDeEstagioPorSeguradora(seguradora);
				boolean presenteEmEstagios = estagioService.listarEstagiosPorSeguradora(seguradora);

				if (presenteEmTermosDeEstagio)
					throw new InvalidFieldException(
							"Não é possível excluir uma seguradora presente em termo de estágio.");

				if (presenteEmEstagios)
					throw new InvalidFieldException("Não é possível excluir uma seguradora presente em estágio.");

				seguradoraService.excluirSeguradora(seguradora);
				return ResponseEntity.noContent().build();
			} else {
				throw new NotFoundException("Seguradora não encontrada!");
			}

		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id da seguradora deve ser um inteiro!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}
}
