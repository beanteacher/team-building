package sansam.team.user.command.domain.aggregate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginType {
    SUCCESS("로그인 성공"),
    FAIL("로그인 실패"),
    BAN("로그인 정지");

    private final String value;

}
