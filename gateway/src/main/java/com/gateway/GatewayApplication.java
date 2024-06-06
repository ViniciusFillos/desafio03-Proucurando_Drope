package com.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder
				.routes()
 msFuncionarios/feature-exceptions
				.route(r -> r.path("/api/v1/funcionarios/**").uri("lb://msFuncionarios"))
				.route(r -> r.path("/api/v1/propostas/**").uri("lb://msPropostas"))
				.route(r -> r.path("/api/v1/votacao/**").uri("lb://msVotacao"))
				.build();
	}

}
