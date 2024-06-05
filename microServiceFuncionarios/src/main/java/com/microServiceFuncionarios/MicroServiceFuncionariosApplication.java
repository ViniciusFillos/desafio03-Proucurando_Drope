package com.microServiceFuncionarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
public class MicroServiceFuncionariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceFuncionariosApplication.class, args);
	}

}
