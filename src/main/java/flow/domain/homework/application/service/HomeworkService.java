package flow.domain.homework.application.service;

import flow.domain.homework.domain.entity.Homework;
import flow.domain.homework.presentation.dto.req.CustomReq;
import flow.domain.homework.presentation.dto.req.FixedReq;
import flow.domain.homework.presentation.dto.res.CustomRes;
import flow.domain.user.domain.entity.User;

import java.util.List;

public interface HomeworkService {

    void FixedReq(FixedReq fixedReq);

    CustomRes createCustom(CustomReq customReq, User suer);

    void deleteCustom(Long id);

    List<Homework> fixedList(User user);

    List<Homework> customList(User user);
}
