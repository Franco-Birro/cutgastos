package com.alpatech.cutgastos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CutgastosApplication /*extends SpringBootServletInitializer*/{

	@RequestMapping("/")
	String home(){
		return "Bem vindo ao cutgastos!";
	}
	public static void main(String[] args) {
		SpringApplication.run(CutgastosApplication.class, args);
	}
}
