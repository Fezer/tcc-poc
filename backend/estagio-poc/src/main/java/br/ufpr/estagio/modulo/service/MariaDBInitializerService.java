package br.ufpr.estagio.modulo.service;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MariaDBInitializerService {
	
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createProcedureUpdateStatusEstagioIniciado() {
        Query query = entityManager.createNativeQuery(
            "CREATE PROCEDURE IF NOT EXISTS update_estagio_status_para_iniciado() " +
            "BEGIN " +
            "  UPDATE estagio " +
            "  SET status_estagio = 3 " +
            "  WHERE data_inicio <= NOW() " +
            "AND data_termino >= NOW() " +
            "AND status_estagio = 2; " +
            "END"
        );
        query.executeUpdate();
    }

    @Transactional
    public void createProcedureUpdateStatusConcluido() {
        Query query = entityManager.createNativeQuery(
            "CREATE PROCEDURE IF NOT EXISTS update_estagio_status_para_concluido() " +
            "BEGIN " +
            "  UPDATE estagio " +
            "  SET status_estagio = 4 " +
            "  WHERE data_termino <= NOW() " +
            "AND status_estagio = 3; " +
            "END"
        );
        query.executeUpdate();
    }
    
    @Transactional
    public void createEventUpdateStatusEstagio() {
        Query query = entityManager.createNativeQuery(
        	"CREATE EVENT IF NOT EXISTS update_status_estagio_event " +
        	"ON SCHEDULE EVERY 5 MINUTE " +
        	"DO " +
        	"BEGIN " +
        	"  CALL update_estagio_status_para_iniciado(); " +
        	"  CALL update_estagio_status_para_concluido(); " +
        	"END"
        );
        query.executeUpdate();
    }
    
}
