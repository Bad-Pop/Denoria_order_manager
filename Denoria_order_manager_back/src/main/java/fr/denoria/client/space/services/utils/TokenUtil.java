package fr.denoria.client.space.services.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenUtil {

    public String tokenGenerator() {
        return UUID.randomUUID().toString();
    }

}
