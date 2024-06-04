package com.example.ProyectoIntegrador;

import com.example.ProyectoIntegrador.dao.BD;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoIntegradorApplication {

	public static void main(String[] args) {
		BD.crearTablas();
		SpringApplication.run(ProyectoIntegradorApplication.class, args);
	}

}
