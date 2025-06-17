package flow.global.exception;

import org.springframework.http.HttpStatus;

public class FlowException extends RuntimeException {

    private final HttpStatus status;

    public FlowException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
