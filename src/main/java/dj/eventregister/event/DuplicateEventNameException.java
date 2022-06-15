package dj.eventregister.event;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Event o takiej nazwie już istnieje")
public class DuplicateEventNameException extends RuntimeException {
}