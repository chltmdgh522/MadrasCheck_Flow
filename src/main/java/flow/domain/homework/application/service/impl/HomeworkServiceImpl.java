package flow.domain.homework.application.service.impl;

import flow.domain.homework.application.service.HomeworkService;
import flow.domain.homework.domain.entity.ExtensionType;
import flow.domain.homework.domain.entity.Homework;
import flow.domain.homework.domain.repository.HomeworkRepository;
import flow.domain.homework.presentation.dto.req.CustomReq;
import flow.domain.homework.presentation.dto.req.FixedReq;
import flow.domain.homework.presentation.dto.res.CustomRes;
import flow.domain.user.domain.entity.User;
import flow.global.exception.FlowException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;

    @Override
    public void FixedReq(FixedReq fixedReq) {
        Optional<Homework> findHomework = homeworkRepository.findById(fixedReq.id());
        Homework homework = findHomework.get();
        if (homework == null) {
            throw new FlowException(HttpStatus.FORBIDDEN, "정보를 못찾음");
        }
        // true 값이면 true로 업데이트, false면 false로 업데이트
        homework.updateSelect(fixedReq.selected());
    }

    @Override
    public CustomRes createCustom(CustomReq customReq, User user) {
        boolean flag = homeworkRepository.existsByUserAndExtension(user, customReq.extension());
        CustomRes custom;
        if (!flag) {
            Homework homework = Homework.builder()
                    .user(user)
                    .extension(customReq.extension())
                    .type(ExtensionType.CUSTOM)
                    .selected(false)
                    .build();

            homeworkRepository.save(homework);

            custom = CustomRes.of("성공");
        } else {
            custom = CustomRes.of("실패");
        }
        return custom;
    }

    @Override
    public void deleteCustom(Long id) {
        Optional<Homework> findHomework = homeworkRepository.findById(id);
        Homework homework = findHomework.get();
        if (homework == null) {
            throw new FlowException(HttpStatus.FORBIDDEN, "정보를 못찾음");
        }

        homeworkRepository.delete(homework);
    }

    @Override
    public List<Homework> fixedList(User user) {
        return homeworkRepository.findFixedExtensionsByUser(user);

    }

    @Override
    public List<Homework> customList(User user) {

        return homeworkRepository.findCustomExtensionsByUser(user);
    }

}
