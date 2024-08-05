package com.fiap.posTech.parquimetro;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "API Parquimetro",
					 version = "1.0",
		             description = "FIAP - POSTECH - Tech Challenge - Parquimetro")
)
@EnableScheduling
public class ParquimetroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParquimetroApplication.class, args);
	}

}
