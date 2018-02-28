package be.ordina.blog.config;

import be.ordina.blog.model.User;
import be.ordina.blog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

//@Configuration
public class PopulateConfig {

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {

            Arrays.asList(
                    new User("Jeroen", "Meys"),
                    new User("Admin", "Admin")).stream().forEach(userRepository::save);
        };
    }
}
