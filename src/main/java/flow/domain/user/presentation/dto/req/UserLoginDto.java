package flow.domain.user.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;

public class UserLoginDto{
    @NotBlank(message = "code는 필수입니다")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}