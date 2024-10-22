package sansam.team.user.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sansam.team.user.command.domain.aggregate.LoginType;

@Getter @Setter
@ToString
public class AdminLoginLogDTO {

    private long logSeq;

    private long userSeq;

    private LoginType loginCode;

    private String loginIp;

    private String userName;

    private String userId;

    private String userAuth;

    private String userStatus;

    private String startDate;

    private String endDate;

    public static class RequestLoginLogDTO {
        private LoginType loginCode;

        private String userName;

        private String userId;

        private String userAuth;

        private String userStatus;

        private String startDate;

        private String endDate;
    }

}
