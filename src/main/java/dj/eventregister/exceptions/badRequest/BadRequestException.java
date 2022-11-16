package dj.eventregister.exceptions.badRequest;

import dj.eventregister.exceptions.ErrorMessage;

public class BadRequestException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public BadRequestException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

}
