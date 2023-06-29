package br.ufpr.estagio.modulo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;

@Service
public class GeradorDeExcelService {
	
	// Ok
	public ByteArrayOutputStream gerarExcelEstagioSeguradoraUfpr(List<Estagio> estagios) throws IOException {
		
	    Workbook workbook = new XSSFWorkbook();

	    Sheet sheet = workbook.createSheet("Relatório Estágios");

	    String[] headersTitle = {"Id do Estágio", "Nome Aluno", "GRR", "IRA", "Curso", "Status do Estágio", "Contratante", 
	    		"Seguradora", "Apólice", "Orientador", "Agente Integrador", "Supervisor", "Data de Início", "Data de Término",
	    		"Jornada Diária", "Jornada Semanal", "Valor da Bolsa", "Valor de Transporte", "Rua do Estágio", "Número",
	    		"Cidade", "Estado", "CEP"};
	    Row headerRow = sheet.createRow(0);
	    for (int i = 0; i < headersTitle.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(headersTitle[i]);
	    }

	    int rowNum = 1;
	    for (Estagio estagio : estagios) {
	        Row row = sheet.createRow(rowNum++);
	        String dataInicio = new SimpleDateFormat("dd/MM/yyyy").format(estagio.getDataInicio());
	        String dataTermino = new SimpleDateFormat("dd/MM/yyyy").format(estagio.getDataTermino());
	        
	        row.createCell(0).setCellValue(estagio.getId());
	        row.createCell(1).setCellValue(estagio.getAluno().getNome());
	        row.createCell(2).setCellValue(estagio.getAluno().getMatricula());
	        row.createCell(3).setCellValue(estagio.getAluno().getIra());
	        row.createCell(4).setCellValue(estagio.getAluno().getCurso().getNome());
	        row.createCell(5).setCellValue(String.valueOf(estagio.getStatusEstagio()));
	        row.createCell(6).setCellValue(estagio.getContratante().getNome());
	        row.createCell(7).setCellValue(estagio.getSeguradora().getNome());
	        row.createCell(8).setCellValue(estagio.getApolice().getNumero());
	        row.createCell(9).setCellValue(estagio.getOrientador().getNome());
	        row.createCell(10).setCellValue(estagio.getAgenteIntegrador().getNome());
	        row.createCell(11).setCellValue(estagio.getPlanoDeAtividades().getNomeSupervisor());
	        row.createCell(12).setCellValue(dataInicio);
	        row.createCell(13).setCellValue(dataTermino);
	        row.createCell(14).setCellValue(estagio.getJornadaDiaria());
	        row.createCell(15).setCellValue(estagio.getJornadaSemanal());
	        row.createCell(16).setCellValue(estagio.getValorBolsa());
	        row.createCell(17).setCellValue(estagio.getValorTransporte());
	        row.createCell(18).setCellValue(estagio.getContratante().getEndereco().getRua());
	        row.createCell(18).setCellValue(estagio.getContratante().getEndereco().getRua());
	        row.createCell(19).setCellValue(estagio.getContratante().getEndereco().getNumero());
	        row.createCell(20).setCellValue(estagio.getContratante().getEndereco().getCidade());
	        row.createCell(21).setCellValue(estagio.getContratante().getEndereco().getUf());
	        row.createCell(22).setCellValue(estagio.getContratante().getEndereco().getCep());

	        break;
	    }

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    workbook.write(outputStream);
	    
	    return outputStream;
	}
	
	public ByteArrayOutputStream gerarExcelContratante(Contratante contratante) throws IOException {
		
	    Workbook workbook = new XSSFWorkbook();

	    Sheet sheet = workbook.createSheet("Relatório Contratante");
	    
	    String[] headersTitle = {"Nome", "CNPJ", "CPF", "Representante", "Telefone", "Rua", "Número", "Cidade", 
	    		"Estado", "CEP", "Quantidade de Estágios"};
	    
	    Row headerRow = sheet.createRow(0);
	    for (int i = 0; i < headersTitle.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(headersTitle[i]);
	    }

	    int rowNum = 1;
	    
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(contratante.getNome());
        row.createCell(1).setCellValue(contratante.getCnpj());
        row.createCell(2).setCellValue(contratante.getCpf());
        row.createCell(3).setCellValue(contratante.getRepresentanteEmpresa());
        row.createCell(4).setCellValue(contratante.getTelefone());
        row.createCell(5).setCellValue(contratante.getEndereco().getRua());
        row.createCell(6).setCellValue(contratante.getEndereco().getNumero());
        row.createCell(7).setCellValue(contratante.getEndereco().getCidade());
        row.createCell(8).setCellValue(contratante.getEndereco().getUf());
        row.createCell(9).setCellValue(contratante.getEndereco().getCep());
        row.createCell(10).setCellValue(contratante.getEstagio().size());

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    workbook.write(outputStream);
	    
	    return outputStream;
	}
	
	public ByteArrayOutputStream gerarExcelAgenteIntegrador(AgenteIntegrador agenteIntegrador) throws IOException {
		
	    Workbook workbook = new XSSFWorkbook();

	    Sheet sheet = workbook.createSheet("Relatório Agente Integrador");

	    String[] headersTitle = {"CNPJ", "Nome", "Telefone", "qtdConvenios", "qtdEstagios", "ativo"};
	    Row headerRow = sheet.createRow(0);
	    for (int i = 0; i < headersTitle.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(headersTitle[i]);
	    }

	    int rowNum = 1;
	    
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(agenteIntegrador.getCnpj());
        row.createCell(1).setCellValue(agenteIntegrador.getNome());
        row.createCell(2).setCellValue(agenteIntegrador.getTelefone());
        row.createCell(3).setCellValue(agenteIntegrador.getConvenio().size());
        row.createCell(4).setCellValue(agenteIntegrador.getEstagio().size());
        row.createCell(5).setCellValue(String.valueOf(agenteIntegrador.isAtivo()));

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    workbook.write(outputStream);
	    
	    return outputStream;
	}

	public ByteArrayOutputStream gerarExcelCertificadosDeEstagio(List<CertificadoDeEstagio> certificados) throws IOException {
		
	    Workbook workbook = new XSSFWorkbook();

	    Sheet sheet = workbook.createSheet("Certificados de Estágio");

	    String[] headersTitle = {"Id do Estágio", "Nome Aluno", "GRR", "Curso", "Etapa do Fluxo", "Motivo da Reprovação", 
	    		"Parecer COE", "Seguradora", "Apólice", "Orientador", "Agente Integrador", "Supervisor", "Data de Início", 
	    		"Data de Término", "Jornada Diária", "Jornada Semanal", "Valor da Bolsa", "Valor de Transporte", 
	    		"Rua do Estágio", "Número", "Cidade", "Estado", "CEP"};
	    Row headerRow = sheet.createRow(0);
	    for (int i = 0; i < headersTitle.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(headersTitle[i]);
	    }

	    int rowNum = 1;
	    for (CertificadoDeEstagio certificado : certificados) {
	        Row row = sheet.createRow(rowNum++);
	        row.createCell(0).setCellValue(certificado.getEstagio().getId());
	        row.createCell(1).setCellValue(certificado.getEstagio().getAluno().getNome());
	        row.createCell(2).setCellValue(certificado.getEstagio().getAluno().getMatricula());
	        row.createCell(3).setCellValue(certificado.getEstagio().getAluno().getCurso().getNome());
	        row.createCell(4).setCellValue(String.valueOf(certificado.getEtapaFluxo()));
	        if (certificado.getMotivoReprovacao() == null)
	        	row.createCell(5).setCellValue("Não houve reprovação.");
	        else
	        	row.createCell(5).setCellValue(String.valueOf(certificado.getMotivoReprovacao()));
	        row.createCell(6).setCellValue(String.valueOf(certificado.getParecerCOE()));
	        row.createCell(7).setCellValue(certificado.getEstagio().getSeguradora().getNome());
	        row.createCell(8).setCellValue(certificado.getEstagio().getApolice().getNumero());
	        row.createCell(9).setCellValue(certificado.getEstagio().getOrientador().getNome());
	        row.createCell(10).setCellValue(certificado.getEstagio().getAgenteIntegrador().getNome());
	        row.createCell(11).setCellValue(certificado.getEstagio().getPlanoDeAtividades().getNomeSupervisor());
	        row.createCell(12).setCellValue(certificado.getEstagio().getDataInicio());
	        row.createCell(13).setCellValue(certificado.getEstagio().getDataTermino());
	        row.createCell(14).setCellValue(certificado.getEstagio().getJornadaDiaria());
	        row.createCell(15).setCellValue(certificado.getEstagio().getJornadaSemanal());
	        /*row.createCell(16).setCellValue(certificado.getEstagio().getContratante().getEndereco().getRua());
	        row.createCell(17).setCellValue(certificado.getEstagio().getContratante().getEndereco().getNumero());
	        row.createCell(18).setCellValue(certificado.getEstagio().getContratante().getEndereco().getCidade());
	        row.createCell(19).setCellValue(certificado.getEstagio().getContratante().getEndereco().getUf());
	        row.createCell(20).setCellValue(certificado.getEstagio().getContratante().getEndereco().getCep());*/
	        row.createCell(16).setCellValue("Rua A");
	        row.createCell(17).setCellValue(42);
	        row.createCell(18).setCellValue("Curitiba");
	        row.createCell(19).setCellValue("Paraná");
	        row.createCell(20).setCellValue("74329-214");
	        break;
	    }

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    workbook.write(outputStream);
	    
	    return outputStream;
	}
	
	public ByteArrayOutputStream gerarExcelRelatoriosDeEstagio(List<RelatorioDeEstagio> relatorios) throws IOException {
		
	    Workbook workbook = new XSSFWorkbook();

	    Sheet sheet = workbook.createSheet("Certificados de Estágio");

	    String[] headersTitle = {"Id do Estágio", "Nome Aluno", "GRR", "Curso", "Desempenho nas Atividades",
	    		"Contribuição no Estágio", "Desenvolvimento de Atividades", "Efetivação", "Formação Profissional",
	    		"Relações Interpessoais", "Considerações", "Seguradora", "Apólice", "Orientador", "Agente Integrador", 
	    		"Supervisor", "Data de Início", "Data de Término", "Rua do Estágio", "Número", "Cidade", "Estado", "CEP"};
	    Row headerRow = sheet.createRow(0);
	    for (int i = 0; i < headersTitle.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(headersTitle[i]);
	    }

	    int rowNum = 1;
	    for (RelatorioDeEstagio relatorio : relatorios) {
	        Row row = sheet.createRow(rowNum++);
	        row.createCell(0).setCellValue(relatorio.getEstagio().getId());
	        row.createCell(1).setCellValue(relatorio.getEstagio().getAluno().getNome());
	        row.createCell(2).setCellValue(relatorio.getEstagio().getAluno().getMatricula());
	        row.createCell(3).setCellValue(relatorio.getEstagio().getAluno().getCurso().getNome());
	        row.createCell(4).setCellValue(String.valueOf(relatorio.getAvalAtividades()));
	        row.createCell(5).setCellValue(String.valueOf(relatorio.getAvalContribuicaoEstagio()));
	        row.createCell(6).setCellValue(String.valueOf(relatorio.getAvalDesenvolvimentoAtividades()));
	        row.createCell(7).setCellValue(String.valueOf(relatorio.getAvalEfetivacao()));
	        row.createCell(8).setCellValue(String.valueOf(relatorio.getAvalFormacaoProfissional()));
	        row.createCell(9).setCellValue(String.valueOf(relatorio.getAvalRelacoesInterpessoais()));
	        row.createCell(10).setCellValue(relatorio.getConsideracoes());
	        row.createCell(11).setCellValue(relatorio.getEstagio().getSeguradora().getNome());
	        row.createCell(12).setCellValue(relatorio.getEstagio().getApolice().getNumero());
	        row.createCell(13).setCellValue(relatorio.getEstagio().getOrientador().getNome());
	        row.createCell(14).setCellValue(relatorio.getEstagio().getAgenteIntegrador().getNome());
	        row.createCell(15).setCellValue(relatorio.getEstagio().getPlanoDeAtividades().getNomeSupervisor());
	        row.createCell(16).setCellValue(relatorio.getEstagio().getDataInicio());
	        row.createCell(17).setCellValue(relatorio.getEstagio().getDataTermino());
	        /*row.createCell(18).setCellValue(relatorio.getEstagio().getContratante().getEndereco().getRua());
	        row.createCell(19).setCellValue(relatorio.getEstagio().getContratante().getEndereco().getNumero());
	        row.createCell(20).setCellValue(relatorio.getEstagio().getContratante().getEndereco().getCidade());
	        row.createCell(21).setCellValue(relatorio.getEstagio().getContratante().getEndereco().getUf());
	        row.createCell(22).setCellValue(relatorio.getEstagio().getContratante().getEndereco().getCep());*/
	        row.createCell(18).setCellValue("Rua A");
	        row.createCell(19).setCellValue(42);
	        row.createCell(20).setCellValue("Curitiba");
	        row.createCell(21).setCellValue("Paraná");
	        row.createCell(22).setCellValue("74329-214");
	    }

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    workbook.write(outputStream);
	    
	    return outputStream;
	}

	public ByteArrayOutputStream gerarExcelRelatorioDeEstagio(RelatorioDeEstagio relatorio) throws IOException {
		
	    Workbook workbook = new XSSFWorkbook();

	    Sheet sheet = workbook.createSheet("Certificados de Estágio");

	    String[] headersTitle = {"Id do Estágio", "Nome Aluno", "GRR", "Curso", "Desempenho nas Atividades",
	    		"Contribuição no Estágio", "Desenvolvimento de Atividades", "Efetivação", "Formação Profissional",
	    		"Relações Interpessoais", "Considerações", "Seguradora", "Apólice", "Orientador", "Agente Integrador", 
	    		"Supervisor", "Data de Início", "Data de Término", "Rua do Estágio", "Número", "Cidade", "Estado", "CEP"};
	    Row headerRow = sheet.createRow(0);
	    for (int i = 0; i < headersTitle.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(headersTitle[i]);
	    }

	    int rowNum = 1;
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(relatorio.getEstagio().getId());
        row.createCell(1).setCellValue(relatorio.getEstagio().getAluno().getNome());
        row.createCell(2).setCellValue(relatorio.getEstagio().getAluno().getMatricula());
        row.createCell(3).setCellValue(relatorio.getEstagio().getAluno().getCurso().getNome());
        row.createCell(4).setCellValue(String.valueOf(relatorio.getAvalAtividades()));
        row.createCell(5).setCellValue(String.valueOf(relatorio.getAvalContribuicaoEstagio()));
        row.createCell(6).setCellValue(String.valueOf(relatorio.getAvalDesenvolvimentoAtividades()));
        row.createCell(7).setCellValue(String.valueOf(relatorio.getAvalEfetivacao()));
        row.createCell(8).setCellValue(String.valueOf(relatorio.getAvalFormacaoProfissional()));
        row.createCell(9).setCellValue(String.valueOf(relatorio.getAvalRelacoesInterpessoais()));
        row.createCell(10).setCellValue(relatorio.getConsideracoes());
        row.createCell(11).setCellValue(relatorio.getEstagio().getSeguradora().getNome());
        row.createCell(12).setCellValue(relatorio.getEstagio().getApolice().getNumero());
        row.createCell(13).setCellValue(relatorio.getEstagio().getOrientador().getNome());
        row.createCell(14).setCellValue(relatorio.getEstagio().getAgenteIntegrador().getNome());
        row.createCell(15).setCellValue(relatorio.getEstagio().getPlanoDeAtividades().getNomeSupervisor());
        row.createCell(16).setCellValue(relatorio.getEstagio().getDataInicio());
        row.createCell(17).setCellValue(relatorio.getEstagio().getDataTermino());
        /*row.createCell(18).setCellValue(relatorio.getEstagio().getContratante().getEndereco().getRua());
        row.createCell(19).setCellValue(relatorio.getEstagio().getContratante().getEndereco().getNumero());
        row.createCell(20).setCellValue(relatorio.getEstagio().getContratante().getEndereco().getCidade());
        row.createCell(21).setCellValue(relatorio.getEstagio().getContratante().getEndereco().getUf());
        row.createCell(22).setCellValue(relatorio.getEstagio().getContratante().getEndereco().getCep());*/
        row.createCell(18).setCellValue("Rua A");
        row.createCell(19).setCellValue(42);
        row.createCell(20).setCellValue("Curitiba");
        row.createCell(21).setCellValue("Paraná");
        row.createCell(22).setCellValue("74329-214");

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    workbook.write(outputStream);
	    
	    return outputStream;
	}
}
