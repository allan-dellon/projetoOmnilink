package br.com.omnilink;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Desafio Técnico - Projeto CRUD Omnilink
 * @author Allan Dellon
 * e-mail: allandrmorais@gmail.com
 * 
 * Classe principal para execução do projeto
 */

@SpringBootApplication
public class ProjetoOmmilinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoOmmilinkApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
