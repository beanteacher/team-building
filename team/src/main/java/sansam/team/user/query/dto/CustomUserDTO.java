package sansam.team.user.query.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import sansam.team.common.aggregate.RoleType;

import java.util.Collection;

@Getter @Setter
@ToString
public class CustomUserDTO extends User {

    private final long userSeq;

    private final String userId;

    private final RoleType userAuth;


    public CustomUserDTO(String username, String password, Collection<? extends GrantedAuthority> authorities, long userSeq, String userId, RoleType userAuth) {
        super(username, password, authorities);
        this.userSeq = Long.parseLong(username);
        this.userId = userId;
        this.userAuth = userAuth;
    }

    public CustomUserDTO(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, long userSeq, String userId, RoleType userAuth) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userSeq = Long.parseLong(username);
        this.userId = userId;
        this.userAuth = userAuth;
    }

}