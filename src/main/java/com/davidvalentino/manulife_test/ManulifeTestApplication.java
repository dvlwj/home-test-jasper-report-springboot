package com.davidvalentino.manulife_test;

import com.davidvalentino.manulife_test.controller.ReportController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ManulifeTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManulifeTestApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ApplicationContext ctx) {
		return args -> {
			ReportController reportController = ctx.getBean(ReportController.class);
			reportController.generateReport();
		};
	}
}
