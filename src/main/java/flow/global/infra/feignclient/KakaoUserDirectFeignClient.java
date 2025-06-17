package flow.global.infra.feignclient;

import flow.domain.user.presentation.dto.res.OAuth2UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "KakaoUserDirect",
        url = "https://kapi.kakao.com"
)
public interface KakaoUserDirectFeignClient {

    @PostMapping(value = "/v2/user/me")
    OAuth2UserResponse getUserInfoByCode(
            @RequestParam("code") String code,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri
    );
}