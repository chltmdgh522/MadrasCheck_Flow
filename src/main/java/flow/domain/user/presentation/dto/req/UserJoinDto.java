package flow.domain.user.presentation.dto.req;


import java.time.LocalDateTime;

public record UserJoinDto(

        String email,
        String username,
        String password,
        String name,
        String profile,
        String phoneNumber,
        LocalDateTime createdAt

) {
}
