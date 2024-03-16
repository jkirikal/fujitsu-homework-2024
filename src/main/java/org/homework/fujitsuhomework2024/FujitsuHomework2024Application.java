package org.homework.fujitsuhomework2024;

import org.homework.fujitsuhomework2024.service.WeatherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FujitsuHomework2024Application {

	public static void main(String[] args) {
		SpringApplication.run(FujitsuHomework2024Application.class, args);
	}

}
