package flow.domain.user.presentation.controller;

import flow.domain.user.application.service.UserService;
import flow.domain.user.domain.entity.User;
import flow.global.config.session.SessionConst;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "OAuth2 로그인 API", description = "카카오 소셜 로그인 및 로그아웃 처리")
public class UserController {

    private final UserService userService;
    @GetMapping("/login")
    @Operation(summary = "카카오 로그인 링크 요청", description = "카카오 OAuth2 로그인 링크를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 링크 반환 성공")
    public ResponseEntity<String> loginPage() {
        return ResponseEntity.status(200).body(userService.getLoginLink());
    }

    @GetMapping("/callback")
    @Operation(summary = "카카오 로그인 콜백", description = "카카오 OAuth2 인증 성공 후 호출되는 콜백 처리")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공 시 callback 페이지 반환"),
            @ApiResponse(responseCode = "500", description = "로그인 처리 중 서버 오류")
    })
    public String loginPageCallback(
            @Parameter(description = "카카오에서 받은 인가 코드", example = "abc123~~~~~~") @RequestParam("code") String code,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws IOException {

        User user = userService.login(code, response, request);
        if (user == null) {
            model.addAttribute("message", "로그인에 실패했습니다. 다시 시도해주세요.");
            model.addAttribute("success", false);
        } else {
            model.addAttribute("message", "로그인 성공! 환영합니다.");
            model.addAttribute("success", true);
        }

        return "oauth/callback";
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "세션을 무효화하고 로그아웃합니다.")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공 시 메인 페이지 리다이렉트")
    public String logout(
            @Parameter(hidden = true) HttpSession httpSession) {
        userService.logout(httpSession);
        return "redirect:/";
    }
}
