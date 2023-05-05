package br.ufpr.estagio.modulo.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.lowagie.text.DocumentException;

import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.exception.BadRequestException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.model.TermoPoc;
import br.ufpr.estagio.modulo.repository.TermoPocRepository;
import br.ufpr.estagio.modulo.service.AlunoService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.GeradorDePdfService;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;
import br.ufpr.estagio.modulo.service.siga.SigaApiAlunoService;
import br.ufpr.estagio.modulo.wrapper.DiscenteWrapper;

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
	private SigaApiAlunoService sigaApiAlunoService;
	
	@Autowired
	private AlunoService alunoService;

	@Autowired
	private GeradorDePdfService geradorService;
	
	@Autowired
	private ModelMapper mapper;

	// Isso é um listarAluno
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
	
	@GetMapping("/gerar-termo")
	public ResponseEntity<byte[]> gerarPdf() throws IOException, DocumentException {
		
		// TO-DO: Jogar dentro de um try-catch
		
	    ClassLoader classLoader = getClass().getClassLoader();
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    PdfWriter writer = new PdfWriter(outputStream);
	    PdfDocument pdf = new PdfDocument(writer);
	    
	    // Linhas abaixo provisórias para fazer o uso do Flying Saucer, a fim de utilizar certas unidades de medida
	    geradorService.generatePdfFromClasspath("TermoCompromisso-Obrigatorio-Ufpr-EstudanteUfpr.html", "OUTPUT.pdf");
	    
	    /* // Utilizar as linhas abaixo para fazer o uso do Itext. Foram comentadas por causa da incompatibilidade
	     * // algumas unidades de medida.
	    ConverterProperties props = new ConverterProperties();
	    HtmlConverter.convertToPdf(new FileInputStream(new File(classLoader.getResource("TermoCompromisso-Obrigatorio-Ufpr-EstudanteUfpr.html").getFile())), pdf, props);
	    */
	    
	    pdf.close();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    headers.setContentDisposition(ContentDisposition.builder("inline").filename("arquivo.pdf").build());
	    return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
	}
	
	//endpoint de dados auxiliares. so get

}