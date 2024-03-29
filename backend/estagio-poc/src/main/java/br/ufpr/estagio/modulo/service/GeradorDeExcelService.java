package br.ufpr.estagio.modulo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
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
	
	public ByteArrayOutputStream gerarExcelEstagioSeguradoraUfpr(List<Estagio> estagios) throws IOException {
		
	    try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Relatório de Estágios com Seguradora UFPR");
			
			String[] headersTitle = {"Id do Estágio", "Nome Aluno", "GRR", "IRA", "Curso", "Status do Estágio", "Contratante", 
					"Seguradora", "Apólice", "Orientador", "Agente Integrador", "Supervisor", "Data de Início", "Data de Término",
					"Jornada Diária", "Jornada Semanal", "Valor da Bolsa", "Valor de Transporte", "Rua do Estágio", "Número",
					"Cidade", "Estado", "CEP"};
			
			Row titleRow = sheet.createRow(0);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("Relatório de Estágios com Seguradora UFPR");

			int numColumns = headersTitle.length;

			CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, numColumns - 1);
			sheet.addMergedRegion(mergedRegion);

			CellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
			titleCell.setCellStyle(titleCellStyle);

			Row headerRow = sheet.createRow(1);
			for (int i = 0; i < headersTitle.length; i++) {
			    Cell cell = headerRow.createCell(i);
			    cell.setCellValue(headersTitle[i]);
			}

			int rowNum = 2;
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
	}
	
	public ByteArrayOutputStream gerarExcelContratante(Contratante contratante) throws IOException {
		
	    try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Relatório Contratante");
			
			String[] headersTitle = {"Nome", "CNPJ", "CPF", "Representante", "Telefone", "Rua", "Número", "Cidade", 
					"Estado", "CEP", "Quantidade de Estágios"};
			
			Row titleRow = sheet.createRow(0);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("Relatório de Contratante");

			int numColumns = headersTitle.length;

			CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, numColumns - 1);
			sheet.addMergedRegion(mergedRegion);

			CellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
			titleCell.setCellStyle(titleCellStyle);

			Row headerRow = sheet.createRow(1);
			for (int i = 0; i < headersTitle.length; i++) {
			    Cell cell = headerRow.createCell(i);
			    cell.setCellValue(headersTitle[i]);
			}

			int rowNum = 2;
			
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
	}
	
	public ByteArrayOutputStream gerarExcelAgenteIntegrador(AgenteIntegrador agenteIntegrador) throws IOException {
		
	    try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Relatório Agente Integrador");

			String[] headersTitle = {"Nome", "CNPJ", "Telefone", "qtdConvenios", "qtdEstagios", "ativo"};
			Row titleRow = sheet.createRow(0);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("Relatório de Agente Integrador");

			int numColumns = headersTitle.length;

			CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, numColumns - 1);
			sheet.addMergedRegion(mergedRegion);

			CellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
			titleCell.setCellStyle(titleCellStyle);

			Row headerRow = sheet.createRow(1);
			for (int i = 0; i < headersTitle.length; i++) {
			    Cell cell = headerRow.createCell(i);
			    cell.setCellValue(headersTitle[i]);
			}

			int rowNum = 2;
			
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(agenteIntegrador.getNome());
			row.createCell(1).setCellValue(agenteIntegrador.getCnpj());
			row.createCell(2).setCellValue(agenteIntegrador.getTelefone());
			row.createCell(3).setCellValue(agenteIntegrador.getConvenio().size());
			row.createCell(4).setCellValue(agenteIntegrador.getEstagio().size());
			row.createCell(5).setCellValue(String.valueOf(agenteIntegrador.isAtivo()));

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			
			return outputStream;
		}
	}

	public ByteArrayOutputStream gerarExcelCertificadosDeEstagio(List<CertificadoDeEstagio> certificados) throws IOException {
		
	    try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Certificados de Estágio");

			String[] headersTitle = {"Id", "Estagiário", "Orientador", "Contratante", "Data de Início", "Data de Término", 
					"Total de Horas", "Etapa do Fluxo", "Parecer COE", "Motivo da Reprovação"};
			
			Row titleRow = sheet.createRow(0);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("Relatório de Certificados de Estágio");

			int numColumns = headersTitle.length;

			CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, numColumns - 1);
			sheet.addMergedRegion(mergedRegion);

			CellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
			titleCell.setCellStyle(titleCellStyle);

			Row headerRow = sheet.createRow(1);
			for (int i = 0; i < headersTitle.length; i++) {
			    Cell cell = headerRow.createCell(i);
			    cell.setCellValue(headersTitle[i]);
			}

			int rowNum = 2;
			for (CertificadoDeEstagio certificado : certificados) {
			    Row row = sheet.createRow(rowNum++);
			    String dataInicio = new SimpleDateFormat("dd/MM/yyyy").format(certificado.getEstagio().getDataInicio());
				String dataTermino = new SimpleDateFormat("dd/MM/yyyy").format(certificado.getEstagio().getDataTermino());
				
			    row.createCell(0).setCellValue(certificado.getEstagio().getId());
			    row.createCell(1).setCellValue(certificado.getEstagio().getAluno().getNome());
			    row.createCell(2).setCellValue(certificado.getEstagio().getOrientador().getNome());
			    row.createCell(3).setCellValue(certificado.getEstagio().getContratante().getNome());
			    row.createCell(4).setCellValue(dataInicio);
			    row.createCell(5).setCellValue(dataTermino);
			    row.createCell(6).setCellValue(certificado.getEstagio().getFichaDeAvaliacao().getTotalHorasEstagioEfetivamenteRealizadas());
			    row.createCell(7).setCellValue(String.valueOf(certificado.getEtapaFluxo()));
			    row.createCell(8).setCellValue(String.valueOf(certificado.getParecerCOE()));
			    if (certificado.getMotivoReprovacao() == null)
			    	row.createCell(9).setCellValue("Não houve reprovação.");
			    else
			    	row.createCell(9).setCellValue(String.valueOf(certificado.getMotivoReprovacao()));
			    
			    break;
			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			
			return outputStream;
		}
	}
	
	public ByteArrayOutputStream gerarExcelRelatoriosDeEstagio(List<RelatorioDeEstagio> relatorios) throws IOException {
		
	    try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Relatórios de Estágio");
	        
			String[] headersTitle = {"Id do Relatório", "Nome do Aluno", "Ciência do Orientador", "Tipo do Relatório", 
					"Etapa do Fluxo", "Aval. Atividades", "Aval. Contribuição", "Aval. Desenvolvimento",
					"Aval. Efetivação", "Aval. Formação", "Aval. Relações", "Considerações"};
			Row titleRow = sheet.createRow(0);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("Relatório de Relatórios de Estágio");

			int numColumns = headersTitle.length;

			CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, numColumns - 1);
			sheet.addMergedRegion(mergedRegion);

			CellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
			titleCell.setCellStyle(titleCellStyle);

			Row headerRow = sheet.createRow(1);
			for (int i = 0; i < headersTitle.length; i++) {
			    Cell cell = headerRow.createCell(i);
			    cell.setCellValue(headersTitle[i]);
			}

			int rowNum = 2;
			for (RelatorioDeEstagio relatorio : relatorios) {
			    Row row = sheet.createRow(rowNum++);
		        
			    row.createCell(0).setCellValue(relatorio.getEstagio().getId());
			    row.createCell(1).setCellValue(relatorio.getEstagio().getAluno().getNome());
			    row.createCell(2).setCellValue(String.valueOf(relatorio.isCienciaOrientador()));
			    row.createCell(3).setCellValue(String.valueOf(relatorio.getTipoRelatorio()));
			    row.createCell(4).setCellValue(String.valueOf(relatorio.getEtapaFluxo()));
			    row.createCell(5).setCellValue(String.valueOf(relatorio.getAvalAtividades()));
			    row.createCell(6).setCellValue(String.valueOf(relatorio.getAvalContribuicaoEstagio()));
			    row.createCell(7).setCellValue(String.valueOf(relatorio.getAvalDesenvolvimentoAtividades()));
			    row.createCell(8).setCellValue(String.valueOf(relatorio.getAvalEfetivacao()));
			    row.createCell(9).setCellValue(String.valueOf(relatorio.getAvalFormacaoProfissional()));
			    row.createCell(10).setCellValue(String.valueOf(relatorio.getAvalRelacoesInterpessoais()));
			    row.createCell(11).setCellValue(relatorio.getConsideracoes());
			    
			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			
			return outputStream;
		}
	}

	public ByteArrayOutputStream gerarExcelRelatorioDeEstagio(RelatorioDeEstagio relatorio) throws IOException {
		
	    try (Workbook workbook = new XSSFWorkbook()) {
	    	//Workbook workbook = new XSSFWorkbook();

			Sheet sheet = workbook.createSheet("Relatório de Estágio");

			String[] headersTitle = {"Id do Relatório", "Nome do Aluno", "Ciência do Orientador", "Tipo do Relatório", 
					"Etapa do Fluxo", "Aval. Atividades", "Aval. Contribuição", "Aval. Desenvolvimento",
					"Aval. Efetivação", "Aval. Formação", "Aval. Relações", "Considerações"};
			Row titleRow = sheet.createRow(0);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("Relatório de Relatório de Estágio");

			int numColumns = headersTitle.length;

			CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, numColumns - 1);
			sheet.addMergedRegion(mergedRegion);

			CellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
			titleCell.setCellStyle(titleCellStyle);

			Row headerRow = sheet.createRow(1);
			for (int i = 0; i < headersTitle.length; i++) {
			    Cell cell = headerRow.createCell(i);
			    cell.setCellValue(headersTitle[i]);
			}

			int rowNum = 2;
			Row row = sheet.createRow(rowNum++);
			
			row.createCell(0).setCellValue(relatorio.getEstagio().getId());
		    row.createCell(1).setCellValue(relatorio.getEstagio().getAluno().getNome());
		    row.createCell(2).setCellValue(String.valueOf(relatorio.isCienciaOrientador()));
		    row.createCell(3).setCellValue(String.valueOf(relatorio.getTipoRelatorio()));
		    row.createCell(4).setCellValue(String.valueOf(relatorio.getEtapaFluxo()));
		    row.createCell(5).setCellValue(String.valueOf(relatorio.getAvalAtividades()));
		    row.createCell(6).setCellValue(String.valueOf(relatorio.getAvalContribuicaoEstagio()));
		    row.createCell(7).setCellValue(String.valueOf(relatorio.getAvalDesenvolvimentoAtividades()));
		    row.createCell(8).setCellValue(String.valueOf(relatorio.getAvalEfetivacao()));
		    row.createCell(9).setCellValue(String.valueOf(relatorio.getAvalFormacaoProfissional()));
		    row.createCell(10).setCellValue(String.valueOf(relatorio.getAvalRelacoesInterpessoais()));
		    row.createCell(11).setCellValue(relatorio.getConsideracoes());

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			
			return outputStream;
		}
	}
}
