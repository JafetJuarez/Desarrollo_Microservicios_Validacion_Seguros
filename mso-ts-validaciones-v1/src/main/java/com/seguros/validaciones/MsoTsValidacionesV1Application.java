package com.seguros.validaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsoTsValidacionesV1Application {

	public static void main(String[] args) {
		SpringApplication.run(MsoTsValidacionesV1Application.class, args);
	}

}
