package sansam.team.user.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sansam.team.user.command.domain.aggregate.LoginType;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class AdminLoginLogDTO {

    private long logSeq;

    private long userSeq;

    private LoginType loginCode;

    private String loginIp;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    private String userName;

    private String userId;

    private String userAuth;

    private String userStatus;

    private String startDate;

    private String endDate;

}
