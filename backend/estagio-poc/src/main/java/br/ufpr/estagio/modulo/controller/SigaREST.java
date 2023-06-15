package br.ufpr.estagio.modulo.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.exception.BadRequestException;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.CursoSiga;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.model.DocentesData;
import br.ufpr.estagio.modulo.service.siga.SigaApiAlunoService;
import br.ufpr.estagio.modulo.service.siga.SigaApiCursoSigaService;
import br.ufpr.estagio.modulo.service.siga.SigaApiDiscentesService;

@CrossOrigin
@RestController
@RequestMapping("/siga")
public class SigaREST {
	
	@Autowired
    private SigaApiAlunoService sigaApiAlunoService;
	
	@Autowired
    private SigaApiDiscentesService sigaApiDiscentesService;
	
	@Autowired
    private SigaApiCursoSigaService sigaApiCursoSigaService;
    	
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/aluno")
	public ResponseEntity<Object> listarAluno(@RequestParam String grr, @RequestHeader("Authorization") String accessToken){
		try {
			if(grr.isBlank() || grr.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Discente discente = sigaApiAlunoService.buscarAlunoPorGrr(grr, accessToken);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(discente, Discente.class));
			}
		} catch (NumberFormatException ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Id do aluno deve ser um inteiro!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (RuntimeException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
		
	@GetMapping("/docentes")
	public ResponseEntity<Object> listarDocentes(@RequestParam String idPrograma, @RequestHeader("Authorization") String accessToken){
		try {
			if(idPrograma.isBlank() || idPrograma.isEmpty()) {
				throw new BadRequestException("ID do programa não informado!");
			} else {
				DocentesData docentesData = sigaApiDiscentesService.buscarDiscentesPorIdPrograma(idPrograma, accessToken);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(docentesData, DocentesData.class));
			}
		} catch (NumberFormatException ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Id do programa deve ser um inteiro!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (RuntimeException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@GetMapping("/cursos")
	public ResponseEntity<Object> listarCursos(@RequestParam Long idCurso, @RequestHeader("Authorization") String accessToken){
		try {
			if(idCurso < 1) {
				throw new BadRequestException("ID do curso inválido!");
			} else {
				CursoSiga cursoSiga = sigaApiCursoSigaService.buscarCursoSigaPorIdCurso(idCurso, accessToken);
				if(cursoSiga == null) {
					throw new NotFoundException("Curso não encontrado!");
				}
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(cursoSiga, CursoSiga.class));
			}
		} catch (NumberFormatException ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Id do curso deve ser um inteiro!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (RuntimeException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/grr")
	public ResponseEntity<String> buscarGrr(@RequestHeader("Authorization") String accessToken){
		try {
			if(accessToken==null) {
				throw new BadRequestException("Não autorizado.");
			} else {
				String grr = sigaApiAlunoService.buscarGrrPorEmail(accessToken);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(grr, String.class));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
}