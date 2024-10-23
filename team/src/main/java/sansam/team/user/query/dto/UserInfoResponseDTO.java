package sansam.team.user.query.dto;

import lombok.Builder;
import lombok.Data;
import sansam.team.common.aggregate.YnType;

import java.time.LocalDateTime;

@Data
@Builder
public class UserInfoResponseDTO {
    /*조회에 프로필 이미지, 경력연수, 경력 월수, 전공여부,
     생년월일, 깃허브 아이디, 가입일, 성별 추가*/
    private Long userSeq;
    private String userId;
    private String userName;
    private String userNickname;
    private String userProfileImg;
    private String userEmail;
    private String userPhone;
    private String userMajor;
    private YnType projectMemberMajorYn;
    private Long userCareerYears;
    private Long userCareerMonths;
    private String userBirthDate;
    private String userGithubId;
    private LocalDateTime regDate;
    private String userGender;
}
