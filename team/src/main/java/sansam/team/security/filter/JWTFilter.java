package sansam.team.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sansam.team.security.util.JWTUtil;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /* 요청 헤더에 담긴 토큰의 유효성 판별 및 인증 객체 저장 */
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if(jwtUtil.validateToken(token)) {
                Authentication authentication = jwtUtil.getAuthentication(token);
                /* 인증이 완료 되었고 이후 인증 필터는 건너 뛰게 된다. */
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        /* 위의 if문에 걸리지 않아 Authentication 객체가 설정 되지 않으면 다음 필터(인증 필터)가 실행 된다. */
        filterChain.doFilter(request, response);
    }

}
