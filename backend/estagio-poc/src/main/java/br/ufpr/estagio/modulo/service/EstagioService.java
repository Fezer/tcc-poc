package br.ufpr.estagio.modulo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.repository.EstagioRepository;
import br.ufpr.estagio.modulo.repository.TermoDeEstagioRepository;
 
@Service
@Transactional
public class EstagioService {

	@Autowired
	private EstagioRepository estagioRepo;
	
	@Autowired
	private TermoDeEstagioRepository termoDeEstagioRepo;
	
    public EstagioService(EstagioRepository estagioRepo) {
        this.estagioRepo = estagioRepo;
    }
     
    public List<Estagio> listAllEstagios() {
        return estagioRepo.findAll();
    }
     
    public Estagio novoEstagio(Estagio estagio) {
    	EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmPreenchimento;
    	estagio.setStatusEstagio(statusEstagio);
        return estagioRepo.save(estagio);
    }
    
    public Estagio getEstagio(long id) {
        return estagioRepo.findById(id).get();
    }
     
    public Estagio saveEstagio(Estagio estagio) {
        return estagioRepo.save(estagio);
    }
     
    public Estagio updateEstagio(Estagio estagio) {
    	return estagioRepo.save(estagio);
    }
     
    public void deleteEstagio(long id) {
    	estagioRepo.deleteById(id);
    }

	public EstagioDTO toEstagioDTO(Estagio estagio) {
		EstagioDTO estagioDTO = new EstagioDTO();
		estagioDTO.setId(estagio.getId());
		estagioDTO.setTipoEstagio(estagio.getTipoEstagio());
		estagioDTO.setStatusEstagio(estagio.getStatusEstagio());
		estagioDTO.setEstagioUfpr(estagio.isEstagioUfpr());
		estagioDTO.setAluno(estagio.getAluno());
		estagioDTO.setContratante(estagio.getContratante());
		estagioDTO.setSeguradora(estagio.getSeguradora());
		estagioDTO.setApolice(estagio.getApolice());
		estagioDTO.setAgenteIntegrador(estagio.getAgenteIntegrador());
		estagioDTO.setOrientador(estagio.getOrientador());
		estagioDTO.setSupervisor(estagio.getSupervisor());
		estagioDTO.setPlanoDeAtividades(estagio.getPlanoDeAtividades());
		estagioDTO.setDataInicio(estagio.getDataInicio());
		estagioDTO.setDataTermino(estagio.getDataTermino());
		estagioDTO.setJornadaDiaria(estagio.getJornadaDiaria());
		estagioDTO.setJornadaSemanal(estagio.getJornadaSemanal());
		estagioDTO.setValorBolsa(estagio.getValorBolsa());
		estagioDTO.setValorTransporte(estagio.getValorTransporte());
		estagioDTO.setTermoDeCompromisso(estagio.getTermoDeCompromisso() == null ? 0 : estagio.getTermoDeCompromisso().getId());
		List<Long> termoAditivo = new ArrayList<Long>();
		if(estagio.getTermoAdivito() != null) {
			for (TermoDeEstagio t : estagio.getTermoAdivito()) {
				termoAditivo.add(t.getId());
			}
		} else {
			termoAditivo.add((long) 0);
		}
		estagioDTO.setTermoAdivito(termoAditivo);
		estagioDTO.setTermoDeRescisao(estagio.getTermoDeRescisao() == null ? 0 : estagio.getTermoDeRescisao().getId());
		List<Long> relatorioDeEstagio = new ArrayList<Long>();
		if(estagio.getRelatorioDeEstagio() != null) {
			for (RelatorioDeEstagio r : estagio.getRelatorioDeEstagio()) {
				relatorioDeEstagio.add(r.getId());
			}
		} else {
			termoAditivo.add((long) 0);
		}
		estagioDTO.setRelatorioDeEstagio(relatorioDeEstagio);
		estagioDTO.setFichaDeAvaliacao(estagio.getFichaDeAvaliacao() == null ? 0 : estagio.getFichaDeAvaliacao().getId());
		estagioDTO.setCertificadoDeEstagio(estagio.getCertificadoDeEstagio() == null ? 0 : estagio.getCertificadoDeEstagio().getId());
		estagioDTO.setDataCriacao(estagio.getDataCriacao());
		return estagioDTO;
	}

	public Estagio definirTipoEstagio(Estagio estagio, EnumTipoEstagio tipoEstagio) {
		estagio.setTipoEstagio(tipoEstagio);
		return estagioRepo.save(estagio);		
	}

	public Estagio definirEstagioUfpr(Estagio estagio, Boolean estagioUfpr) {
		estagio.setEstagioUfpr(estagioUfpr);
		return estagioRepo.save(estagio);
	}
}
