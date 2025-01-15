package com.nikhil.electronic.store.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI openAPI() {
		
		return new OpenAPI()
				.info(new Info()
						.title("Electronic Store API")
						.description("This is Electronic Store APIs developed by Mr.Nikhil Pawar")
						.version("1.0")
						.contact(new Contact().name("Nikhil").email("nikhilkpawar11@gmail.com").url("nikhil.com"))
						.license(new License().name("SpringBoot"))
						)
				.externalDocs(new ExternalDocumentation().url("https://www.instagram.com/n_i_k_h_i_l_11.0").description("This is external url {My Instagram Id !!} !!")
						);
		
	}

}
