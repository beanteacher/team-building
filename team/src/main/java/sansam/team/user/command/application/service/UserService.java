package sansam.team.user.command.application.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import sansam.team.user.command.application.dto.LoginLogDTO;
import sansam.team.user.command.application.dto.UserDTO;
import sansam.team.user.command.application.dto.UserUpdateRequestDTO;
import sansam.team.user.command.domain.aggregate.LoginType;
import sansam.team.user.command.domain.aggregate.entity.LoginLog;
import sansam.team.user.command.domain.aggregate.entity.User;
import sansam.team.user.command.domain.repository.LoginLogRepository;
import sansam.team.user.command.domain.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LoginLogRepository loginLogRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public boolean joinProcess(UserDTO.UserJoinDTO userJoinDTO) {
        userJoinDTO.changePasswordEncoder(userJoinDTO.getPassword());
        User user = modelMapper.map(userJoinDTO, User.class);

        boolean isOk = true;
        try {
            userRepository.save(user);
        } catch (Exception e) {
            isOk = false;
            log.error(e.getMessage());
        }

        return isOk;
    }

    @Transactional
    public User updateUser(Long userSeq, UserUpdateRequestDTO request) {
        User user = userRepository.findById(userSeq).orElseThrow(() -> new EntityNotFoundException("can't find user"));

        // User 엔티티 업데이트
        user.modifyUser(request);

        return user;
    }

    @Transactional
    public boolean setLoginLog(Authentication authentication, HttpServletRequest request) {

        if(ObjectUtils.isEmpty(authentication)) return false;

        LoginLogDTO.LoginLogRequestDto logRequestDto = LoginLogDTO.LoginLogRequestDto.builder()
                .userSeq(Long.parseLong(authentication.getName()))
                .loginCode(LoginType.SUCCESS)
                .loginIp(getClientIP(request))
                .loginAgent("")                 //TODO ayoeng - agent 값 필요없으면 삭제하기
                .build();
        LoginLog log = loginLogRepository.save(modelMapper.map(logRequestDto, LoginLog.class));

        return ObjectUtils.isEmpty(log);
    }

    private static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        log.info("> X-FORWARDED-FOR : {}", ip);

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
            log.info("> Proxy-Client-IP : {}", ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.info(">  WL-Proxy-Client-IP : {}", ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.info("> HTTP_CLIENT_IP : {}", ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info("> HTTP_X_FORWARDED_FOR : {}", ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
            log.info("> getRemoteAddr : {}", ip);
        }
        log.info("> Result : IP Address : {}", ip);

        return ip;
    }

}
