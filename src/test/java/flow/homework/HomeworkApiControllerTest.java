package flow.homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import flow.domain.homework.application.service.HomeworkService;
import flow.domain.homework.presentation.controller.api.HomeworkApiController;
import flow.domain.homework.presentation.dto.req.CustomDeleteReq;
import flow.domain.homework.presentation.dto.req.CustomReq;
import flow.domain.homework.presentation.dto.req.FixedReq;
import flow.domain.homework.presentation.dto.res.CustomRes;
import flow.domain.user.domain.entity.User;
import flow.global.config.session.SessionConst;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *  HomeworkApiController의 모든 엔드포인트를 테스트
 * - /api/homework [GET, POST, PATCH, DELETE]
 * - 로그인 세션 필요 (SessionAttribute)
 * - Service는 Mock 처리
 */
@WebMvcTest(HomeworkApiController.class)
class HomeworkApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HomeworkService homeworkService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final User mockUser = User.builder()
            .id("test-id")
            .email("test@example.com")
            .name("홍길동")
            .build();

    @Test
    @DisplayName("[GET] /api/homework/list - 확장자 목록 조회")
    void getRegisteredExtensions() throws Exception {
        List<String> extensions = List.of("exe", "pdf", "xlsx");

        Mockito.when(homeworkService.getAllExtensionsForUser(any(User.class)))
                .thenReturn(extensions);

        mockMvc.perform(get("/api/homework/list")
                        .sessionAttr(SessionConst.LOGIN_MEMBER, mockUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0]").value("exe"));
    }

    @Test
    @DisplayName("[POST] /api/homework - 커스텀 확장자 등록 성공")
    void createCustomExtensionSuccess() throws Exception {
        CustomReq req = new CustomReq("xlsx");
        CustomRes res = CustomRes.of("성공", true);

        Mockito.when(homeworkService.createCustom(any(CustomReq.class), any(User.class)))
                .thenReturn(res);

        mockMvc.perform(post("/api/homework")
                        .sessionAttr(SessionConst.LOGIN_MEMBER, mockUser)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("extension", "xlsx"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.message").value("성공"));
    }

    @Test
    @DisplayName("[POST] /api/homework - 커스텀 확장자 중복 등록 실패")
    void createCustomExtensionDuplicateFail() throws Exception {
        CustomReq req = new CustomReq("exe");
        CustomRes res = CustomRes.of("이미 등록된 확장자입니다.", false);

        Mockito.when(homeworkService.createCustom(any(CustomReq.class), any(User.class)))
                .thenReturn(res);

        mockMvc.perform(post("/api/homework")
                        .sessionAttr(SessionConst.LOGIN_MEMBER, mockUser)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("extension", "exe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.message").value("이미 등록된 확장자입니다."));
    }

    @Test
    @DisplayName("[PATCH] /api/homework - 고정 확장자 선택 상태 변경")
    void updateFixedExtension() throws Exception {
        FixedReq req = new FixedReq(1L, true);

        mockMvc.perform(patch("/api/homework")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "1")
                        .param("selected", "true"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[DELETE] /api/homework - 커스텀 확장자 삭제")
    void deleteCustomExtension() throws Exception {
        CustomDeleteReq req = new CustomDeleteReq(1L);

        mockMvc.perform(delete("/api/homework")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "1"))
                .andExpect(status().isOk());
    }
}
