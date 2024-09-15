package com.etiqa.assessment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				title = "Etiqa Customer and Product Management API",
				description = """
						This REST API allows the Etiqa team to efficiently manage customers and products. Built using Spring <b>WebFlux</b> for reactive programming, the API supports various
						operations such as creating, updating, deleting, and retrieving customer and product information.
						""",
				version = "1.0.0",
				contact = @Contact(
						name = "Roman Sarker",
						email = "mdroman601@gmail.com",
						url = "https://romantechlife.blogspot.com/"
				)		)
)
public class EtiqaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtiqaApplication.class, args);
	}

}
