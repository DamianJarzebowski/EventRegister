package dj.eventregister.models.eventrecord;

public class InvalidEventRecordException extends RuntimeException {
    public InvalidEventRecordException(String message) {
        super(message);
    }
}
