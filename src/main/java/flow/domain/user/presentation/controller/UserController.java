package flow.domain.user.presentation.controller;

import flow.domain.user.application.service.UserService;
import flow.domain.user.domain.entity.User;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginMember) {
        if (loginMember != null) {
            return "home/no";
        } else {
            return "home/yes";
        }
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginPage() {
        return ResponseEntity.status(200).body(userService.getLoginLink());
    }

    @GetMapping("/callback")
    public String login(@RequestParam("code") String code, HttpServletResponse response,
                        HttpServletRequest request, BindingResult bindingResult) throws IOException {
        User user = userService.login(code, response);
        if (user == null) bindingResult.reject("로그인 실패했습니다");
        else {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, user);
        }
        return "redirect:/";
    }


    @PostMapping("/logout")
    public void logout(HttpSession httpSession) {
        userService.logout(httpSession);
    }
}