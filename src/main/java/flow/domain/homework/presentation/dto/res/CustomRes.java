package flow.domain.homework.presentation.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "커스텀 생성 응답 메시지")
public record CustomRes(
        @Schema(description = "응답 메시지", example = "성공 or 실패")
        String message
) { public static CustomRes of(String message) {
    return new CustomRes(message);
}
}
