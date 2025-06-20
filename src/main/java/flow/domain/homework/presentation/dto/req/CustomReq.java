package flow.domain.homework.presentation.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "커스텀 확장자 생성 요청 DTO")
public record CustomReq(
        @Schema(description = "생성할 커스텀 이름", example = "pdf")
        String extension

) {
}
