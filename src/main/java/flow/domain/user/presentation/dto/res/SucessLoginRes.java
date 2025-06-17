package flow.domain.user.presentation.dto.res;

public record SucessLoginRes(
        String message
) { public static SucessLoginRes of(String message) {
    return new SucessLoginRes(message);
}
}
