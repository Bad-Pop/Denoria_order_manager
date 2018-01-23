package fr.denoria.client.space.exceptions;

public class NewsletterException extends DenoriaException {

    public NewsletterException() {
        super();
    }

    public NewsletterException(String message) {
        super(message);
    }

    public NewsletterException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewsletterException(Throwable cause) {
        super(cause);
    }
}
