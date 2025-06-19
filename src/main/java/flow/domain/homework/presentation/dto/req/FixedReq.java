package flow.domain.homework.presentation.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "고정 확장자 수정 요청 DTO")
public record FixedReq(
        @Schema(description = "수정할 고정 확장자 ID", example = "3")
        Long id,

        @Schema(description = "수정할 고정 확장자 Selected", example = "true")
        boolean selected
) {
}
