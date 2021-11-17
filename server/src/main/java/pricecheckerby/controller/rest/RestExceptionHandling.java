package pricecheckerby.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pricecheckerby.exception.DomainNotFoundException;
import pricecheckerby.exception.InvalidTokenException;

import java.util.NoSuchElementException;

@ControllerAdvice(basePackages = {"pricecheckerby.controller.rest"})
@Slf4j
public class RestExceptionHandling {
    @ExceptionHandler({
            DomainNotFoundException.class,
            NoSuchElementException.class,
            InvalidTokenException.class
    })
    ResponseEntity<String> domainNotFoundResponse(Exception e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler({Exception.class})
    ResponseEntity<String> exception(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
