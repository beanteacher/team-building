package sansam.team.user.query.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sansam.team.common.aggregate.RoleType;
import sansam.team.common.aggregate.entity.BaseTimeEntity;
import sansam.team.security.jwt.JwtToken;
import sansam.team.user.command.domain.aggregate.StatusType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserQueryDTO extends BaseTimeEntity {

    private Long userSeq;

    private String id;

    private String name;

    private String nickname;

    private String password;

    private RoleType auth = RoleType.MEMBER;

    private String phone;

    private String email;

    private String birthDate;

    private String gender;

    private String githubId;

    private String profileImg;

    private StatusType status = StatusType.ACTIVE;

    private LocalDateTime pwdModDate;

    private LocalDateTime banDate;

    private LocalDateTime delDate;

    @Getter
    public static class LoginRequestDTO {
        private String id;
        private String pw;
    }

    @Getter
    @Setter
    public static class LoginResponseDTO implements UserDetails {
        private Long userSeq;
        private String userId;
        private String userPassword;
        private String userName;
        private RoleType userAuth;
        private JwtToken jwtToken;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(userAuth.getCode()));

            return authorities;
        }

        @Override
        public String getPassword() {
            return this.userPassword=userPassword;
        }

        @Override
        public String getUsername() {
            return this.userName=userName;
        }
    }

}
