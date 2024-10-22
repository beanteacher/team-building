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
import org.springframework.web.multipart.MultipartFile;
import sansam.team.common.s3.FileUploadUtil;
import sansam.team.user.command.application.dto.LoginLogDTO;
import sansam.team.user.command.application.dto.UserDTO;
import sansam.team.user.command.application.dto.UserUpdateRequestDTO;
import sansam.team.user.command.domain.aggregate.LoginType;
import sansam.team.user.command.domain.aggregate.entity.LoginLog;
import sansam.team.user.command.domain.aggregate.entity.User;
import sansam.team.user.command.domain.repository.LoginLogRepository;
import sansam.team.user.command.domain.repository.UserRepository;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LoginLogRepository loginLogRepository;
    private final FileUploadUtil fileUploadUtil;
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
    public User updateUser(Long userSeq, UserUpdateRequestDTO request, MultipartFile userProfileImg) throws IOException {
        User user = userRepository.findById(userSeq).orElseThrow(() -> new EntityNotFoundException("can't find user"));

        if(userProfileImg != null && !userProfileImg.isEmpty()){
            String imagUrl = fileUploadUtil.uploadFile(userProfileImg);
            request.setUserProfileImg(imagUrl);
        }else {
            request.setUserProfileImg(user.getUserProfileImg());
        }

        // User 엔티티 업데이트
        modelMapper.map(request, user);

        return user;
    }

    @Transactional
    public boolean setLoginSuccessLog(Authentication authentication, HttpServletRequest request) {

        if(ObjectUtils.isEmpty(authentication)) return false;

        // TODO 아영 - user_status = 'active'가 아닐 경우 로그인 실패
        // TODO 아영 - fail이 연속 5회일경우 회원 상태 정지 후 로그인 상태도 정지 (로그인 시도 횟수를 초과하였습니다. 관리자에게 문의해 주세요.)

        LoginLogDTO.LoginLogRequestDto logRequestDto = LoginLogDTO.LoginLogRequestDto.builder()
                .userSeq(Long.parseLong(authentication.getName()))
                .loginCode(LoginType.SUCCESS)
                .loginIp(getClientIP(request))
                .build();
        LoginLog log = loginLogRepository.save(modelMapper.map(logRequestDto, LoginLog.class));

        return ObjectUtils.isEmpty(log);
    }

    @Transactional
    public void setLoginErrorLog(Authentication authentication, HttpServletRequest request) {

        LoginLogDTO.LoginLogRequestDto logRequestDto = LoginLogDTO.LoginLogRequestDto.builder()
                .userSeq(Long.parseLong(authentication.getName()))
                .loginCode(LoginType.FAIL)
                .loginIp(getClientIP(request))
                .build();

        loginLogRepository.save(modelMapper.map(logRequestDto, LoginLog.class));
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
