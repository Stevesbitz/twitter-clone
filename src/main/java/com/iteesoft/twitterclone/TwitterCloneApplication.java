package com.iteesoft.twitterclone;

import com.iteesoft.twitterclone.model.User;
import com.iteesoft.twitterclone.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TwitterCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterCloneApplication.class, args);
    }

	@Bean
    CommandLineRunner run(UserRepository userRepo) {
		return args -> {
            userRepo.save(new User("Reece James", "rc24", "reecy@blues.com", "1001"));
            userRepo.save(new User("Mason Mount", "mm19", "moneymase@blues.com", "0001"));
            userRepo.save(new User("Bernardo Silva", "bs12", "benny@city.com", "0011"));
            userRepo.save(new User( "Thierry Henry", "igwe14", "th14@gunners.com", "0111"));
		};
	}
}
