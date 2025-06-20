package flow.domain.homework.presentation.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "커스텀 생성 응답 메시지")
public record CustomRes(
        @Schema(description = "응답 메시지", example = "이미 중복된 확장자입니다.")
        String message,

        @Schema(description = "반환 여부", example = "성공이면 true 아니면 false")
        boolean flag
) {
    public static CustomRes of(String message, boolean flag) {
        return new CustomRes(message, flag);
    }
}
