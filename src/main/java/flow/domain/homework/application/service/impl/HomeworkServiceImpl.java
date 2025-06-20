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

import java.util.ArrayList;
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
        String rawExtension = customReq.extension();
        if (rawExtension == null) {
            return CustomRes.of("확장자가 비어있습니다.",false);
        }

        // 0. 20자 제한

        if (rawExtension.length() > 20) {
            return CustomRes.of("20자내로 작성해주세요.",false);
        }
        // 1. 빈 문자열 검사
        if (rawExtension.isEmpty()) {
            return CustomRes.of("확장자가 비어있습니다.",false);
        }

        // 2. 앞뒤/중간 공백 제거
        String cleanedExt = rawExtension.trim().replaceAll("\\s+", "");

        // 3. 정규식 검사: 영문 대소문자 + 숫자만 허용
        if (!cleanedExt.matches("^[a-zA-Z0-9]+$")) {
            log.info(cleanedExt);
            return CustomRes.of("확장자는 영문자와 숫자만 입력할 수 있습니다.",false);
        }

        // 4. 등록 개수 제한 (최대 10개)
        List<Homework> customExtensions = homeworkRepository.findCustomExtensionsByUser(user);
        if (customExtensions.size() >= 10) {
            return CustomRes.of("커스텀 확장자는 최대 10개까지 등록할 수 있습니다.",false);
        }

        // 5. 중복 검사
        boolean alreadyExists = homeworkRepository.existsByUserAndExtension(user, cleanedExt);
        if (alreadyExists) {
            return CustomRes.of("이미 등록된 확장자입니다.",false);
        }

        // 저장
        Homework homework = Homework.builder()
                .user(user)
                .extension(cleanedExt)
                .type(ExtensionType.CUSTOM)
                .selected(false)
                .build();

        homeworkRepository.save(homework);
        return CustomRes.of("성공",true);
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

    @Override
    public List<String> getAllExtensionsForUser(User user) {
        List<String> extension = new ArrayList<>();
        List<Homework> fixedHomework = fixedList(user);
        List<Homework> customHomework = customList(user);

        for (Homework homework : customHomework) {
            extension.add(homework.getExtension());
        }
        for (Homework homework : fixedHomework) {
            extension.add(homework.getExtension());
        }
        return extension;
    }

}
