package fr.denoria.client.space;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("fr.denoria.client.space")
@EnableJpaRepositories(basePackages = "fr.denoria.client.space")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
