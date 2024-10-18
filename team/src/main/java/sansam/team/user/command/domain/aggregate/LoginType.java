package sansam.team.user.command.domain.aggregate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginType {
    SUCCESS("SUCCESS", "로그인 성공"),
    FAIL("FAIL", "로그인 실패"),
    BAN("BAN", "로그인 정지");

    private final String code;
    private final String value;

}
