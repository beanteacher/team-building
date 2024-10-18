package sansam.team.security.handler;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import sansam.team.security.jwt.JwtToken;
import sansam.team.user.command.application.service.UserService;
import sansam.team.user.query.dto.CustomUserDTO;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final Environment env;

    private final UserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if(userService.setLoginSuccessLog(authentication, request)) {
            log.error("setLoginLog Error : {}", authentication);
            userService.setLoginErrorLog(authentication, request);
        }

        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("JWT_SECRET_KEY"));
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);

        CustomUserDTO userInfo = (CustomUserDTO) authentication.getPrincipal();

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())  // userSeq를 subject로 설정
                .claim("userId", userInfo.getUserId())
                .claim("auth", userInfo.getUserAuth())
                .setIssuedAt(new Date())  // iat 추가 (발행 시간)
                .setExpiration(new Date((new Date()).getTime() + 86400000))  // 만료 시간 (1일)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성 (7일 만료)
        String refreshToken = Jwts.builder()
                .setIssuedAt(new Date())  // iat 추가 (발행 시간)
                .setExpiration(new Date((new Date()).getTime() + 604800000))  // 만료 시간 (7일)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        // JWT 토큰 생성
        JwtToken jwtToken = JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        response.setHeader("token", jwtToken.getAccessToken());
    }

}
