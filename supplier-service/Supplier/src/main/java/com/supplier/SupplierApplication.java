package com.supplier;

import com.supplier.entities.Supplier;
import com.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SupplierApplication implements CommandLineRunner {
	private final SupplierRepository supplierRepository;

	public static void main(String[] args) {
		SpringApplication.run(SupplierApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		supplierRepository.save(new Supplier(1L,"JBOSS","jboss@gmail.com"));
		supplierRepository.save(new Supplier(2L,"Tomcat","tomcat@gmail.com"));
		supplierRepository.save(new Supplier(3L,"Springboot","springboot@gmail.com"));

	}
}
