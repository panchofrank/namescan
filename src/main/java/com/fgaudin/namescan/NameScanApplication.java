package com.fgaudin.namescan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NameScanApplication {

	public static void main(String[] args) {
		SpringApplication.run(NameScanApplication.class, args);
	}

	// TODO create the resource where it is used instead of here
	@Bean
	public Resource ofacResource(@Value("${namescan.ofac.file}") String ofacFilePath) {
		return new FileSystemResource(ofacFilePath);
	}
}
