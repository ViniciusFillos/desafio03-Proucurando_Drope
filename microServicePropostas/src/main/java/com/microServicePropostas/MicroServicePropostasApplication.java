package com.microServicePropostas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroServicePropostasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServicePropostasApplication.class, args);
	}

}
