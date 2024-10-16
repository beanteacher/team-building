package sansam.team.user.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sansam.team.exception.CustomException;
import sansam.team.exception.ErrorCodeType;
import sansam.team.user.query.dto.CustomUserDTO;
import sansam.team.user.query.dto.UserQueryDTO;
import sansam.team.user.query.mapper.UserQueryMapper;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserQueryMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // TODO 아영 - 비밀번호도 확인하기
        UserQueryDTO.LoginResponseDTO user = userMapper.findByUserId(userId)
                .filter(pw -> pw.getUserPassword()!=null)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_LOGIN_NOT_FOUND));

        return new CustomUserDTO(
                String.valueOf(user.getUserSeq()),
                user.getUserPassword(),
                user.getAuthorities(),
                user.getUserSeq(),
                user.getUserId(),
                user.getUserAuth()
        );
    }

}
