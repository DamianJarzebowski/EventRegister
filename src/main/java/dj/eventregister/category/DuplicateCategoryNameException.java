package dj.eventregister.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Kategoria o tej samej nazwie już istnieje.")
public class DuplicateCategoryNameException extends RuntimeException {
}