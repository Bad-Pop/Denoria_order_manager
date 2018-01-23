package fr.denoria.client.space.exceptions;

import fr.denoria.client.space.models.dto.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.NoSuchAlgorithmException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = {NoSuchAlgorithmException.class})
    public ResponseEntity handleException(Exception e) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new Error(e.getMessage(), e.toString()));
    }

    @ExceptionHandler(value = {UserException.class,
            OrderRequestException.class,
            NewsletterException.class,
            CandidatureException.class,
            AdminException.class})
    public ResponseEntity handleException(DenoriaException e) {
        return ResponseEntity
                .status(OK)
                .body(new Error(e.getMessage(), e.toString()));
    }

    @ExceptionHandler(value = {RequestException.class})
    public ResponseEntity handleException(RequestException e) {
        return ResponseEntity
                .status(OK)
                .body(new Error(e.getMessage(), e.toString()));
    }
}
