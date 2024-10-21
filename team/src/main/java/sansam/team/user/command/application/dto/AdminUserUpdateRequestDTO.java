package sansam.team.user.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sansam.team.common.aggregate.RoleType;
import sansam.team.user.command.domain.aggregate.StatusType;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserUpdateRequestDTO {

    private String userProfileImg;
    private String userName;
    private String userId;
    private String userEmail;
    private RoleType userAuth;
    private StatusType userStatus;
    private String userPhone;
    private String userGender;
    private String userMajor;
    private Long userCareerYears;
}
