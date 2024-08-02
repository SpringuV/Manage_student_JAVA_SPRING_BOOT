package vutran.my_first_project_spring_boot.management_student.Handle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vutran.my_first_project_spring_boot.management_student.Entity.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> errorCatchAll(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> errorResponseResponseEntity(Exception e){
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.REQUEST_TIMEOUT.value(), e.getMessage());
//        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(errorResponse);
//    }

}
