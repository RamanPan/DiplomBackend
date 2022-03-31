package ru.ramanpan.petroprimoweb.exceptions;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthException extends AuthenticationException {
    private HttpStatus httpStatus;
    public JwtAuthException(String message) {
        super(message);
    }
    public JwtAuthException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public JwtAuthException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
    }
}
