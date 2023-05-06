package dj.eventregister.models.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "This category name already exist.")
public class DuplicateCategoryNameException extends RuntimeException {
}