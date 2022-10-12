package dj.eventregister.models.participant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Email o tej samej nazwie już istnieje.")
public class DuplicateEmailException extends RuntimeException {
}
