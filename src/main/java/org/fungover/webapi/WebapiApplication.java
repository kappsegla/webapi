package org.fungover.webapi;

import org.fungover.webapi.entities.User;
import org.fungover.webapi.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebapiApplication.class, args);
    }

    @Bean
    public CommandLineRunner setUp(UserRepository entityRepository, PasswordEncoder passwordEncoder){
        return (args)->{
            if (entityRepository.findByName("admin") == null){
                var user = new User();
                user.setName("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                user.setRoles("ROLE_ADMIN,CREATE_USER");
                entityRepository.save(user);
            }
        };
    }
}
