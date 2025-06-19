package flow.domain.homework.presentation.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "커스텀 확장자 삭제 요청 DTO")
public record CustomDeleteReq(
        @Schema(description = "삭제할 커스텀 확장자 ID", example = "3")
        Long id
) {
}
