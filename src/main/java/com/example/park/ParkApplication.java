package com.example.park;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.park.domain.mapper")
@SpringBootApplication
public class ParkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkApplication.class, args);
	}

}
