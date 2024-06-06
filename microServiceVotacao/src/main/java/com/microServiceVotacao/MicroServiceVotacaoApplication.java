package com.microServiceVotacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroServiceVotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceVotacaoApplication.class, args);
	}

}
