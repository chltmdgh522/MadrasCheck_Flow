package flow.domain.user.presentation.controller;

import flow.domain.user.application.service.UserService;
import flow.domain.user.domain.entity.User;
import flow.domain.user.presentation.dto.req.UserLoginDto;
import flow.domain.user.presentation.dto.res.SucessLoginRes;
import flow.global.config.session.SessionConst;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;


    @GetMapping("/login")
    public ResponseEntity<String> loginPage() {
        return ResponseEntity.status(200).body(userService.getLoginLink());
    }


    @GetMapping("/callback")
    public String loginPageCallback(@RequestParam("code") String code,
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
    public void logout(HttpSession httpSession) {
        userService.logout(httpSession);
    }
}