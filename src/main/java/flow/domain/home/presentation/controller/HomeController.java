package flow.domain.home.presentation.controller;

import flow.domain.user.domain.entity.User;
import flow.global.config.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                           User loginMember,
                       Model model){
        if (loginMember != null) {
            model.addAttribute("user",loginMember);
            return "home/yes";
        }else{
            return "home/no";
        }
    }
}
