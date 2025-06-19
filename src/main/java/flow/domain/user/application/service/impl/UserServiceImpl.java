package flow.domain.user.application.service.impl;


import flow.domain.homework.domain.entity.ExtensionType;
import flow.domain.homework.domain.entity.Homework;
import flow.domain.homework.domain.repository.HomeworkRepository;
import flow.domain.user.application.service.UserService;
import flow.domain.user.domain.entity.User;
import flow.domain.user.domain.repository.UserRepository;
import flow.domain.user.presentation.dto.res.KakaoTokenResponse;
import flow.domain.user.presentation.dto.res.OAuth2UserResponse;
import flow.global.config.session.SessionConst;
import flow.global.infra.feignclient.KakaoTokenFeignClient;
import flow.global.infra.feignclient.KakaoUserFeignClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final KakaoTokenFeignClient kakaoTokenFeignClient;
    private final KakaoUserFeignClient kakaoUserFeignClient;
    private final UserRepository userRepository;
    private final HomeworkRepository homeworkRepository;
    @Value("${oauth2.base-url}")

    private String baseUrl;

    @Value("${oauth2.client-id}")
    private String clientId;

    @Value("${oauth2.redirect-uri}")
    private String redirectUri;

    @Override
    public String getLoginLink() {
        return baseUrl +
                "?client_id=" +
                clientId +
                "&redirect_uri=" +
                redirectUri +
                "&response_type=code" +
                "&scope=profile_nickname,profile_image,account_email";  // 필요한 Scope를 콤마로 구분
    }

    @Override
    public User login(String code, HttpServletResponse response, HttpServletRequest request) throws IOException {
        try {
            // 1단계: access_token 요청
            KakaoTokenResponse tokenRes = kakaoTokenFeignClient.getToken(
                    "authorization_code",
                    clientId,
                    redirectUri,
                    code
            );

            String bearerToken = "Bearer " + tokenRes.access_token();

            // 2단계: 사용자 정보 요청
            OAuth2UserResponse userResponse = kakaoUserFeignClient.getUserInfo(bearerToken);

            User user = findOrCreateUser(userResponse);

            request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, user);
            return user;

        } catch (Exception e) {
            log.error("로그인 실패", e);
            return null;
        }
    }

    private User findOrCreateUser(OAuth2UserResponse userResponse) {
        return userRepository.findById(userResponse.kakaoId())
                .map(existingUser -> {
                    existingUser.updateNameAndEmailAndProfile(
                            userResponse.nickname(),
                            userResponse.email(),
                            userResponse.profileImage()
                    );
                    return userRepository.save(existingUser);
                })
                .orElseGet(() -> {
                    User newUser = userRepository.save(User.builder()
                            .id(userResponse.kakaoId())
                            .email(userResponse.email())
                            .name(userResponse.nickname())
                            .profile(userResponse.profileImage())
                            .build()
                    );

                    List<String> fixedExts = List.of("bat", "cmd", "com", "cpl", "exe", "scr", "js"); // 고정 확장자
                    List<Homework> homeworkList = fixedExts.stream()
                            .map(ext -> Homework.builder()
                                    .user(newUser)
                                    .extension(ext)
                                    .type(ExtensionType.FIXED)
                                    .selected(false)
                                    .build()
                            ).toList();

                    homeworkRepository.saveAll(homeworkList);

                    return newUser;
                });
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}
