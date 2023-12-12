package com.example.demo;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepo;
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
			config.exposeIdsFor(Product.class);
		});
	}

	@Bean
	CommandLineRunner start(ProductRepo productRepo) {
		return args -> {
			productRepo.save(new Product(null, "pc hp", 1051.55));
			productRepo.save(new Product(null, "pc asus", 1051.55));
			productRepo.save(new Product(null, "pc lenovo", 1051.55));
			productRepo.findAll().forEach(System.out::println);
		};
	}

}
