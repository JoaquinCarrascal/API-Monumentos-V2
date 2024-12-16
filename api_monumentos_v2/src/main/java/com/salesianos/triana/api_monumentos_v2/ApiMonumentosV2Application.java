package com.salesianos.triana.api_monumentos_v2;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ApiMonumentosV2", version= "1.0" , contact = @Contact(email= "j.carrascalfranco@gmail.com")))
public class ApiMonumentosV2Application {

	public static void main(String[] args) {
		SpringApplication.run(ApiMonumentosV2Application.class, args);
	}

}
