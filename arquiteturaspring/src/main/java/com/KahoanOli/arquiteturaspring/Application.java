package com.KahoanOli.arquiteturaspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);

		builder.run(args);

		ConfigurableApplicationContext applicationContext = builder.context();

		ExemploValue value = applicationContext.getBean(ExemploValue.class);
		value.imprimirVariavel();
	}

}
