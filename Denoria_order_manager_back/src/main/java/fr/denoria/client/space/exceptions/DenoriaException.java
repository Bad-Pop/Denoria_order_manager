package fr.denoria.client.space.exceptions;

public abstract class DenoriaException extends Exception {

    public DenoriaException() {
        super();
    }

    public DenoriaException(String message) {
        super(message);
    }

    public DenoriaException(String message, Throwable cause) {
        super(message, cause);
    }

    public DenoriaException(Throwable cause) {
        super(cause);
    }
}
