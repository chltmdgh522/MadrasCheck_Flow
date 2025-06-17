package flow.domain.user.presentation.dto.res;

public record KakaoTokenResponse(
        String access_token,
        String token_type,
        String refresh_token,
        int expires_in,
        String scope
) {}