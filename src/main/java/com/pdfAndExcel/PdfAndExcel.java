package com.pdfAndExcel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*@EnableConfigurationProperties(StorageProperties.class)*/
public class PdfAndExcel {

	public static void main(String[] args) {
		SpringApplication.run(PdfAndExcel.class, args);
		}
/*	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}*/
}
