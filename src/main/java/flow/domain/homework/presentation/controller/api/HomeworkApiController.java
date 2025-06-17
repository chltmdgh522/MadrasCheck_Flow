package flow.domain.homework.presentation.controller.api;

import flow.domain.homework.application.service.HomeworkService;
import flow.domain.homework.presentation.dto.res.CustomReq;
import flow.domain.homework.presentation.dto.res.FixedReq;
import flow.domain.user.domain.entity.User;
import flow.global.config.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/homework")
@RequiredArgsConstructor
@Slf4j
public class HomeworkApiController {

    private final HomeworkService homeworkService;


    @PatchMapping
    public void updateFixedHomework(@ModelAttribute FixedReq fixedReq,
                                    @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                    User loginMember){

    }
    @PostMapping
    public void createCustomHomework(@ModelAttribute CustomReq customReq,
                                     @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                         User loginMember){

    }

    @DeleteMapping
    public void deleteCustomHomework(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                     User loginMember){

    }

}
