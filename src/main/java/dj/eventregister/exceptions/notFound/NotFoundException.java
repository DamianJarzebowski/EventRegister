package dj.eventregister.exceptions.notFound;


import dj.eventregister.exceptions.ErrorMessage;

public class NotFoundException extends RuntimeException {


    private final ErrorMessage errorMessage;

    public NotFoundException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

}
