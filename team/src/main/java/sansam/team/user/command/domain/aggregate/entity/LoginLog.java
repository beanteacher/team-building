package sansam.team.user.command.domain.aggregate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import sansam.team.user.command.domain.aggregate.LoginType;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_login_log")
public class LoginLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_log_seq")
    private long logSeq;

    @Column(name = "user_seq")
    private long userSeq;

    @Column(name = "login_log_code")
    private LoginType loginCode;

    @Column(name = "login_log_ip")
    private String loginIp;

    @Column(name = "login_log_agent")
    private long loginAgent;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "login_log_reg_date", updatable = false)
    private LocalDateTime regDate;

}
