package ru.develgame.selfeducation.cascade.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AlbumForbiddenException extends RuntimeException {
    public AlbumForbiddenException(String message) {
        super(message);
    }
}
