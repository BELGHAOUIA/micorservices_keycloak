package or.sid.billingservice;

import or.sid.billingservice.config.FeignClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@Import(FeignClientConfig.class)
@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication{
	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}

}