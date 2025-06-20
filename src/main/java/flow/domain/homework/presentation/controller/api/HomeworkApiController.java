package flow.domain.homework.presentation.controller.api;

import flow.domain.homework.application.service.HomeworkService;
import flow.domain.homework.presentation.dto.req.CustomDeleteReq;
import flow.domain.homework.presentation.dto.req.CustomReq;
import flow.domain.homework.presentation.dto.req.FixedReq;
import flow.domain.homework.presentation.dto.res.CustomRes;
import flow.domain.user.domain.entity.User;
import flow.global.config.session.SessionConst;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/homework")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "과제 확장자 차단 API", description = "고정/커스텀 확장자 과제 관리 API")
public class HomeworkApiController {

    private final HomeworkService homeworkService;


    @PatchMapping
    @Operation(summary = "고정 확장자 업데이트", description = "고정 확장자 체크 여부를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
    })
    public void updateFixedHomework(@Parameter(description = "고정 확장자 ID, selected 여부") @ModelAttribute FixedReq fixedReq) {
        homeworkService.FixedReq(fixedReq);
    }

    @PostMapping
    @Operation(summary = "커스텀 확장자 생성", description = "사용자가 직접 추가한 커스텀 확장자를 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공"),
    })
    public ResponseEntity<CustomRes> createCustomHomework(
            @Parameter(description = "커스텀 확장자 이름") @ModelAttribute CustomReq customReq,
            @Parameter(hidden = true) @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginMember) {
        return ResponseEntity.ok(homeworkService.createCustom(customReq, loginMember));
    }

    @DeleteMapping
    @Operation(summary = "커스텀 확장자 삭제", description = "등록된 커스텀 확장자를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
    })
    public void deleteCustomHomework(
            @Parameter(description = "삭제할 커스텀 확장자 ID") @ModelAttribute CustomDeleteReq customDeleteReq) {
        homeworkService.deleteCustom(customDeleteReq.id());
    }

    @GetMapping("/list")
    @Operation(summary = "확장자 목록 조회", description = "사용자의 고정 + 커스텀 확장자 목록을 조회합니다.")
    public ResponseEntity<List<String>> getRegisteredExtensions(
            @Parameter(hidden = true)
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginMember) {

        return ResponseEntity.ok(homeworkService.getAllExtensionsForUser(loginMember));
    }

}
