package br.ufpr.estagio.modulo;

import java.io.File;
import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.itextpdf.html2pdf.HtmlConverter;

import br.ufpr.estagio.modulo.service.GeradorDePdfService;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class PocServiceApplication {
	
	@Autowired
	private GeradorDePdfService geradorDePdfService;
	
	public static void main(String[] args) {
		SpringApplication.run(PocServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@PostConstruct
	public void init() {
	    String diretorioAtual = new File("").getAbsolutePath();
	    String diretorioDestino = diretorioAtual + "/src/main/resources/arquivos";

	    File diretorio = new File(diretorioDestino);
	    if (!diretorio.exists()) {
	        diretorio.mkdirs();
	    }
	}

}
