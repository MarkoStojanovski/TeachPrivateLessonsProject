package spring.learnteachsubject.web.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import spring.learnteachsubject.model.exceptions.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    @ResponseBody
//    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
//        String errorMessage = "Invalid value for parameter '" + ex.getName() + "'. Expected a value of type '"
//                + ex.getRequiredType().getSimpleName() + "'.";
//        return ResponseEntity
//                .badRequest()
//                .body("{\"error\": \"" + errorMessage + "\"}");
//    }

}



