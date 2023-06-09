package br.ufpr.estagio.modulo.service;

import java.io.FileOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.html2pdf.HtmlConverter;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lowagie.text.DocumentException;
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
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.FichaDeAvaliacao;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;

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
import java.util.Optional;

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
	
	public byte[] gerarPdfFicha(Aluno aluno, FichaDeAvaliacao ficha) throws IOException, DocumentException {
	    ClassLoader classLoader = getClass().getClassLoader();
	    
	    String html = getHtmlFichaAluno(aluno, ficha);
	    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(outputStream);
	    
	    return outputStream.toByteArray();
	}
	
	private String getHtmlFichaAluno(Aluno aluno, FichaDeAvaliacao ficha) {
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
			
			html = IOUtils.toString(classLoader.getResourceAsStream("FichaDeAvaliacao.html"), StandardCharsets.UTF_8);
			
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
		
		// Informacoes da ficha
		html = html.replace("{{comentarioCoordenador}}", ficha.getAcompanhamentoCoordenadorComentario());
		html = html.replace("{{comentarioOrientador}}", ficha.getAcompanhamentoOrientadorComentario());
		html = html.replace("{{atividadesRealizadas}}", ficha.getAtividadesRealizadasConsideracoes());
		html = html.replace("{{contribuicao}}", ficha.getContribuicaoEstagio());
		html = html.replace("{{acompanhamentoCoordenador}}", String.valueOf(ficha.getAcompanhamentoCoordenador()));
		html = html.replace("{{acompanhamentoOrientador}}", String.valueOf(ficha.getAcompanhamentoOrientador()));
		html = html.replace("{{conduta}}", String.valueOf(ficha.getAvalConduta()));
		html = html.replace("{{criatividade}}", String.valueOf(ficha.getAvalCriatividade()));
		html = html.replace("{{dominio}}", String.valueOf(ficha.getAvalDominioTecnico()));
		html = html.replace("{{efetivacao}}", String.valueOf(ficha.getAvalEfetivacao()));
		html = html.replace("{{habilidades}}", String.valueOf(ficha.getAvalHabilidades()));
		html = html.replace("{{pontualidade}}", String.valueOf(ficha.getAvalPontualidade()));
		html = html.replace("{{protagonismo}}", String.valueOf(ficha.getAvalProtagonismo()));
		html = html.replace("{{responsabilidade}}", String.valueOf(ficha.getAvalResponsabilidade()));
		
		
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
	        		+ "    <table>\n"
	        		+ "        <caption>Dados do Estágio</caption>\n"
	        		+ "        <tr>\n"
	        		+ "            <th>Nome Aluno</th>\n"
	        		+ "            <th>GRR</th>\n"
	        		+ "			<th>IRA</th>\n"
	        		+ "			<th>Curso</th>\n"
	        		+ "        </tr>\n"
	        		+ "		<tr>\n"
	        		+ "            <td>{{nome}}</td>\n"
	        		+ "            <td>{{grr}}</td>\n"
	        		+ "			<td>{{ira}}</td>\n"
	        		+ "            <td>{{curso}}</td>\n"
	        		+ "        </tr>\n"
	        		+ "        <br></br>\n"
	        		+ "		<tr>\n"
	        		+ "            <th colspan=\"2\">Contratante</th>\n"
	        		+ "            <th>Seguradora</th>\n"
	        		+ "			<th>Apólice</th>\n"
	        		+ "            <td></td>\n"
	        		+ "        </tr>\n"
	        		+ "		<tr>\n"
	        		+ "            <td colspan=\"2\">{{contratante}}</td>\n"
	        		+ "            <td>{{seguradora}}</td>\n"
	        		+ "			<td>{{apolice}}</td>\n"
	        		+ "            <td></td>\n"
	        		+ "        </tr>\n"
	        		+ "        <br></br>\n"
	        		+ "		<tr>\n"
	        		+ "            <th colspan=\"2\">Orientador</th>\n"
	        		+ "            <th>Agente Integrador</th>\n"
	        		+ "			<th>Supervisor</th>\n"
	        		+ "        </tr>\n"
	        		+ "		<tr>\n"
	        		+ "            <td colspan=\"2\">{{orientador}}</td>\n"
	        		+ "            <td>{{agente}}</td>\n"
	        		+ "			<td>{{supervisor}}</td>\n"
	        		+ "        </tr>\n"
	        		+ "        <br></br>\n"
	        		+ "		<tr>\n"
	        		+ "            <th>Data de Início</th>\n"
	        		+ "            <th>Data de Término</th>\n"
	        		+ "			<th>Jornada Diária</th>\n"
	        		+ "            <th>Jornada Semanal</th>\n"
	        		+ "        </tr>\n"
	        		+ "		<tr>\n"
	        		+ "            <td>{{dataInicio}}</td>\n"
	        		+ "            <td>{{dataTermino}}</td>\n"
	        		+ "			<td>{{jornadaDiaria}}</td>\n"
	        		+ "            <td>{{jornadaSemanal}}</td>\n"
	        		+ "        </tr>\n"
	        		+ "        <br></br>\n"
	        		+ "		<tr>\n"
	        		+ "            <th colspan=\"2\">Valor da Bolsa</th>\n"
	        		+ "            <th colspan=\"2\">Valor de Transporte</th>\n"
	        		+ "        </tr>\n"
	        		+ "		<tr>\n"
	        		+ "            <td colspan=\"2\">{{valorBolsa}}</td>\n"
	        		+ "            <td colspan=\"2\">{{valorTransporte}}</td>\n"
	        		+ "        </tr>\n"
	        		+ "    </table>\n"
	        		+ "\n"
	        		+ "    <br></br>\n"
	        		+ "\n"
	        		+ "    <table>\n"
	        		+ "        <caption>Endereço do Estágio</caption>\n"
	        		+ "        <tr>\n"
	        		+ "            <th colspan=\"2\">Rua</th>\n"
	        		+ "            <th>Número</th>\n"
	        		+ "		</tr>\n"
	        		+ "		<tr>\n"
	        		+ "            <td colspan=\"2\">Rua XXXXXXXX YYYYYYY ZZZZZZZZ</td>\n"
	        		+ "            <td>123</td>\n"
	        		+ "		</tr>\n"
	        		+ "\n"
	        		+ "		<tr>\n"
	        		+ "            <th>Cidade</th>\n"
	        		+ "            <th>Estado</th>\n"
	        		+ "            <th>CEP</th>\n"
	        		+ "        </tr>\n"
	        		+ "        <tr>\n"
	        		+ "            <td>Curitiba</td>\n"
	        		+ "            <td>Paraná</td>\n"
	        		+ "            <td>82721-412</td>\n"
	        		+ "        </tr>\n"
	        		+ "    </table>\n"
	        		+ "\n"
	        		+ "	<br></br>";
	        estagioHtml = estagioHtml.replace("{{nome}}", estagio.getAluno().getNome());
	        estagioHtml = estagioHtml.replace("{{seguradora}}", String.valueOf(estagio.getSeguradora().isSeguradoraUfpr()));
	        estagioHtml = estagioHtml.replace("{{grr}}", estagio.getAluno().getMatricula());
	        estagioHtml = estagioHtml.replace("{{ira}}", estagio.getAluno().getIra());
	        estagioHtml = estagioHtml.replace("{{curso}}", estagio.getAluno().getCurso().getNome());
	        estagioHtml = estagioHtml.replace("{{contratante}}", estagio.getContratante().getNome());
	        estagioHtml = estagioHtml.replace("{{apolice}}", String.valueOf(estagio.getApolice().getNumero()));
	        estagioHtml = estagioHtml.replace("{{agente}}", estagio.getAgenteIntegrador().getNome());

//	        estagioHtml = estagioHtml.replace("{{orientador}}", estagio.getOrientador().getNome());
	        estagioHtml = estagioHtml.replace("{{orientador}}", "Orientador Estático");
	        
	        estagioHtml = estagioHtml.replace("{{supervisor}}", estagio.getPlanoDeAtividades().getNomeSupervisor());
	        
	        //String dataInicioFormatada = new SimpleDateFormat("dd/MM/yyyy").format(estagio.getDataInicio());
	        //estagioHtml = estagioHtml.replace("{{dataInicio}}", dataInicioFormatada);
	        estagioHtml = estagioHtml.replace("{{dataInicio}}", "01/01/2022");
	        
	        //String dataTerminoFormatada = new SimpleDateFormat("dd/MM/yyyy").format(estagio.getDataTermino());
	        //estagioHtml = estagioHtml.replace("{{dataTermino}}", dataTerminoFormatada);
	        estagioHtml = estagioHtml.replace("{{dataTermino}}", "01/01/2023");
	        
	        estagioHtml = estagioHtml.replace("{{jornadaDiaria}}", String.valueOf(estagio.getJornadaDiaria()));
	        estagioHtml = estagioHtml.replace("{{jornadaSemanal}}", String.valueOf(estagio.getJornadaSemanal()));
	        
	        estagioHtml = estagioHtml.replace("{{valorBolsa}}", String.valueOf(estagio.getValorBolsa()));
	        estagioHtml = estagioHtml.replace("{{valorTransporte}}", String.valueOf(estagio.getValorTransporte()));
	        // Adicionar o HTML do estágio à lista
	        estagiosHtml.append(estagioHtml);
	    }

	    // Substituir a tag no HTML principal com os estágios
	    html = html.replace("{{estagios}}", estagiosHtml.toString());

	    return html;
	}
	
	public byte[] gerarPdfCertificadosDeEstagio(List<CertificadoDeEstagio> certificados) throws IOException, DocumentException {
	    ClassLoader classLoader = getClass().getClassLoader();
	    
	    String html = getHtmlCertificadosDeEstagio(certificados);
	    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(outputStream);
	    
	    return outputStream.toByteArray();
	}
	
	private String getHtmlCertificadosDeEstagio(List<CertificadoDeEstagio> certificados) {
	    // Carregar o HTML do arquivo
	    ClassLoader classLoader = getClass().getClassLoader();
	    String html = "";
	    try {
	        html = IOUtils.toString(classLoader.getResourceAsStream("relatorio-certificado-estagio.html"), StandardCharsets.UTF_8);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    StringBuilder estagiosHtml = new StringBuilder();
	    for (CertificadoDeEstagio certificado : certificados) {
	        String estagioHtml = "<h2>Certificado de Estágio de {{nome}}</h2>\n"
	        		+ "    <table>\n"
	        		+ "        <caption>Somente jogando os dados</caption>\n"
	        		+ "        <tr>\n"
	        		+ "            <th>Etapa Fluxo</th>\n"
	        		+ "            <th>Parecer COE</th>\n"
	        		+ "        </tr>\n"
	        		+ "		<tr>\n"
	        		+ "            <td>{{etapaFluxo}}</td>\n"
	        		+ "            <td>{{parecerCoe}}</td>\n"
	        		+ "        </tr>\n"
	        		+ "        <br></br>\n"
	        		+ "		\n"
	        		+ "    </table>";
	        estagioHtml = estagioHtml.replace("{{etapaFluxo}}", String.valueOf(certificado.getEtapaFluxo()));
	        estagioHtml = estagioHtml.replace("{{parecerCoe}}", String.valueOf(certificado.getParecerCOE()));
	        estagioHtml = estagioHtml.replace("{{nome}}", certificado.getEstagio().getAluno().getNome());
	        // Adicionar o HTML do estágio à lista
	        estagiosHtml.append(estagioHtml);
	    }

	    // Substituir a tag no HTML principal com os estágios
	    html = html.replace("{{certificados}}", estagiosHtml.toString());

	    return html;
	}
	
	public byte[] gerarPdfCertificadoOrientador(Orientador orientador, CertificadoDeEstagio certificado) throws IOException, DocumentException {
	    ClassLoader classLoader = getClass().getClassLoader();
	    
	    String html = getHtmlCertificadoOrientador(orientador, certificado);
	    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(outputStream);
	    
	    return outputStream.toByteArray();
	}
	
	private String getHtmlCertificadoOrientador(Orientador orientador, CertificadoDeEstagio certificado) {
	    // Carregar o HTML do arquivo
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
			
			html = IOUtils.toString(classLoader.getResourceAsStream("CertificadoDeEstagioOrientador.html"), StandardCharsets.UTF_8);
			
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
		
		// Informacoes da ficha
		if (certificado.getMotivoReprovacao() == null)
			html = html.replace("{{reprovacao}}", "Não houve reprovação.");
		else
			html = html.replace("{{reprovacao}}", certificado.getMotivoReprovacao());
		
		html = html.replace("{{nome}}", certificado.getEstagio().getAluno().getNome());
		html = html.replace("{{grr}}", certificado.getEstagio().getAluno().getMatricula());
		html = html.replace("{{ira}}", certificado.getEstagio().getAluno().getIra());
		html = html.replace("{{orientador}}", certificado.getEstagio().getOrientador().getNome());
		html = html.replace("{{curso}}", certificado.getEstagio().getAluno().getCurso().getNome());
		html = html.replace("{{etapaFluxo}}", String.valueOf(certificado.getEtapaFluxo()));
		html = html.replace("{{parecerCoe}}", String.valueOf(certificado.getParecerCOE()));
		html = html.replace("{{statusEstagio}}", String.valueOf(certificado.getEstagio().getStatusEstagio()));
		
		return html;
	}
	
	public byte[] gerarPdfRelatoriosDeEstagio(List<RelatorioDeEstagio> relatorios) throws IOException, DocumentException {
	    ClassLoader classLoader = getClass().getClassLoader();
	    
	    String html = getHtmlRelatoriosDeEstagio(relatorios);
	    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(outputStream);
	    
	    return outputStream.toByteArray();
	}
	
	private String getHtmlRelatoriosDeEstagio(List<RelatorioDeEstagio> relatorios) {
	    // Carregar o HTML do arquivo
	    ClassLoader classLoader = getClass().getClassLoader();
	    String html = "";
	    try {
	        html = IOUtils.toString(classLoader.getResourceAsStream("relatorio-relatorios-de-estagio.html"), StandardCharsets.UTF_8);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    StringBuilder estagiosHtml = new StringBuilder();
	    for (RelatorioDeEstagio relatorio : relatorios) {
	        String estagioHtml = "<h2>Relatório de Estágio de {{nome}}</h2>\n"
	        		+ "    <table>\n"
	        		+ "        <caption>Somente jogando os dados</caption>\n"
	        		+ "        <tr>\n"
	        		+ "            <th>Ciência Orientador</th>\n"
	        		+ "            <th>Considerações</th>\n"
	        		+ "        </tr>\n"
	        		+ "		<tr>\n"
	        		+ "            <td>{{cienciaOrientador}}</td>\n"
	        		+ "            <td>{{consideracoes}}</td>\n"
	        		+ "        </tr>\n"
	        		+ "        <br></br>\n"
	        		+ "		\n"
	        		+ "    </table>";
	        estagioHtml = estagioHtml.replace("{{cienciaOrientador}}", String.valueOf(relatorio.isCienciaOrientador()));
	        estagioHtml = estagioHtml.replace("{{consideracoes}}", relatorio.getConsideracoes());
	        estagioHtml = estagioHtml.replace("{{nome}}", relatorio.getEstagio().getAluno().getNome());
	        // Adicionar o HTML do estágio à lista
	        estagiosHtml.append(estagioHtml);
	    }

	    // Substituir a tag no HTML principal com os estágios
	    html = html.replace("{{relatorios}}", estagiosHtml.toString());

	    return html;
	}
	
	public byte[] gerarPdfRelatorioDeEstagio(Optional<RelatorioDeEstagio> relatorio) throws IOException, DocumentException {
	    ClassLoader classLoader = getClass().getClassLoader();
	    
	    String html = getHtmlRelatorioDeEstagio(relatorio);
	    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(outputStream);
	    
	    return outputStream.toByteArray();
	}
	
	private String getHtmlRelatorioDeEstagio(Optional<RelatorioDeEstagio> relatorioFind) {
	    // Carregar o HTML do arquivo
		RelatorioDeEstagio relatorio = relatorioFind.get();
		
	    ClassLoader classLoader = getClass().getClassLoader();
	    String html = "";
	    try {
	        html = IOUtils.toString(classLoader.getResourceAsStream("relatorio-relatorio-de-estagio.html"), StandardCharsets.UTF_8);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    StringBuilder estagiosHtml = new StringBuilder();
	    
	        String estagioHtml = "<h2>Relatório de Estágio de {{nome}}</h2>\n"
	        		+ "    <table>\n"
	        		+ "        <caption>Somente jogando os dados</caption>\n"
	        		+ "        <tr>\n"
	        		+ "            <th>Ciência Orientador</th>\n"
	        		+ "            <th>Considerações</th>\n"
	        		+ "        </tr>\n"
	        		+ "		<tr>\n"
	        		+ "            <td>{{cienciaOrientador}}</td>\n"
	        		+ "            <td>{{consideracoes}}</td>\n"
	        		+ "        </tr>\n"
	        		+ "        <br></br>\n"
	        		+ "		\n"
	        		+ "    </table>";
	        estagioHtml = estagioHtml.replace("{{cienciaOrientador}}", String.valueOf(relatorio.isCienciaOrientador()));
	        estagioHtml = estagioHtml.replace("{{consideracoes}}", relatorio.getConsideracoes());
	        estagioHtml = estagioHtml.replace("{{nome}}", relatorio.getEstagio().getAluno().getNome());
	        // Adicionar o HTML do estágio à lista
	        estagiosHtml.append(estagioHtml);
	    

	    // Substituir a tag no HTML principal com os estágios
	    html = html.replace("{{relatorio}}", estagiosHtml.toString());

	    return html;
	}
	
	public byte[] gerarPdfAlunoRelatorioDeEstagio(RelatorioDeEstagio relatorio) throws IOException, DocumentException {
	    ClassLoader classLoader = getClass().getClassLoader();
	    
	    String html = getHtmlAlunoRelatorioDeEstagio(relatorio);
	    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(outputStream);
	    
	    return outputStream.toByteArray();
	}
	
	private String getHtmlAlunoRelatorioDeEstagio(RelatorioDeEstagio relatorio) {
	    // Carregar o HTML do arquivo
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
			
			html = IOUtils.toString(classLoader.getResourceAsStream("RelatorioDeEstagio.html"), StandardCharsets.UTF_8);
			
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
		
		// Informacoes do relatório
		html = html.replace("{{consideracoes}}", relatorio.getConsideracoes());
		html = html.replace("{{avalAtividades}}", String.valueOf(relatorio.getAvalAtividades()));
		html = html.replace("{{contribuicao}}", String.valueOf(relatorio.getAvalContribuicaoEstagio()));
		html = html.replace("{{desenvolvimento}}", String.valueOf(relatorio.getAvalDesenvolvimentoAtividades()));
		html = html.replace("{{efetivacao}}", String.valueOf(relatorio.getAvalEfetivacao()));
		html = html.replace("{{formacao}}", String.valueOf(relatorio.getAvalFormacaoProfissional()));
		html = html.replace("{{relacoesInterpessoais}}", String.valueOf(relatorio.getAvalRelacoesInterpessoais()));
		html = html.replace("{{nome}}", relatorio.getEstagio().getAluno().getNome());
		html = html.replace("{{grr}}", relatorio.getEstagio().getAluno().getMatricula());
		html = html.replace("{{ira}}", relatorio.getEstagio().getAluno().getIra());
		html = html.replace("{{curso}}", relatorio.getEstagio().getAluno().getCurso().getNome());
		
		return html;
	}
	
	public byte[] gerarPdfAlunoTermoAditivo(TermoDeEstagio termo) throws IOException, DocumentException {
	    ClassLoader classLoader = getClass().getClassLoader();
	    
	    String html = getHtmlAlunoTermoAditivo(termo);
	    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(outputStream);
	    
	    return outputStream.toByteArray();
	}
	
	private String getHtmlAlunoTermoAditivo(TermoDeEstagio termo) {
	    // Carregar o HTML do arquivo
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
			
			html = IOUtils.toString(classLoader.getResourceAsStream("TermoAditivo.html"), StandardCharsets.UTF_8);
			
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
		
		// Informacoes do relatório
		if (termo.getEstagio().isEstagioUfpr()) {
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
			html = html.replace("{{razaoSocial}}", termo.getEstagio().getContratante().getNome());
			html = html.replace("{{cnpj}}", termo.getEstagio().getContratante().getCnpj());
			html = html.replace("{{representante}}", termo.getEstagio().getContratante().getRepresentanteEmpresa());
			html = html.replace("{{telefoneContratante}}", termo.getEstagio().getContratante().getTelefone());
			/*html = html.replace("{{ruaContratante}}", termo.getEstagio().getContratante().getEndereco().getRua());
			html = html.replace("{{numeroContratante}}", termo.getEstagio().valueOf(estagio.getContratante().getEndereco().getNumero()));
			html = html.replace("{{cidadeContratante}}", termo.getEstagio().getContratante().getEndereco().getCidade());
			html = html.replace("{{ufContratante}}", termo.getEstagio().getContratante().getEndereco().getUf());
			html = html.replace("{{cepContratante}}", termo.getEstagio().getContratante().getEndereco().getCep());
			*/
			html = html.replace("{{ruaContratante}}", "Rua");
			html = html.replace("{{numeroContratante}}", "5");
			html = html.replace("{{cidadeContratante}}", "Curitiba");
			html = html.replace("{{ufContratante}}", "Paraná");
			html = html.replace("{{cepContratante}}", "80213-931");
		}
		
		// Informacoes do aluno
		String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(termo.getEstagio().getAluno().getDataNascimento());

		html = html.replace("{{nome}}", termo.getEstagio().getAluno().getNome());
		html = html.replace("{{rg}}", termo.getEstagio().getAluno().getRg());
		html = html.replace("{{dataNascimento}}", dataFormatada);
		html = html.replace("{{telefone}}", termo.getEstagio().getAluno().getTelefone());
		html = html.replace("{{email}}", termo.getEstagio().getAluno().getEmail());
		/*html = html.replace("{{rua}}", termo.getEstagio().getAluno().getEndereco().getRua());
		html = html.replace("{{numeroEndereco}}", String.valueOf(termo.getEstagio().getAluno().getEndereco().getNumero()));
		html = html.replace("{{complemento}}", termo.getEstagio().getAluno().getEndereco().getComplemento());
		html = html.replace("{{cidade}}", termo.getEstagio().getAluno().getEndereco().getCidade());
		html = html.replace("{{uf}}", termo.getEstagio().getAluno().getEndereco().getUf());
		html = html.replace("{{cep}}", termo.getEstagio().getAluno().getEndereco().getCep());*/
		html = html.replace("{{rua}}", "Rua X");
		html = html.replace("{{numeroEndereco}}", "0");
		html = html.replace("{{complemento}}", "Casa 2");
		html = html.replace("{{cidade}}", "Curitiba");
		html = html.replace("{{uf}}", "Paraná");
		html = html.replace("{{cep}}", "81810-481");
		
		html = html.replace("{{curso}}", termo.getEstagio().getAluno().getCurso().getNome());
		
		html = html.replace("{{matricula}}", termo.getEstagio().getAluno().getMatricula());
		html = html.replace("{{nivel}}", "4º Período"); // NULO!!
		html = html.replace("{{instituicao}}", "Universidade Federal do Paraná");
		
		html = html.replace("{{agente}}", termo.getAgenteIntegrador().getNome());
		html = html.replace("{{apolice}}", String.valueOf(termo.getApolice().getNumero()));
		html = html.replace("{{contratante}}", termo.getContratante().getNome());
		//html = html.replace("{{coordenador}}", termo.getCoordenador().getNome());
		html = html.replace("{{coordenador}}", "Alessandro Brawerman");
		html = html.replace("{{orientador}}", termo.getOrientador().getNome());
		//html = html.replace("{{supervisor}}", termo.getPlanoAtividades().getNomeSupervisor());
		html = html.replace("{{supervisor}}", "Supervisor A");
		
		//html = html.replace("{{ajustes}}", termo.getDescricaoAjustes());
		html = html.replace("{{ajustes}}", "Ajustando o teste");
		
		
		return html;
	}
	
	public Workbook gerarExcelEstagioSeguradoraUfpr(List<Estagio> estagios) {
	    Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Estágios Seguradora UFPR");

	    // Cabeçalho
	    Row headerRow = sheet.createRow(0);
	    headerRow.createCell(0).setCellValue("ID");
	    headerRow.createCell(1).setCellValue("Nome");
	    headerRow.createCell(2).setCellValue("Período");

	    int rowNum = 1;
	    for (Estagio estagio : estagios) {
	        Row row = sheet.createRow(rowNum++);
	        row.createCell(0).setCellValue(estagio.getId());
	        row.createCell(1).setCellValue(estagio.getAluno().getNome());
	        row.createCell(2).setCellValue(estagio.getDataCriacao());
	    }

	    // Formatação
	    sheet.autoSizeColumn(0);
	    sheet.autoSizeColumn(1);
	    sheet.autoSizeColumn(2);

	    return workbook;
	}
}
