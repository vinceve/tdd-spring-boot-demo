package be.rombit.tdd.tabs.controllers.exceptions;

import be.rombit.tdd.tabs.services.exceptions.BusinessValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class BusinessValidationController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BusinessValidationException.class)
    public ResponseEntity handleException(BusinessValidationException exception, WebRequest request) {
        return ResponseEntity.unprocessableEntity().build();
    }
}
