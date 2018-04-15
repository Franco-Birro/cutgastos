package com.alpatech.cutgastos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CutgastosApplication /*extends SpringBootServletInitializer*/{

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder){
		return applicationBuilder.sources(CutgastosApplication.class);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(CutgastosApplication.class, args);
	}
}
