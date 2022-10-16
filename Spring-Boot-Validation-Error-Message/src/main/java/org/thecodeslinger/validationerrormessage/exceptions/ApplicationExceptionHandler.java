package org.thecodeslinger.validationerrormessage.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thecodeslinger.validationerrormessage.dtos.ErrorMessage;

import java.util.List;

/**
 * Custom exception handle to catch and format validation exceptions.
 * <p>
 * When field errors are defined then {@link MethodArgumentNotValidException#getFieldErrors()}
 * contains the errors that were defined. Otherwise, the default message of the
 * {@link MethodArgumentNotValidException#getGlobalError()} can be used.
 */
@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessage> handleValidationError(MethodArgumentNotValidException e) {
        log.warn("{}", e.getMessage());

        List<String> messages;
        if (0 < e.getFieldErrorCount()) {
            messages = e.getFieldErrors().stream()
                    .map(this::buildMessageText)
                    .toList();
        } else {
            var error = e.getGlobalError();
            messages = List.of(error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(
                new ErrorMessage(HttpStatus.BAD_REQUEST.value(), messages));
    }

    private String buildMessageText(FieldError error) {
        if (null != error.getDefaultMessage()) {
            return "%s: %s".formatted(error.getField(), error.getDefaultMessage());
        } else {
            return "Invalid value for '%s'.".formatted(error.getField());
        }
    }
}
