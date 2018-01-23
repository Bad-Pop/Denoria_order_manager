package fr.denoria.client.space.exceptions;

public class OrderRequestException extends DenoriaException {


    public OrderRequestException() {
        super();
    }

    public OrderRequestException(String message) {
        super(message);
    }

    public OrderRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderRequestException(Throwable cause) {
        super(cause);
    }
}
