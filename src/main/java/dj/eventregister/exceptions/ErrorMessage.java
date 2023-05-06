package dj.eventregister.exceptions;

public enum ErrorMessage {

    NOT_FOUND("Resource does not exist"),
    BAD_REQUEST("Bad request - check documentation");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
