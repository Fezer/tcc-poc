package br.ufpr.estagio.modulo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Estagio;

@Service
public class GeradorDeExcelService {
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
	        row.createCell(12).setCellValue(estagio.getDataInicio());
	        row.createCell(13).setCellValue(estagio.getDataTermino());
	        row.createCell(14).setCellValue(estagio.getJornadaDiaria());
	        row.createCell(15).setCellValue(estagio.getJornadaSemanal());
	        row.createCell(16).setCellValue(estagio.getValorBolsa());
	        row.createCell(17).setCellValue(estagio.getValorTransporte());
	        /*row.createCell(18).setCellValue(estagio.getContratante().getEndereco().getRua());
	        row.createCell(19).setCellValue(estagio.getContratante().getEndereco().getNumero());
	        row.createCell(20).setCellValue(estagio.getContratante().getEndereco().getCidade());
	        row.createCell(21).setCellValue(estagio.getContratante().getEndereco().getUf());
	        row.createCell(22).setCellValue(estagio.getContratante().getEndereco().getCep());*/
	        row.createCell(18).setCellValue("Rua A");
	        row.createCell(19).setCellValue(42);
	        row.createCell(20).setCellValue("Curitiba");
	        row.createCell(21).setCellValue("Paraná");
	        row.createCell(22).setCellValue("74329-214");
	        break;
	    }

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    workbook.write(outputStream);
	    
	    return outputStream;
	}
	
	public ByteArrayOutputStream gerarExcelContratante(Contratante contratante) throws IOException {
		
	    Workbook workbook = new XSSFWorkbook();

	    Sheet sheet = workbook.createSheet("Relatório Contratante");

	    String[] headersTitle = {"CPF", "Nome", "Representante", "Telefone", "Rua", "Número", "Cidade", 
	    		"Estado", "CEP"};
	    Row headerRow = sheet.createRow(0);
	    for (int i = 0; i < headersTitle.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(headersTitle[i]);
	    }

	    int rowNum = 1;
	    
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(contratante.getCpf());
        row.createCell(1).setCellValue(contratante.getNome());
        row.createCell(2).setCellValue(contratante.getRepresentanteEmpresa());
        row.createCell(3).setCellValue(contratante.getTelefone());
        /*row.createCell(4).setCellValue(contratante.getEndereco().getRua());
        row.createCell(5).setCellValue(contratante.getEndereco().getNumero());
        row.createCell(6).setCellValue(contratante.getEndereco().getCidade());
        row.createCell(7).setCellValue(contratante.getEndereco().getUf());
        row.createCell(8).setCellValue(contratante.getEndereco().getCep());*/
        row.createCell(4).setCellValue("Rua A");
        row.createCell(5).setCellValue(42);
        row.createCell(6).setCellValue("Curitiba");
        row.createCell(7).setCellValue("Paraná");
        row.createCell(8).setCellValue("74329-214");

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    workbook.write(outputStream);
	    
	    return outputStream;
	}
	
public ByteArrayOutputStream gerarExcelAgenteIntegrador(AgenteIntegrador agenteIntegrador) throws IOException {
		
	    Workbook workbook = new XSSFWorkbook();

	    Sheet sheet = workbook.createSheet("Relatório Agente Integrador");

	    String[] headersTitle = {"CNPJ", "Nome", "Telefone", "Rua", "Número", "Cidade", 
	    		"Estado", "CEP"};
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
        /*row.createCell(3).setCellValue(contratante.getEndereco().getRua());
        row.createCell(4).setCellValue(contratante.getEndereco().getNumero());
        row.createCell(5).setCellValue(contratante.getEndereco().getCidade());
        row.createCell(6).setCellValue(contratante.getEndereco().getUf());
        row.createCell(7).setCellValue(contratante.getEndereco().getCep());*/
        row.createCell(3).setCellValue("Rua A");
        row.createCell(4).setCellValue(42);
        row.createCell(5).setCellValue("Curitiba");
        row.createCell(6).setCellValue("Paraná");
        row.createCell(7).setCellValue("74329-214");

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    workbook.write(outputStream);
	    
	    return outputStream;
	}
}