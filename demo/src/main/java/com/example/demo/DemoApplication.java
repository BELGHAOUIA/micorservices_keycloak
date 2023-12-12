package com.example.demo;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer() {
		return RepositoryRestConfigurer.withConfig(config -> {
			config.exposeIdsFor(Customer.class);
		});
	}

	@Bean
	CommandLineRunner start(CustomerRepo customerRepo) {
		return args -> {
			customerRepo.save(new Customer(null, "eni", "contact@eni.tn"));
			customerRepo.save(new Customer(null, "fst", "contact@fst.tn"));
			customerRepo.save(new Customer(null, "ensi", "contact@ensi.tn"));

			customerRepo.findAll().forEach(System.out::println);
		};
	}

}
