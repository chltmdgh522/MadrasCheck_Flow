package flow.domain.homework.presentation.controller.view;

import flow.domain.homework.application.service.HomeworkService;
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
public class HomeworkViewController {

    private final HomeworkService homeworkService;

    @GetMapping("/homework")
    public String homeworkGet(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                       User loginMember,
                       Model model){
        model.addAttribute("fixedList",homeworkService.fixedList(loginMember));
        model.addAttribute("customList",homeworkService.customList(loginMember));

        return "homework/homework";
    }
}
