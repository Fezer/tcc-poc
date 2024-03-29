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

import br.ufpr.estagio.modulo.dto.ContratanteDTO;
import br.ufpr.estagio.modulo.dto.EnderecoDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.enums.EnumTipoContratante;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Endereco;
import br.ufpr.estagio.modulo.service.ContratanteService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/contratante")
public class ContratanteREST {

	@Autowired
	private ContratanteService contratanteService;

	@Autowired
	private EstagioService estagioService;

	@Autowired
	private TermoDeEstagioService termoDeEstagioService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/")
	public ResponseEntity<Object> criarContratante(@RequestBody ContratanteDTO contratanteDTO) {
		try {
			Contratante contratante = mapper.map(contratanteDTO, Contratante.class);

			if (contratante.getNome() == null || contratante.getNome().isEmpty())
				throw new InvalidFieldException("Preencha o nome.");

			if (contratante.getTelefone() == null || contratante.getTelefone().isEmpty())
				throw new InvalidFieldException("Preencha o telefone.");

			if (contratante.getTipo() == EnumTipoContratante.PessoaFisica && contratante.getCpf() == null)
				throw new InvalidFieldException("Contratante do tipo Pessoa Física deve possuir CPF.");

			if (contratante.getTipo() == EnumTipoContratante.PessoaFisica && contratante.getCpf().isEmpty())
				throw new InvalidFieldException("CPF inválido.");

			if (contratante.getTipo() == EnumTipoContratante.PessoaJuridica && contratante.getCnpj() == null)
				throw new InvalidFieldException("Contratante do tipo Pessoa Jurídica deve possuir CNPJ.");

			if (contratante.getTipo() == EnumTipoContratante.PessoaJuridica && contratante.getCnpj().isEmpty())
				throw new InvalidFieldException("CNPJ inválido.");

			if (contratante.getTipo() == EnumTipoContratante.PessoaFisica && (contratante.getCnpj() != null))
				throw new InvalidFieldException("Contratante do tipo Pessoa Física não deve possuir CNPJ.");

			if (contratante.getTipo() == EnumTipoContratante.PessoaJuridica && (contratante.getCpf() != null))
				throw new InvalidFieldException("Contratante do tipo Pessoa Jurídica não deve possuir CPF.");

			if (contratante.getRepresentanteEmpresa() == null || contratante.getRepresentanteEmpresa().isEmpty())
				throw new InvalidFieldException("Preencha o nome do representante da empresa.");

			contratante.setAtivo(true);

			contratante = contratanteService.criarContratante(contratante);

			contratanteDTO = mapper.map(contratante, ContratanteDTO.class);

			return new ResponseEntity<>(contratanteDTO, HttpStatus.CREATED);

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
	public ResponseEntity<Object> buscarContratantePorId(@PathVariable String id) {
		try {
			long idLong = Long.parseLong(id);

			if (idLong < 1) {
				throw new InvalidFieldException("Id do contratante inválido!");
			}

			Optional<Contratante> contratante = contratanteService.buscarPorId(idLong);

			if (contratante.isEmpty()) {
				throw new NotFoundException("Contratante não encontrado!");
			}
			ContratanteDTO contratanteDTO = mapper.map(contratante, ContratanteDTO.class);
			return ResponseEntity.status(HttpStatus.OK).body(contratanteDTO);

		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do contratante deve ser um número!");
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

	@GetMapping("/nome/{nomeContratante}")
	public ResponseEntity<Object> buscarContratanteNome(@PathVariable String nomeContratante) {
		try {
			List<Contratante> contratantesFind = contratanteService.buscarPorNome(nomeContratante);

			if (contratantesFind.isEmpty()) {
				throw new NotFoundException("Contratante não encontrado!");
			} else {
				List<ContratanteDTO> contratantesDTO = contratantesFind.stream()
						.map(ap -> mapper.map(ap, ContratanteDTO.class)).collect(Collectors.toList());
				return ResponseEntity.status(HttpStatus.OK).body(contratantesDTO);
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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

	@GetMapping("/nome/contendo/{nomeContratante}")
	public ResponseEntity<?> buscarContratantePorNomeSimilar(@PathVariable String nomeContratante) {
		try {
			List<Contratante> contratantesFind = contratanteService.buscarPorNomeContendo(nomeContratante);
			if (contratantesFind.isEmpty()) {
				throw new NotFoundException("Contratante não encontrado!");
			} else {
				List<ContratanteDTO> contratantesDTO = contratantesFind.stream()
						.map(ap -> mapper.map(ap, ContratanteDTO.class)).collect(Collectors.toList());
				return ResponseEntity.ok().body(contratantesDTO);
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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

	@GetMapping("/nome/comecandoPor/{nomeContratante}")
	public ResponseEntity<?> buscarContratantePorNomeComecandoPor(@PathVariable String nomeContratante) {
		try {
			List<Contratante> contratantesFind = contratanteService
					.buscarContratantePorNomeComecandoPor(nomeContratante);
			if (contratantesFind.isEmpty()) {
				throw new NotFoundException("Contratante não encontrado!");
			} else {
				List<ContratanteDTO> contratantesDTO = contratantesFind.stream()
						.map(ap -> mapper.map(ap, ContratanteDTO.class)).collect(Collectors.toList());
				return ResponseEntity.ok().body(contratantesDTO);
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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
	public ResponseEntity<Object> listarContratantes(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(required = false) String nome, @RequestParam(required = false) String cnpj) {
		try {
			Optional<String> nomeOptional = nome == null ? Optional.empty() : Optional.of(nome);
			Optional<String> cnpjOptional = cnpj == null ? Optional.empty() : Optional.of(cnpj);

			Page<Contratante> contratantes = contratanteService.listarContratantesPaginated(page, nomeOptional,
					cnpjOptional);

			if (contratantes.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(contratantes);
			}

			List<ContratanteDTO> contratantesDTO = contratantes.stream().map(ap -> mapper.map(ap, ContratanteDTO.class))
					.collect(Collectors.toList());

			return ResponseEntity.status(HttpStatus.OK)
					.body(new PageImpl<>(contratantesDTO, contratantes.getPageable(), contratantes.getTotalElements()));

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
	public ResponseEntity<Object> atualizarContratante(@PathVariable String id,
			@RequestBody ContratanteDTO contratanteDTO) {
		try {

			long idLong = Long.parseLong(id);

			if (idLong < 1) {
				throw new InvalidFieldException("Id do contratante inválido!");
			}

			Optional<Contratante> contratante = contratanteService.buscarPorId(idLong);

			if (contratante.isPresent()) {
				if (contratanteDTO.getNome() == null || contratanteDTO.getNome().isEmpty())
					throw new InvalidFieldException("Preencha o nome.");

				if (contratanteDTO.getTelefone() == null || contratanteDTO.getTelefone().isEmpty())
					throw new InvalidFieldException("Preencha o telefone.");

				if (contratanteDTO.getTipo() == EnumTipoContratante.PessoaFisica && contratanteDTO.getCpf() == null)
					throw new InvalidFieldException("Contratante do tipo Pessoa Física deve possuir CPF.");

				if (contratanteDTO.getTipo() == EnumTipoContratante.PessoaFisica && contratanteDTO.getCpf().isEmpty())
					throw new InvalidFieldException("CPF inválido.");

				if (contratanteDTO.getTipo() == EnumTipoContratante.PessoaJuridica && contratanteDTO.getCnpj() == null)
					throw new InvalidFieldException("Contratante do tipo Pessoa Jurídica deve possuir CNPJ.");

				if (contratanteDTO.getTipo() == EnumTipoContratante.PessoaJuridica
						&& contratanteDTO.getCnpj().isEmpty())
					throw new InvalidFieldException("CNPJ inválido.");

				if (contratanteDTO.getTipo() == EnumTipoContratante.PessoaFisica && (contratanteDTO.getCnpj() != null))
					throw new InvalidFieldException("Contratante do tipo Pessoa Física não deve possuir CNPJ.");

				if (contratanteDTO.getTipo() == EnumTipoContratante.PessoaJuridica && (contratanteDTO.getCpf() != null))
					throw new InvalidFieldException("Contratante do tipo Pessoa Jurídica não deve possuir CPF.");

				if (contratanteDTO.getRepresentanteEmpresa() == null
						|| contratanteDTO.getRepresentanteEmpresa().isEmpty())
					throw new InvalidFieldException("Preencha o nome do representante da empresa.");

				Contratante contratanteAtualizado = mapper.map(contratanteDTO, Contratante.class);
				contratanteAtualizado.setId(idLong);
				contratanteAtualizado = contratanteService.atualizarContratante(contratanteAtualizado);
				ContratanteDTO contratanteDTOAtualizado = mapper.map(contratanteAtualizado, ContratanteDTO.class);
				return ResponseEntity.ok().body(contratanteDTOAtualizado);
			} else {
				throw new NotFoundException("Contratante não encontrado!");
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do contratante deve ser um número!");
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
	public ResponseEntity<?> excluirContratante(@PathVariable String id) {
		try {

			long idLong = Long.parseLong(id);

			if (idLong < 1) {
				throw new InvalidFieldException("Id do contratante inválido!");
			}

			Optional<Contratante> contratanteFind = contratanteService.buscarPorId(idLong);

			if (contratanteFind.isPresent()) {
				Contratante contratante = contratanteFind.get();

				boolean presenteEmTermosDeEstagio = termoDeEstagioService
						.listarTermosDeEstagioPorContratante(contratante);
				boolean presenteEmEstagios = estagioService.listarEstagiosPorContratante(contratante);

				if (presenteEmTermosDeEstagio)
					throw new InvalidFieldException(
							"Não é possível excluir um contratante presente em termo de estágio.");

				if (presenteEmEstagios)
					throw new InvalidFieldException("Não é possível excluir um contratante presente em estágio.");

				contratanteService.excluirContratante(contratante);
				return ResponseEntity.noContent().build();
			} else {
				throw new NotFoundException("Contratante não encontrado!");
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do contratante deve ser um número!");
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

	@PostMapping("/{idContratante}/endereco/")
	public ResponseEntity<Object> criarEnderecoContratante(@PathVariable String idContratante,
			@RequestBody EnderecoDTO enderecoDTO) {
		try {
			long idLong = Long.parseLong(idContratante);

			if (idLong < 1) {
				throw new InvalidFieldException("Id do contratante inválido!");
			}

			Optional<Contratante> contratanteFind = contratanteService.buscarPorId(idLong);

			if (contratanteFind.isEmpty()) {
				throw new NotFoundException("Contratante não encontrado!");
			} else {
				if (enderecoDTO.getRua() == null || enderecoDTO.getRua().isEmpty())
					throw new InvalidFieldException("Rua inválida.");

				if (enderecoDTO.getNumero() < 1)
					throw new InvalidFieldException("Número inválido.");

				if (enderecoDTO.getCidade() == null || enderecoDTO.getCidade().isEmpty())
					throw new InvalidFieldException("Cidade inválida.");

				if (enderecoDTO.getUf() == null || enderecoDTO.getUf().isEmpty())
					throw new InvalidFieldException("Estado inválido.");

				if (enderecoDTO.getCep() == null || enderecoDTO.getCep().isEmpty())
					throw new InvalidFieldException("CEP inválido.");

				Endereco endereco = mapper.map(enderecoDTO, Endereco.class);
				Contratante contratante = contratanteService.criarEnderecoContratante(contratanteFind.get(), endereco);
				ContratanteDTO contratanteDTO = mapper.map(contratante, ContratanteDTO.class);
				return ResponseEntity.ok().body(contratanteDTO);
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do contratante deve ser um número!");
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

	@PutMapping("/{id}/alterar-status")
	public ResponseEntity<Object> alterarStatusContratante(@PathVariable String id) {
		try {
			Long idLong = Long.parseLong(id);

			if (idLong < 1) {
				throw new InvalidFieldException("Id docontratante inválido!");
			}

			Optional<Contratante> contratanteFind = contratanteService.buscarPorId(idLong);

			if (contratanteFind.isPresent()) {
				Contratante contratante = contratanteFind.get();

				if (contratante.isAtivo())
					contratante.setAtivo(false);

				else if (!contratante.isAtivo())
					contratante.setAtivo(true);

				contratante.setId(idLong);
				contratante = contratanteService.atualizarContratante(contratante);

				ContratanteDTO contratanteDTOAtualizado = mapper.map(contratante, ContratanteDTO.class);
				return ResponseEntity.ok().body(contratanteDTOAtualizado);
			} else {
				throw new NotFoundException("Contratante não encontrado!");
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O id deve ser um inteiro!");
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
