package flow.domain.user.application.service.impl;


import flow.domain.user.application.service.UserService;
import flow.domain.user.domain.entity.User;
import flow.domain.user.domain.repository.UserRepository;
import flow.domain.user.presentation.dto.res.OAuth2UserResponse;
import flow.global.infra.feignclient.KakaoUserDirectFeignClient;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final KakaoUserDirectFeignClient kakaoUserDirectFeignClient;
    private final UserRepository userRepository;
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

    public User login(String code, HttpServletResponse response) throws IOException {
        try {
            OAuth2UserResponse userResponse = kakaoUserDirectFeignClient.getUserInfoByCode(code, clientId, redirectUri);
            log.info("카카오 ID {}", userResponse.id());

            User user = findOrCreateUser(userResponse);

            log.info("로그인 성공 {}", user.getName());
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
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .id(userResponse.kakaoId())
                                .email(userResponse.email())
                                .name(userResponse.nickname())
                                .profile(userResponse.profileImage())
                                .build()
                ));
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}
