package flow.domain.user.presentation.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public record OAuth2UserResponse(
        Long id,

        @JsonProperty("connected_at")
        String connectedAt,

        Map<String, Object> properties,

        @JsonProperty("kakao_account")
        Map<String, Object> kakaoAccount
) {
        // 이메일 추출
        public String email() {
                return kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
        }

        // 닉네임 추출
        public String nickname() {
                return properties != null ? (String) properties.get("nickname") : null;
        }

        // 프로필 이미지 추출
        public String profileImage() {
                return properties != null ? (String) properties.get("profile_image") : null;
        }

        // 카카오 ID를 String으로
        public String kakaoId() {
                return String.valueOf(id);
        }
}