package com.fgaudin.namescan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class NameScanApplication {

	public static void main(String[] args) {
		SpringApplication.run(NameScanApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()                 .apis(RequestHandlerSelectors.basePackage("com.fgaudin.namescan"))
				.build()
				.apiInfo(metaData());

	}


	// TODO create the resource where it is used instead of here
	@Bean
	public Resource ofacResource(@Value("${namescan.ofac.file}") String ofacFilePath) {
		return new FileSystemResource(ofacFilePath);
	}


	private ApiInfo metaData() {
		ApiInfo apiInfo = new ApiInfo(
				"Name Scan REST API",
				"REST API for details of people on watch list",
				"1.0",
				"Terms of service",
				new Contact("Anonymos", "https://todo", "anonymous@todo"),
				"Apache License Version 2.0",
				"https://www.apache.org/licenses/LICENSE-2.0");
		return apiInfo;
	}
}
