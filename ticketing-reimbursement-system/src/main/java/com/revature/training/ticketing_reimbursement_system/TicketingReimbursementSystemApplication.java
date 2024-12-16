package com.revature.training.ticketing_reimbursement_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
// import org.springframework.boot.web.client.RestTemplateBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.stereotype.Controller;

@SpringBootApplication
@EnableJpaRepositories
public class TicketingReimbursementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketingReimbursementSystemApplication.class, args);
	}

}
