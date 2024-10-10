package com.example.cc;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.cc.model.User;
import com.example.cc.repository.UserRepository;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class ChronoCapsuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChronoCapsuleApplication.class, args);
	}
	
	@Bean
	ApplicationRunner init(UserRepository repository) {
		return args -> {

			repository.save(new User("testuser", "Test@email.com", "testpassword"));
			repository.findAll().forEach(System.out::println);
		};
	}

}
