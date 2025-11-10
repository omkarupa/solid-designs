package com.op_solutions.solid_designs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.op_solutions.solid_designs","com.op_solutions.sunsmart"})
public class SolidDesignsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolidDesignsApplication.class, args);
	}

}
