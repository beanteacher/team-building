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
public class AdminUserInfoQueryDTO {
    /* 이미지URL 이름 아이디 닉네임 이메일 회원유형 상태 가입일
    전화번호 생년월일 성별 전공 경력일수 깃허브아이디 참여중인 프로젝트 */
    private String userProfileImg;
    private String userName;
    private String userId;
    private String userEmail;
    private RoleType userAuth;
    private StatusType userStatus;
    private LocalDateTime regDate;
    private String userGithubId;
    private String userPhone;
    private String userGender;
    private String userBirthDate;
    private String userMajor;
    private Long userCareerYears;
    private String projectBoardImgUrl;

}
