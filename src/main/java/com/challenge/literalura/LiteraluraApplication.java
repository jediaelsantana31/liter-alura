package com.challenge.literalura;

import com.challenge.literalura.main.BookManagementApp;
import com.challenge.literalura.repository.BookRepository;
import com.challenge.literalura.service.APIRequestService;
import com.challenge.literalura.service.DataConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LiteraluraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(APIRequestService apiRequestService, DataConverter dataConverter, BookRepository bookRepository) {
		return args -> {
			BookManagementApp mainMenuService = new BookManagementApp(apiRequestService, dataConverter, bookRepository);
			mainMenuService.showMenu();
		};
	}
}
