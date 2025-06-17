package flow.domain.homework.presentation.dto.res;

import flow.domain.user.presentation.dto.res.SucessLoginRes;

public record CustomRes(String message
) { public static CustomRes of(String message) {
    return new CustomRes(message);
}
}
