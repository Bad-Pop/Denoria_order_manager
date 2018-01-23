package fr.denoria.client.space.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("PUT", "DELETE", "UPDATE", "OPTIONS", "POST", "GET", "DELETE")
                .allowedHeaders("z-pseudo", "z-token", "z-user")
//                .exposedHeaders("header1", "header2")
                .allowCredentials(false).maxAge(3600);
    }
}
