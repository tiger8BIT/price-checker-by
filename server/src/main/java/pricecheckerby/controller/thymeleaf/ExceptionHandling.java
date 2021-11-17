package pricecheckerby.controller.thymeleaf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = {"pricecheckerby.controller.thymeleaf"})
@Slf4j
public class ExceptionHandling {
    @ExceptionHandler({Exception.class})
    String exception(Exception e) {
        log.error(e.getMessage());
        return "some-error";
    }
}
