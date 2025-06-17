package flow.domain.homework.presentation.controller.api;

import flow.domain.homework.application.service.HomeworkService;
import flow.domain.homework.presentation.dto.req.CustomDeleteReq;
import flow.domain.homework.presentation.dto.req.CustomReq;
import flow.domain.homework.presentation.dto.req.FixedReq;
import flow.domain.homework.presentation.dto.res.CustomRes;
import flow.domain.user.domain.entity.User;
import flow.global.config.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/homework")
@RequiredArgsConstructor
@Slf4j
public class HomeworkApiController {

    private final HomeworkService homeworkService;


    @PatchMapping
    public void updateFixedHomework(@ModelAttribute FixedReq fixedReq){
        log.info(String.valueOf(fixedReq.id()));

        homeworkService.FixedReq(fixedReq);
    }
    @PostMapping
    public ResponseEntity<CustomRes> createCustomHomework(@ModelAttribute CustomReq customReq,
                                                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                         User loginMember){
        return  ResponseEntity.ok(homeworkService.createCustom(customReq,loginMember));
    }

    @DeleteMapping
    public void deleteCustomHomework(@ModelAttribute CustomDeleteReq customDeleteReq){
            homeworkService.deleteCustom(customDeleteReq.id());
    }

}
