package flow.domain.homework.application.service;

import flow.domain.homework.domain.entity.Homework;
import flow.domain.homework.presentation.dto.res.CustomReq;
import flow.domain.homework.presentation.dto.res.FixedReq;
import flow.domain.user.domain.entity.User;

import java.util.List;

public interface HomeworkService {

    void FixedReq(FixedReq fixedReq);

    void createCustom(CustomReq customReq, User suer);

    void deleteCustom(Long id);

    List<Homework> fixedList(User user);

    List<Homework> customList(User user);
}
