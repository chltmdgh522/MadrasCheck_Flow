package flow.global.exception;


import babbuddy.domain.user.infra.exception.UserNotFoundException;
import babbuddy.global.infra.exception.auth.BabbuddyAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(FlowException.class)
    public ResponseEntity<ErrorResponse> handleBabbuddyException(FlowException e) {
        log.error("BabbuddyException caught: {}", e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(ErrorResponse.of(e));
    }

}
