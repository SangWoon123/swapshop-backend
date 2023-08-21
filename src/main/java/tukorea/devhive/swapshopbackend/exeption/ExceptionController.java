package tukorea.devhive.swapshopbackend.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(ReportAlreadyExistsException.class)
    public ResponseEntity<String> handleReportAlreadyExistsException(ReportAlreadyExistsException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
