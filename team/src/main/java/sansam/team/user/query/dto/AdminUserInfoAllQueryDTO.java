package sansam.team.user.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sansam.team.common.aggregate.RoleType;
import sansam.team.user.command.domain.aggregate.StatusType;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserInfoAllQueryDTO {

    /* 회원번호 이름 아이디 닉네임 회원유형 상태 가입일 이메일 핸드폰 번호 */
    private Long userSeq;
    private String userName;
    private String userId;
    private String userNickname;
    private RoleType userAuth;
    private StatusType userStatus;
    private LocalDateTime regDate;
    private String userEmail;
    private String userPhone;
}
