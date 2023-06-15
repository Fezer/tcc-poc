package br.ufpr.estagio.modulo.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.dto.PeriodoRecessoDTO;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.PeriodoRecesso;
import br.ufpr.estagio.modulo.service.PeriodoRecessoService;

@CrossOrigin
@RestController
@RequestMapping("/periodoRecesso")
public class PeriodoRecessoREST {
	
	@Autowired
    private PeriodoRecessoService periodoRecessoService;
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("")
	public ResponseEntity<PeriodoRecessoDTO> inserir(@RequestBody PeriodoRecessoDTO periodoRecesso){
		try {
		PeriodoRecesso newPeriodoRecesso = periodoRecessoService.novo(mapper.map(periodoRecesso, PeriodoRecesso.class));
		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(newPeriodoRecesso, PeriodoRecessoDTO.class));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
	    } catch(Exception e) {
			e.printStackTrace();
			throw new NotFoundException("Não foi possível criar um novo periodo de recesso!.");
		}
	}
	
	@GetMapping("")
	public ResponseEntity<?> listarTodos(){
		try {
			List<PeriodoRecesso> lista = periodoRecessoService.listarTodos();
			if(lista.isEmpty()) {
				throw new NotFoundException("Nenhum período de recesso encontrado!");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(lista.stream().map(e -> mapper.map(e, PeriodoRecessoDTO.class)).collect(Collectors.toList()));
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> listarPeriodoRecesso(@PathVariable String id){
		try {
			long idLong = Long.parseLong(id);
	    	
		    if (idLong < 1) {
		   		throw new InvalidFieldException("Id do período de recesso inválido!");
		   	}
		    
			Optional<PeriodoRecesso> periodoRecessoOptional = periodoRecessoService.buscarPorId(idLong);
		if(periodoRecessoOptional.isEmpty()) {
			throw new NotFoundException("Período de recesso não encontrado!");
		} else {
			PeriodoRecesso periodoRecesso = periodoRecessoOptional.get();
			PeriodoRecessoDTO periodoRecessoDTO = mapper.map(periodoRecesso, PeriodoRecessoDTO.class);
			return new ResponseEntity<>(periodoRecessoDTO, HttpStatus.OK);
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
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody PeriodoRecessoDTO periodoRecesso){
		try {
			long idLong = Long.parseLong(id);
	    	
		    if (idLong < 1) {
		   		throw new InvalidFieldException("Id do período de recesso inválido!");
		   	}
		    
			Optional<PeriodoRecesso> periodoRecessoOptional = periodoRecessoService.buscarPorId(idLong);
			if(periodoRecessoOptional.isEmpty()) {
				throw new NotFoundException("Periodo de recesso não encontrado!");
			} else {
				
				if (periodoRecesso.getDataFim().before(periodoRecesso.getDataInicio())) {
					throw new InvalidFieldException("Data de término não pode ser igual ou anterior à data de início!");
				}
				
				long diferencaMilissegundos = periodoRecesso.getDataFim().getTime() - periodoRecesso.getDataInicio().getTime();
				long diferencaDias = TimeUnit.MILLISECONDS.toDays(diferencaMilissegundos);
				
				if (diferencaDias > 30) {
				    throw new InvalidFieldException("O período de recesso deve ser de 30 dias!");
				}
				
				PeriodoRecesso periodoRecessoAtualizado = periodoRecessoService.atualizarDados(periodoRecessoOptional.get(), periodoRecesso);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(periodoRecessoAtualizado, PeriodoRecessoDTO.class));
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
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable String id){
		try {
			long idLong = Long.parseLong(id);
	    	
		    if (idLong < 1) {
		   		throw new InvalidFieldException("Id do período de recesso inválido!");
		   	}
		    
			Optional<PeriodoRecesso> periodoRecessoOptional = periodoRecessoService.buscarPorId(idLong);
			if(periodoRecessoOptional.isEmpty()) {
				throw new NotFoundException("Período de recesso não encontrado!");
			} else {
				periodoRecessoService.deletar(idLong);
				return ResponseEntity.status(HttpStatus.OK).body(null);
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
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
}
