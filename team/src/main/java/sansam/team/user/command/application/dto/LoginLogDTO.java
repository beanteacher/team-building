package sansam.team.user.command.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import sansam.team.user.command.domain.aggregate.LoginType;

import java.time.LocalDateTime;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginLogDTO {

    private long logSeq;

    private long userSeq;

    private LoginType loginCode;

    private String loginIp;

    private String loginAgent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    @Getter @Builder
    public static class LoginLogRequestDto {
        private long userSeq;

        private LoginType loginCode;

        private String loginIp;

        private String loginAgent;
    }

}
