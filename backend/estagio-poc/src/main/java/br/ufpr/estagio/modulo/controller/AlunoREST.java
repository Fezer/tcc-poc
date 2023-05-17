package br.ufpr.estagio.modulo.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.ufpr.estagio.modulo.dto.ApoliceDTO;
import br.ufpr.estagio.modulo.dto.CertificadoDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.ContratanteDTO;
import br.ufpr.estagio.modulo.dto.DadosAuxiliaresDTO;
import br.ufpr.estagio.modulo.dto.DadosBancariosDTO;
import br.ufpr.estagio.modulo.dto.AlunoDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.dto.FichaDeAvaliacaoDTO;
import br.ufpr.estagio.modulo.dto.RelatorioDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoDocumento;
import br.ufpr.estagio.modulo.exception.BadRequestException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.DadosAuxiliares;
import br.ufpr.estagio.modulo.model.DadosBancarios;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.FichaDeAvaliacao;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.service.AlunoService;
import br.ufpr.estagio.modulo.service.CertificadoDeEstagioService;
import br.ufpr.estagio.modulo.service.DadosAuxiliaresService;
import br.ufpr.estagio.modulo.service.DadosBancariosService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.FichaDeAvaliacaoService;
import br.ufpr.estagio.modulo.service.GeradorDePdfService;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


@CrossOrigin
@RestController
@RequestMapping("/aluno")
public class AlunoREST {

	@Autowired
	private EstagioService estagioService;
	
	@Autowired
	private TermoDeEstagioService termoDeEstagioService;
	
	@Autowired
	private RelatorioDeEstagioService relatorioDeEstagioService;
	
	@Autowired
	private FichaDeAvaliacaoService fichaDeAvaliacaoService;
	
	@Autowired
	private CertificadoDeEstagioService certificadoDeEstagioService;
		
	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private DadosAuxiliaresService dadosService;
	
	@Autowired
	private DadosBancariosService dadosBancariosService;

	@Autowired
	private GeradorDePdfService geradorService;
	
	@Autowired
	private ModelMapper mapper;
	
	private final ResourceLoader resourceLoader;

	public AlunoREST(ResourceLoader resourceLoader) {
	    this.resourceLoader = resourceLoader;
	}
	
	// Isso é um listarAluno.
	//É só mudar então.
	@GetMapping("/{grrAlunoURL}")
	public ResponseEntity<Aluno> listarTermo(@PathVariable String grrAlunoURL) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					return ResponseEntity.status(HttpStatus.OK).body(mapper.map(aluno, Aluno.class));
				}
			}
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@PostMapping("/{grrAlunoURL}/estagio")
	public ResponseEntity<EstagioDTO> novoEstagio(@PathVariable String grrAlunoURL) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Estagio estagio = alunoService.novoEstagio(aluno);
					EstagioDTO estagioDTO = estagioService.toEstagioDTO(estagio);
					return new ResponseEntity<>(estagioDTO, HttpStatus.CREATED);
				}
			}
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@DeleteMapping("/{grrAlunoURL}/estagio/{idEstagio}")
	public ResponseEntity<Void> deletarEstagio(@PathVariable String grrAlunoURL, @PathVariable Long idEstagio) {
	    try {
	        if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
	            throw new BadRequestException("GRR do aluno não informado!");
	        } else {
	            Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
	            if (aluno == null) {
	                throw new NotFoundException("Aluno não encontrado!");
	            } else {
	                Estagio estagio = alunoService.buscarEstagioPorId(idEstagio);
	                if (estagio == null) {
	                    throw new NotFoundException("Estágio não encontrado!");
	                } else {
	                    alunoService.cancelarEstagio(estagio);
	                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	                }
	            }
	        }
	    } catch (NumberFormatException e) {
	        throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
	    } catch (PocException e) {
	        e.printStackTrace();
	        throw e;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
	    }
	}
	
	@PutMapping("/{grrAlunoURL}/estagio/{idEstagio}")
	@ResponseBody
	public ResponseEntity<Object> cancelarEstagio(@PathVariable String grrAlunoURL, @PathVariable Long idEstagio) {
	    try {
	        if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
	            throw new BadRequestException("GRR do aluno não informado!");
	        } else {
	            Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
	            if (aluno == null) {
	                throw new NotFoundException("Aluno não encontrado!");
	            } else {
	                Estagio estagio = alunoService.buscarEstagioPorId(idEstagio);
	                if (estagio == null) {
	                    throw new NotFoundException("Estágio não encontrado!");
	                } else {
	                	if (estagio.getStatusEstagio() == EnumStatusEstagio.Aprovado) {
	                		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Não é possível cancelar um estágio aprovado."));
	            		} else {
	            			alunoService.cancelarEstagio(estagio);
	            		}
	                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	                }
	            }
	        }
	    } catch (NumberFormatException e) {
	        throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
	    } catch (PocException e) {
	        e.printStackTrace();
	        throw e;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
	    }
	}
	
	@GetMapping("/{grrAlunoURL}/dadosAuxiliares")
	@ResponseBody
	public ResponseEntity<Object> listarDadosAuxiliares(@PathVariable String grrAlunoURL) {
	    try {
	    	if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Optional<Aluno> alunoOptional = alunoService.buscarAlunoGrr(grrAlunoURL);
		        Aluno aluno = alunoOptional.get();
		        
		        if (aluno == null)
					throw new NotFoundException("Aluno não encontrado!");
		        
				DadosAuxiliares dados = new DadosAuxiliares();
				
				dados.setId(aluno.getDadosAuxiliares().getId());
				
				dados = dadosService.buscarDadosAuxiliaresPorId(dados.getId());
				
				if (dados == null)
					throw new NotFoundException("Dados não encontrados!");
				
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(dados, DadosAuxiliares.class));
			}
	    	
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/{grrAlunoURL}/dadosAuxiliares")
	@ResponseBody
	public ResponseEntity<Object> atualizarDadosAuxiliares(@PathVariable String grrAlunoURL, @RequestBody DadosAuxiliaresDTO dadosDTO) {
	    try {
	        Optional<Aluno> aluno = alunoService.buscarAlunoGrr(grrAlunoURL);
	        Aluno alunoAntigo = aluno.get();
	        
	        if (aluno.isPresent()) {
	        	DadosAuxiliares dadosAtualizado = mapper.map(dadosDTO, DadosAuxiliares.class);

	        	dadosAtualizado.setId(alunoAntigo.getDadosAuxiliares().getId());
	        	dadosAtualizado.setAluno(alunoAntigo);
	        	
	        	dadosAtualizado = dadosService.atualizarDados(dadosAtualizado);
	        	DadosAuxiliaresDTO dadosDTOAtualizado = mapper.map(dadosAtualizado, DadosAuxiliaresDTO.class);
	        	
	        	return ResponseEntity.ok().body(dadosDTOAtualizado);
	        } else {
				throw new NotFoundException("Os dados não foram encontrados.");
			}
	        
	        
	    } catch (NumberFormatException e) {
	        throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
	    } catch (PocException e) {
	        e.printStackTrace();
	        throw e;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
	    }
	}
	
	@PostMapping("/{grrAlunoURL}/dadosBancarios")
	public ResponseEntity<DadosBancariosDTO> criarDadosBancarios(@PathVariable String grrAlunoURL, @RequestBody DadosBancariosDTO dadosBancariosDTO){
		try {
			Optional<Aluno> aluno = alunoService.buscarAlunoGrr(grrAlunoURL);
	        Aluno alunoAntigo = aluno.get();
	        
	        if (aluno.isPresent()) {
				DadosBancarios dadosBancarios = mapper.map(dadosBancariosDTO, DadosBancarios.class);
			    
				dadosBancarios = dadosBancariosService.criarDadosBancarios(alunoAntigo, dadosBancarios);
				
				dadosBancariosDTO = mapper.map(dadosBancarios, DadosBancariosDTO.class);
				
				return new ResponseEntity<>(dadosBancariosDTO, HttpStatus.CREATED);	
	        } else {
				throw new NotFoundException("Os dados não foram encontrados.");
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/{grrAlunoURL}/dadosBancarios")
	@ResponseBody
	public ResponseEntity<Object> listarDadosBancarios(@PathVariable String grrAlunoURL) {
	    try {
	    	if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Optional<Aluno> alunoOptional = alunoService.buscarAlunoGrr(grrAlunoURL);
		        Aluno aluno = alunoOptional.get();
		        
		        if (aluno == null)
					throw new NotFoundException("Aluno não encontrado!");
		        
		        DadosBancarios dados = new DadosBancarios();
				
				dados.setId(aluno.getDadosBancarios().getId());
				
				dados = dadosBancariosService.buscarDadosBancariosPorId(dados.getId());
				
				if (dados == null)
					throw new NotFoundException("Dados não encontrados!");
				
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(dados, DadosBancarios.class));
			}
	    	
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@PutMapping("/{grrAlunoURL}/dadosBancarios")
	@ResponseBody
	public ResponseEntity<Object> atualizarDadosBancarios(@PathVariable String grrAlunoURL, @RequestBody DadosBancariosDTO dadosDTO) {
	    try {
	        Optional<Aluno> aluno = alunoService.buscarAlunoGrr(grrAlunoURL);
	        Aluno alunoAntigo = aluno.get();
	        
	        if (aluno.isPresent()) {
	        	DadosBancarios dadosAtualizado = mapper.map(dadosDTO, DadosBancarios.class);
	        	
	        	dadosAtualizado.setId(alunoAntigo.getDadosBancarios().getId());
	        	dadosAtualizado.setAluno(alunoAntigo);
	        	
	        	System.out.println("Dados bancários do rapaz: " + alunoAntigo.getDadosBancarios().getId());
	        	
	        	dadosAtualizado = dadosBancariosService.atualizarDados(dadosAtualizado);
	        	DadosBancariosDTO dadosDTOAtualizado = mapper.map(dadosAtualizado, DadosBancariosDTO.class);
	        	
	        	return ResponseEntity.ok().body(dadosDTOAtualizado);
	        } else {
				throw new NotFoundException("Os dados não foram encontrados.");
			}
	        
	        
	    } catch (NumberFormatException e) {
	        throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
	    } catch (PocException e) {
	        e.printStackTrace();
	        throw e;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
	    }
	}

	@PutMapping("/{grrAlunoURL}/termo/{idTermo}/solicitarAprovacaoTermo")
	public ResponseEntity<TermoDeEstagioDTO> solicitarAprovacaoTermo(@PathVariable String grrAlunoURL,
			@PathVariable Long idTermo) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<TermoDeEstagio> termofind = Optional
							.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
					if (termofind == null || termofind.isEmpty()) {
						throw new NotFoundException("Termo não encontrado!");
					} else {
						TermoDeEstagio termo = alunoService.solicitarAprovacaoTermo(termofind);
						return ResponseEntity.status(HttpStatus.CREATED)
								.body(mapper.map(termo, TermoDeEstagioDTO.class));
					}
				}
			}
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	

	@GetMapping("/{grrAlunoURL}/estagio/emPreenchimento")
	public ResponseEntity<List<EstagioDTO>> buscarEstagioEmPreenchimento(@PathVariable String grrAlunoURL) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<Estagio> estagio = estagioService.buscarEstagioEmPreenchimentoPorAluno(aluno);
					List<EstagioDTO> listEstagioDTO = new ArrayList<EstagioDTO>();
					EstagioDTO estagioDTO = new EstagioDTO();
					if (estagio == null || estagio.isEmpty()) {
						estagioDTO = null;
					} else {
						for (Estagio e : estagio) {
							estagioDTO = estagioService.toEstagioDTO(e);
							listEstagioDTO.add(estagioDTO);
						}
					}
					return new ResponseEntity<>(listEstagioDTO, HttpStatus.OK);
				}
			}
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("/{grrAlunoURL}/estagio/emAprovacao")
	public ResponseEntity<List<EstagioDTO>> buscarEstagioEmAprovacao(@PathVariable String grrAlunoURL) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<Estagio> estagio = estagioService.buscarEstagioEmAprovacaoPorAluno(aluno);
					List<EstagioDTO> listEstagioDTO = new ArrayList<EstagioDTO>();
					EstagioDTO estagioDTO = new EstagioDTO();
					if (estagio == null || estagio.isEmpty()) {
						estagioDTO = null;
					} else {
						for (Estagio e : estagio) {
							estagioDTO = estagioService.toEstagioDTO(e);
							listEstagioDTO.add(estagioDTO);
						}
					}
					return new ResponseEntity<>(listEstagioDTO, HttpStatus.OK);
				}
			}
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	/** Neste caso, estágio em progresso é um estágio já aprovado, mas ainda não
	iniciado ou um estágio já aprovado e já iniciado ou seja, um estágio em
	andamento. **/
	@GetMapping("/{grrAlunoURL}/estagio/emProgresso")
	public ResponseEntity<List<EstagioDTO>> buscarEstagioEmProgresso(@PathVariable String grrAlunoURL) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<Estagio> estagio = estagioService.buscarEstagioEmProgressoPorAluno(aluno);
					List<EstagioDTO> listEstagioDTO = new ArrayList<EstagioDTO>();
					EstagioDTO estagioDTO = new EstagioDTO();
					if (estagio == null || estagio.isEmpty()) {
						estagioDTO = null;
					} else {
						for (Estagio e : estagio) {
							estagioDTO = estagioService.toEstagioDTO(e);
							listEstagioDTO.add(estagioDTO);
						}
					}
					return new ResponseEntity<>(listEstagioDTO, HttpStatus.OK);
				}
			}
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/{grrAlunoURL}/estagio")
	public ResponseEntity<List<EstagioDTO>> buscarEstagioPorStatus(@PathVariable String grrAlunoURL, @RequestParam String statusEstagio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<Estagio> estagio = estagioService.buscarEstagioPorStatusEstagio(aluno, statusEstagio);
					List<EstagioDTO> listEstagioDTO = new ArrayList<EstagioDTO>();
					EstagioDTO estagioDTO = new EstagioDTO();
					if (estagio == null || estagio.isEmpty()) {
						estagioDTO = null;
					} else {
						for (Estagio e : estagio) {
							estagioDTO = estagioService.toEstagioDTO(e);
							listEstagioDTO.add(estagioDTO);
						}
					}
					return new ResponseEntity<>(listEstagioDTO, HttpStatus.OK);
				}
			}
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/{grrAlunoURL}/gerar-termo")
	public ResponseEntity<byte[]> gerarTermoPdf(@PathVariable String grrAlunoURL) throws IOException {
		
		// TO-DO: Jogar dentro de um try-catch
		
		if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
			throw new BadRequestException("GRR do aluno não informado!");
		} else {
			Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
			if (aluno == null) {
				throw new NotFoundException("Aluno não encontrado!");
			} else {
				byte[] pdf = geradorService.gerarPdf(aluno);
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_PDF);
				headers.setContentDisposition(ContentDisposition.builder("inline").filename("arquivo.pdf").build());
		
				return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
			}
		}

	}
	
	@PostMapping("/{grrAlunoURL}/upload-termo")
	public ResponseEntity<String> uploadTermo(@PathVariable String grrAlunoURL, @RequestParam("file") MultipartFile file) {
	    
		// TO-DO: Jogar dentro de um try-catch
		
		if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
	        throw new BadRequestException("GRR do aluno não informado!");
	    } else {
	        Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
	        if (aluno == null) {
	            throw new NotFoundException("Aluno não encontrado!");
	        } else {
	            if (file.isEmpty()) {
	                throw new BadRequestException("Arquivo não informado!");
	            } else {
	                try {

	                	Path diretorioAtual = Paths.get("").toAbsolutePath();
	                	
	                	String diretorioDestino = diretorioAtual + "/src/main/resources/arquivos/";
	                	
	                	String nomeArquivo = grrAlunoURL + "-" + EnumTipoDocumento.TermoDeCompromisso;
	                    Path destino = Paths.get(diretorioDestino + nomeArquivo);
	                    
	                    Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

	                    return ResponseEntity.ok("Termo de compromisso salvo com sucesso!");
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
	                }
	            }
	        }
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
	
	@PostMapping("/{grrAlunoURL}/estagio/{idEstagio}/relatorioDeEstagio")
	public ResponseEntity<RelatorioDeEstagioDTO> criarRelatorioDeEstagio(@PathVariable String grrAlunoURL, @PathVariable long idEstagio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idEstagio);
					if (estagioFind.isEmpty()) {
						throw new NotFoundException("Estágio não encontrado!");
					}
					Estagio estagio = estagioFind.get();
					if (estagio.getAluno().getId() != aluno.getId()) {
						throw new NotFoundException("Estágio não pertence ao aluno!");
					} else {
						RelatorioDeEstagio relatorioEstagio = relatorioDeEstagioService.criarRelatorioDeEstagio(estagio);
						return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(relatorioEstagio, RelatorioDeEstagioDTO.class));
					}
				}
			}
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/{grrAlunoURL}/estagio/{idEstagio}/relatorioDeEstagio/{idRelatorio}/solicitarCiencia")
	public ResponseEntity<RelatorioDeEstagioDTO> solicitarCienciaRelatorioDeEstagio(@PathVariable String grrAlunoURL, @PathVariable long idEstagio, @PathVariable long idRelatorio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idEstagio);
					if (estagioFind.isEmpty()) {
						throw new NotFoundException("Estágio não encontrado!");
					}
					Optional<RelatorioDeEstagio> relatorioFind = relatorioDeEstagioService.buscarRelatorioPorId(idRelatorio);
					if (relatorioFind.isEmpty()) {
						throw new NotFoundException("Relatório não encontrado!");
					}
					Estagio estagio = estagioFind.get();
					if (estagio.getAluno().getId() != aluno.getId()) {
						throw new NotFoundException("Estágio não pertence ao aluno!");
					} else {
						RelatorioDeEstagio relatorioEstagio = relatorioFind.get();
						if (relatorioEstagio.getEstagio().getId() != estagio.getId()) {
							throw new NotFoundException("Relatório de estágio não pertence ao estágio!");
						} else {
							relatorioEstagio = relatorioDeEstagioService.solicitarCienciaRelatorioDeEstagioAluno(relatorioEstagio);
							return ResponseEntity.status(HttpStatus.OK).body(mapper.map(relatorioEstagio, RelatorioDeEstagioDTO.class));
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PostMapping("/{grrAlunoURL}/estagio/{idEstagio}/fichaDeAvaliacao")
	public ResponseEntity<FichaDeAvaliacaoDTO> criarFichaDeAvaliacao(@PathVariable String grrAlunoURL, @PathVariable long idEstagio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idEstagio);
					if (estagioFind.isEmpty()) {
						throw new NotFoundException("Estágio não encontrado!");
					}
					Estagio estagio = estagioFind.get();
					if (estagio.getAluno().getId() != aluno.getId()) {
						throw new NotFoundException("Estágio não pertence ao aluno!");
					} else {
						if (estagio.getFichaDeAvaliacao() != null) {
							throw new BadRequestException("Já existe uma ficha de avaliação criada pra esse estágio!");
						} else {
							FichaDeAvaliacao fichaDeAvaliacao = fichaDeAvaliacaoService.criarFichaDeAvaliacao(estagio);
							return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(fichaDeAvaliacao, FichaDeAvaliacaoDTO.class));
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PostMapping("/{grrAlunoURL}/estagio/{idEstagio}/certificadoDeEstagio")
	public ResponseEntity<CertificadoDeEstagioDTO> solicitarCertificadoDeEstagio(@PathVariable String grrAlunoURL, @PathVariable long idEstagio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idEstagio);
					if (estagioFind.isEmpty()) {
						throw new NotFoundException("Estágio não encontrado!");
					}
					Estagio estagio = estagioFind.get();
					if (estagio.getAluno().getId() != aluno.getId()) {
						throw new NotFoundException("Estágio não pertence ao aluno!");
					} else {
						if (estagio.getFichaDeAvaliacao() == null) {
							throw new BadRequestException("Não existe uma ficha de avaliação criada pra esse estágio!");
						} else {
							if (estagio.getCertificadoDeEstagio() != null) {
								throw new BadRequestException("Já existe um certificado criada pra esse estágio!");
							} else {
								CertificadoDeEstagio certificadoDeEstagio = certificadoDeEstagioService.criarCertificadoDeEstagio(estagio);
								return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(certificadoDeEstagio, CertificadoDeEstagioDTO.class));
							}
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/{grrAlunoURL}/certificadoDeEstagio")
	public ResponseEntity<List<CertificadoDeEstagioDTO>> listarCertificadosDeEstagio(@PathVariable String grrAlunoURL) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<Estagio> listaEstagios = aluno.getEstagio();
					if (listaEstagios == null || listaEstagios.isEmpty()) {
						throw new NotFoundException("O aluno não possui nenhum estágio!");
					} else {
						List<CertificadoDeEstagio> listaCertificados = alunoService.listarCertificadosDeEstagioAluno(aluno);
						return ResponseEntity.status(HttpStatus.OK).body(listaCertificados.stream().map(e -> mapper.map(e, CertificadoDeEstagioDTO.class)).collect(Collectors.toList()));
					}
				}
			}
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PostMapping("/{grrAlunoURL}/estagio/{idEstagio}/termoAditivo")
	public ResponseEntity<TermoDeEstagioDTO> novoTermoAditivo(@PathVariable String grrAlunoURL, @PathVariable long idEstagio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idEstagio);
					if (estagioFind.isEmpty()) {
						throw new NotFoundException("Estágio não encontrado!");
					}
					Estagio estagio = estagioFind.get();
					if (estagio.getAluno().getId() != aluno.getId()) {
						throw new NotFoundException("Estágio não pertence ao aluno!");
					} else {
						TermoDeEstagio termoAditivo = termoDeEstagioService.novoTermoAditivo(estagio);
						return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(termoAditivo, TermoDeEstagioDTO.class));
					}
				}
			}
		} catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
}