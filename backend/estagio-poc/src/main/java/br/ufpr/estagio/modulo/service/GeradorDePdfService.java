package br.ufpr.estagio.modulo.service;

import java.io.FileOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.html2pdf.HtmlConverter;

import org.apache.commons.io.IOUtils;
//import com.lowagie.text.DocumentException;
import org.springframework.core.io.ClassPathResource;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.lowagie.text.DocumentException;

import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Estagio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

@Service
public class GeradorDePdfService {
	
	/*public void generatePdf(String html, String filename) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();

        try (OutputStream os = new FileOutputStream(filename)) {
            renderer.createPDF(os);
        }
    }
	
	public void generatePdfFromClasspath(String input, String filename) throws IOException, DocumentException {
        ClassPathResource resource = new ClassPathResource(input);
        try (InputStream is = resource.getInputStream()) {
            generatePdf(new String(is.readAllBytes()), filename);
        }
    }*/
	
	/*public byte[] gerarPdf(Aluno aluno, Estagio estagio) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        String html = getHtml(aluno, estagio);
        
        ConverterProperties converterProperties = new ConverterProperties();
        //converterProperties.setBaseUri("src/main/resources");
        converterProperties.setBaseUri(classLoader.getResource("TermoCompromisso-Obrigatorio-Ufpr-EstudanteUfpr.html").toString()); // caminho base dos recursos
        
        //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);

        //HtmlConverter.convertToPdf(new FileInputStream(new File(classLoader.getResource("TermoCompromisso-Obrigatorio-Ufpr-EstudanteUfpr.html").getFile())), pdf, converterProperties);
        HtmlConverter.convertToPdf(html, pdf, converterProperties);
        
        pdf.close();
        return outputStream.toByteArray();
    }*/
	
	public byte[] gerarPdf(Aluno aluno, Estagio estagio) throws IOException, DocumentException {
	    ClassLoader classLoader = getClass().getClassLoader();
	    
	    String html = getHtml(aluno, estagio);
	    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(outputStream);
	    
	    return outputStream.toByteArray();
	}
	
	private String getHtml(Aluno aluno, Estagio estagio) {
		// carregar o HTML do arquivo
		ClassLoader classLoader = getClass().getClassLoader();
		
		Path diretorioAtual = Paths.get("").toAbsolutePath();
    	
    	String resources = diretorioAtual + "/src/main/resources/";
		
		String cssPath = resources + "termo/bootstrap.min.css";
		String estiloPath = resources + "termo/estilo.css";
		String jqueryPath = resources + "termo/jquery.js";
		String bootstrapPath = resources + "termo/bootstrap.js";
		String scriptPath = resources + "termo/script.js";

		//System.out.println(scriptPath);
		
		/*URL cssUrl = classLoader.getResource(cssPath);
		URL estiloUrl = classLoader.getResource(estiloPath);
		URL jqueryUrl = classLoader.getResource(jqueryPath);
		URL bootstrapUrl = classLoader.getResource(bootstrapPath);
		URL scriptUrl = classLoader.getResource(scriptPath);
		
		System.out.println("cssUrl: " + cssUrl);
		System.out.println("estiloUrl: " + estiloUrl);
		System.out.println("jqueryUrl: " + jqueryUrl);
		System.out.println("bootstrapUrl: " + bootstrapUrl);
		System.out.println("scriptUrl: " + scriptUrl);
		*/
		String html = "";
		try {
			
			html = IOUtils.toString(classLoader.getResourceAsStream("TermoCompromisso-Obrigatorio-Ufpr-EstudanteUfpr.html"), StandardCharsets.UTF_8);
			//html = IOUtils.toString(classLoader.getResourceAsStream("termo.html"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//html = html.replace("${cssPath}", cssPath);
		//html = html.replace("${estiloPath}", estiloPath);
		//html = html.replace("${jqueryPath}", jqueryPath);
		html = html.replace("${bootstrapPath}", bootstrapPath);
		//html = html.replace("${scriptPath}", scriptPath);
		
		
		String imagePath = resources + "termo/prograd.png";
		html = html.replace("{{imagePath}}", imagePath);
		// Informacoes do concedente
		
		/*Estagio estagio = new Estagio();
		estagio = aluno.getEstagio().get(0);*/
		
		if (estagio.isEstagioUfpr()) {
			html = html.replace("{{razaoSocial}}", "Universidade Federal do Paraná");
			html = html.replace("{{cnpj}}", "CNPJ UFPR");
			html = html.replace("{{representante}}", "Dieval Guizelini");
			html = html.replace("{{telefoneContratante}}", "41 92924 9201");
			html = html.replace("{{ruaContratante}}", "Rua Alguma Coisa Arcoverde");
			html = html.replace("{{numeroContratante}}", "1725");
			html = html.replace("{{cidadeContratante}}", "Curitiba");
			html = html.replace("{{ufContratante}}", "Paraná");
			html = html.replace("{{cepContratante}}", "80213-931");
		} else {
			html = html.replace("{{razaoSocial}}", estagio.getContratante().getNome());
			html = html.replace("{{cnpj}}", estagio.getContratante().getCnpj());
			html = html.replace("{{representante}}", estagio.getContratante().getRepresentanteEmpresa());
			html = html.replace("{{telefoneContratante}}", estagio.getContratante().getTelefone());
			/*html = html.replace("{{ruaContratante}}", estagio.getContratante().getEndereco().getRua());
			html = html.replace("{{numeroContratante}}", String.valueOf(estagio.getContratante().getEndereco().getNumero()));
			html = html.replace("{{cidadeContratante}}", estagio.getContratante().getEndereco().getCidade());
			html = html.replace("{{ufContratante}}", estagio.getContratante().getEndereco().getUf());
			html = html.replace("{{cepContratante}}", estagio.getContratante().getEndereco().getCep());
			*/
			html = html.replace("{{ruaContratante}}", "Rua");
			html = html.replace("{{numeroContratante}}", "5");
			html = html.replace("{{cidadeContratante}}", "Curitiba");
			html = html.replace("{{ufContratante}}", "Paraná");
			html = html.replace("{{cepContratante}}", "80213-931");
		}
		
		// Informacoes do aluno
		String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(aluno.getDataNascimento());

		html = html.replace("{{nome}}", aluno.getNome());
		html = html.replace("{{rg}}", aluno.getRg());
		html = html.replace("{{dataNascimento}}", dataFormatada);
		html = html.replace("{{telefone}}", aluno.getTelefone());
		html = html.replace("{{email}}", aluno.getEmail());
		/*html = html.replace("{{rua}}", aluno.getEndereco().getRua());
		html = html.replace("{{numeroEndereco}}", String.valueOf(aluno.getEndereco().getNumero()));
		html = html.replace("{{complemento}}", aluno.getEndereco().getComplemento());
		html = html.replace("{{cidade}}", aluno.getEndereco().getCidade());
		html = html.replace("{{uf}}", aluno.getEndereco().getUf());
		html = html.replace("{{cep}}", aluno.getEndereco().getCep());*/
		html = html.replace("{{rua}}", "Rua X");
		html = html.replace("{{numeroEndereco}}", "0");
		html = html.replace("{{complemento}}", "Casa 2");
		html = html.replace("{{cidade}}", "Curitiba");
		html = html.replace("{{uf}}", "Paraná");
		html = html.replace("{{cep}}", "81810-481");
		
		html = html.replace("{{curso}}", aluno.getCurso().getNome());
		
		html = html.replace("{{matricula}}", aluno.getMatricula());
		html = html.replace("{{nivel}}", "4º Período"); // NULO!!
		html = html.replace("{{instituicao}}", "Universidade Federal do Paraná");
		
		return html;
	}
}
