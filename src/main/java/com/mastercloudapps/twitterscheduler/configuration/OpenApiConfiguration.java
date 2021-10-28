package com.mastercloudapps.twitterscheduler.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {

	@Bean
	public OpenAPI customOpenAPI() {

		return new OpenAPI()
				.components(new Components())
				.info(new Info()
						.title("Twitter Scheduler API")
						.description("Twitter Scheduler TFM of URJC MasterCloudApps 2020-2021")
						.contact(new Contact()
								.name("David Rojo")
								.url("https://davidrojo.eu")));
	}
}
