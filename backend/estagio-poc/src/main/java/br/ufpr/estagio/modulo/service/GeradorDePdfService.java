package br.ufpr.estagio.modulo.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class GeradorDePdfService {
	public void gerarPdf() {
		try {
			Document document = new Document(PageSize.A4);
			
			DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
			String currentDateTime = dateFormatter.format(new Date());
			
			String filePath = "/home/gabriel/Documentos/" + currentDateTime + ".pdf";
			
			PdfWriter.getInstance(document, new FileOutputStream(filePath));
			
			document.open();
			
			Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontTitle.setSize(16);

            Paragraph title = new Paragraph("CONTRATO DE ESTÁGIO", fontTitle);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);

            // Informações do Aluno
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontHeader.setSize(12);

            Paragraph header = new Paragraph("INFORMAÇÕES DO ALUNO", fontHeader);
            header.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(header);

            document.add(new Paragraph("Nome do Aluno: Fulano de Tal"));
            document.add(new Paragraph("RG: 1234567"));
            document.add(new Paragraph("CPF: 123.456.789-00"));
            document.add(new Paragraph("Curso: TADS"));
            document.add(new Paragraph("Matrícula: 123456789"));
            document.add(new Paragraph("Nível: Superior"));
            document.add(new Paragraph("Email: fulano@exemplo.com"));
            document.add(new Paragraph("Data de nascimento: 01/01/1990"));

            // Endereço do Aluno
            Font fontHeader2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontHeader2.setSize(12);

            Paragraph header2 = new Paragraph("ENDEREÇO DO ALUNO", fontHeader2);
            header2.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(header2);

            document.add(new Paragraph("Rua: Rua Tal"));
            document.add(new Paragraph("Número: 123"));
            document.add(new Paragraph("Complemento: Ap. 123"));
            document.add(new Paragraph("Telefone: (11) 1234-5678"));
            document.add(new Paragraph("Cidade: São Paulo"));
            document.add(new Paragraph("Estado: SP"));
            document.add(new Paragraph("CEP: 12345-678"));

            // Informações do Contratante
            Font fontHeader3 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontHeader3.setSize(12);

            Paragraph header3 = new Paragraph("INFORMAÇÕES DO CONTRATANTE", fontHeader3);
            header3.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(header3);

            document.add(new Paragraph("Contratante: Pessoa Física"));

            document.add(new Paragraph("Nome do Contratante: João da Silva"));
            document.add(new Paragraph("CPF: 123.456.789-00"));
            document.add(new Paragraph("Telefone: (11) 1234-5678"));

            // Endereço do Contratante
            Font fontHeader4 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontHeader4.setSize(12);

            Paragraph header4 = new Paragraph("ENDEREÇO DO CONTRATANTE", fontHeader4);
            header4.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(header4);

            document.add(new Paragraph("Rua: Rua do Contratante"));
            document.add(new Paragraph("Número: 123"));
            document.add(new Paragraph("Cidade: São Paulo"));
            document.add(new Paragraph("Estado: SP"));
			
			document.close();
					
			System.out.println("PDF gerado");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
