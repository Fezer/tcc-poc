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

import br.ufpr.estagio.modulo.enums.EnumTipoContratante;
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Contratante;
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
import java.util.List;

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
	    
	    String html = getHtmlTermoAluno(aluno, estagio);
	    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(outputStream);
	    
	    return outputStream.toByteArray();
	}
	
	private String getHtmlTermoAluno(Aluno aluno, Estagio estagio) {
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
	
	public byte[] gerarPdfContratante(Contratante contratante) throws IOException, DocumentException {
	    ClassLoader classLoader = getClass().getClassLoader();
	    
	    String html = getHtmlRelatorioContratante(contratante);
	    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(outputStream);
	    
	    return outputStream.toByteArray();
	}
	
	private String getHtmlRelatorioContratante(Contratante contratante) {
		// carregar o HTML do arquivo
		ClassLoader classLoader = getClass().getClassLoader();
		
		String html = "";
		try {
			//html = IOUtils.toString(classLoader.getResourceAsStream("copy.html"), StandardCharsets.UTF_8);
			
			if (contratante.getTipo() == EnumTipoContratante.PessoaJuridica)
				html = IOUtils.toString(classLoader.getResourceAsStream("relatorio-contratante-juridico.html"), StandardCharsets.UTF_8);
			
			else if (contratante.getTipo() == EnumTipoContratante.PessoaFisica)
				html = IOUtils.toString(classLoader.getResourceAsStream("relatorio-contratante-fisico.html"), StandardCharsets.UTF_8);

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (contratante.getTipo() == EnumTipoContratante.PessoaJuridica)
			html = html.replace("{{cnpj}}", contratante.getCnpj());
		
		else if (contratante.getTipo() == EnumTipoContratante.PessoaFisica)
			html = html.replace("{{cpf}}", contratante.getCpf());
		
		
		html = html.replace("{{nome}}", contratante.getNome());
		html = html.replace("{{representante}}", contratante.getRepresentanteEmpresa());
		html = html.replace("{{telefone}}", contratante.getTelefone());
		/*html = html.replace("{{ruaContratante}}", "Rua");
		html = html.replace("{{numeroContratante}}", "5");
		html = html.replace("{{cidadeContratante}}", "Curitiba");
		html = html.replace("{{ufContratante}}", "Paraná");
		html = html.replace("{{cepContratante}}", "80213-931");
		*/
		System.out.println(html.length());
		return html;
	}
	
	public byte[] gerarPdfAgenteIntegrador(AgenteIntegrador agenteIntegrador) throws IOException, DocumentException {
	    ClassLoader classLoader = getClass().getClassLoader();
	    
	    String html = getHtmlRelatorioAgenteIntegrador(agenteIntegrador);
	    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(outputStream);
	    
	    return outputStream.toByteArray();
	}
	
	private String getHtmlRelatorioAgenteIntegrador(AgenteIntegrador agenteIntegrador) {
		// carregar o HTML do arquivo
		ClassLoader classLoader = getClass().getClassLoader();
		
		String html = "";
		try {
			//html = IOUtils.toString(classLoader.getResourceAsStream("copy.html"), StandardCharsets.UTF_8);
			
			html = IOUtils.toString(classLoader.getResourceAsStream("relatorio-agente-integrador.html"), StandardCharsets.UTF_8);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		html = html.replace("{{nome}}", agenteIntegrador.getNome());
		html = html.replace("{{cnpj}}", agenteIntegrador.getCnpj());
		html = html.replace("{{telefone}}", agenteIntegrador.getTelefone());
		/*html = html.replace("{{ruaContratante}}", "Rua");
		html = html.replace("{{numeroContratante}}", "5");
		html = html.replace("{{cidadeContratante}}", "Curitiba");
		html = html.replace("{{ufContratante}}", "Paraná");
		html = html.replace("{{cepContratante}}", "80213-931");
		*/
		System.out.println(html.length());
		return html;
	}
	
	public byte[] gerarPdfEstagioSeguradoraUfpr(List<Estagio> estagios) throws IOException, DocumentException {
	    ClassLoader classLoader = getClass().getClassLoader();
	    
	    String html = getHtmlEstagioSeguradoraUfpr(estagios);
	    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(outputStream);
	    
	    return outputStream.toByteArray();
	}
	
	/*private String getHtmlEstagioSeguradoraUfpr(Estagio estagio) {
		// carregar o HTML do arquivo
		ClassLoader classLoader = getClass().getClassLoader();
		
		String html = "";
		try {
			//html = IOUtils.toString(classLoader.getResourceAsStream("copy.html"), StandardCharsets.UTF_8);
			
			html = IOUtils.toString(classLoader.getResourceAsStream("relatorio-estagio-seguradora-ufpr.html"), StandardCharsets.UTF_8);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		html = html.replace("{{nome}}", estagio.getAluno().getNome());
		html = html.replace("{{seguradora}}", String.valueOf(estagio.getSeguradora().isSeguradoraUfpr()));
		//html = html.replace("{{telefone}}", agenteIntegrador.getTelefone());
		/*html = html.replace("{{ruaContratante}}", "Rua");
		html = html.replace("{{numeroContratante}}", "5");
		html = html.replace("{{cidadeContratante}}", "Curitiba");
		html = html.replace("{{ufContratante}}", "Paraná");
		html = html.replace("{{cepContratante}}", "80213-931");
		
		System.out.println(html.length());
		return html;
	}*/
	
	private String getHtmlEstagioSeguradoraUfpr(List<Estagio> estagios) {
	    // Carregar o HTML do arquivo
	    ClassLoader classLoader = getClass().getClassLoader();
	    String html = "";
	    try {
	        html = IOUtils.toString(classLoader.getResourceAsStream("relatorio-estagio-seguradora-ufpr.html"), StandardCharsets.UTF_8);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    StringBuilder estagiosHtml = new StringBuilder();
	    for (Estagio estagio : estagios) {
	        String estagioHtml = "<h2>Estágio de {{nome}}</h2>\n"
	        		+ "        <table>\n"
	        		+ "            <caption>Dados do Estágio</caption>\n"
	        		+ "            <tr>\n"
	        		+ "                <th>Nome Aluno</th>\n"
	        		+ "                <th>Seguradora UFPR?</th>\n"
	        		+ "            </tr>\n"
	        		+ "            <tr>\n"
	        		+ "                <td>{{nome}}</td>\n"
	        		+ "                <td>{{seguradora}}</td>\n"
	        		+ "            </tr>\n"
	        		+ "        </table>\n"
	        		+ "    \n"
	        		+ "        <br></br>\n"
	        		+ "    \n"
	        		+ "        <table>\n"
	        		+ "            <caption>Endereço do Estágio</caption>\n"
	        		+ "            <tr>\n"
	        		+ "                <th>Rua</th>\n"
	        		+ "                <th>Número</th>\n"
	        		+ "                <th>Cidade</th>\n"
	        		+ "                <th>Estado</th>\n"
	        		+ "                <th>CEP</th>\n"
	        		+ "            </tr>\n"
	        		+ "            <tr>\n"
	        		+ "                <td>Rua A</td>\n"
	        		+ "                <td>123</td>\n"
	        		+ "                <td>Curitiba</td>\n"
	        		+ "                <td>Paraná</td>\n"
	        		+ "                <td>82721-412</td>\n"
	        		+ "            </tr>\n"
	        		+ "        </table>";
	        estagioHtml = estagioHtml.replace("{{nome}}", estagio.getAluno().getNome());
	        estagioHtml = estagioHtml.replace("{{seguradora}}", String.valueOf(estagio.getSeguradora().isSeguradoraUfpr()));
	        // Adicionar o HTML do estágio à lista
	        estagiosHtml.append(estagioHtml);
	    }

	    // Substituir a tag no HTML principal com os estágios
	    html = html.replace("{{estagios}}", estagiosHtml.toString());

	    return html;
	}
}
