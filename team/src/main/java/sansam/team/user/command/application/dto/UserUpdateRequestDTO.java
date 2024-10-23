package sansam.team.user.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import sansam.team.common.aggregate.YnType;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserUpdateRequestDTO {

    private String userName;
    private String userMajor;
    private YnType projectMemberMajorYn;
    private String userNickname;
    private String userPhone;
    private String userEmail;
    private String userBirthDate;
    private String userGithubId;
    private String userGender;
    private String userProfileImg;
    private Long userCareerYears;
    private Long userCareerMonths;

}

