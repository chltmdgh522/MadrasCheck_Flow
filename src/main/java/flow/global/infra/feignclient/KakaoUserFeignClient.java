package flow.global.infra.feignclient;

import flow.domain.user.presentation.dto.res.OAuth2UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "KakaoUser", url = "https://kapi.kakao.com")
public interface KakaoUserFeignClient {

    @PostMapping(value = "/v2/user/me")
    OAuth2UserResponse getUserInfo(@RequestHeader("Authorization") String bearerToken);
}
