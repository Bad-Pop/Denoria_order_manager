package fr.denoria.client.space.models.dto;

public class Error {

    private String error;

    private String stackTrace;

    public Error() {
    }

    public Error(String error, String stackTrace) {
        this.error = error;
        this.stackTrace = stackTrace;
    }

    public Error(String error, String stackTrace, String errorCode) {
        this.error = error;
        this.stackTrace = stackTrace;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}
