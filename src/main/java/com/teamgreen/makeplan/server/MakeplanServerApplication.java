package com.teamgreen.makeplan.server;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;


@EnableJpaAuditing
@SpringBootApplication
public class MakeplanServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MakeplanServerApplication.class, args);

	}


}
