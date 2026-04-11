package com.knight.simulator;

import com.knight.simulator.application.ChaosSimulatorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulatorApplication.class, args);

	}

	@Bean
	CommandLineRunner run(ChaosSimulatorService chaosService) {
		return args -> {
			System.out.println("SISTEMA ONLINE. Saldo inicial: R$400.000.000,00");
			Thread.sleep(2000);

			chaosService.initializeMarket();
		};
	}
}
