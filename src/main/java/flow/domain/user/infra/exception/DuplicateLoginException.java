package flow.domain.user.infra.exception;

import flow.global.exception.FlowException;
import org.springframework.http.HttpStatus;

public class DuplicateLoginException extends FlowException {
    public DuplicateLoginException() {
        super(HttpStatus.UNAUTHORIZED, "중복 로그인은 허용되지 않습니다.");
    }
}
