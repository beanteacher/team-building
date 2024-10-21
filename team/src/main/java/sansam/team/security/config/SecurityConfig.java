package sansam.team.security.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sansam.team.security.filter.CustomAuthenticationFilter;
import sansam.team.security.filter.JWTFilter;
import sansam.team.security.handler.JwtAccessDeniedHandler;
import sansam.team.security.handler.JwtAuthenticationEntryPoint;
import sansam.team.security.handler.LoginFailureHandler;
import sansam.team.security.handler.LoginSuccessHandler;
import sansam.team.security.util.JWTUtil;
import sansam.team.security.util.SecurityUtil;
import sansam.team.user.command.application.service.UserService;
import sansam.team.user.query.service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityUtil securityUtil;
    private final JWTUtil jwtUtil;
    private final Environment env;
    private final UserDetailServiceImpl userDetailService;
    private final UserService userService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .requestMatchers(
                                        "/",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/login",
                                        "/api/v1/user/join",
                                        "/ws/chat/**"
                                ).permitAll()
                                .requestMatchers("/api/v1/admin/**")
                                .hasAnyAuthority("MANAGER", "SUB_MANAGER")
                                .requestMatchers(
                                        "/api/v1/**"
                                ).authenticated()
                                .anyRequest().permitAll()
                )
                // JWT token 방식 사용
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                // 인증, 인가 관련 Exception Handler 설정
                .exceptionHandling(exception -> {
                    exception.accessDeniedHandler(new JwtAccessDeniedHandler());
                    exception.authenticationEntryPoint(new JwtAuthenticationEntryPoint());
                });

        return http.build();
    }

    private Filter getAuthenticationFilter() {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        customAuthenticationFilter.setAuthenticationManager(getAuthenticationManager());
        customAuthenticationFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(env, userService));
        customAuthenticationFilter.setAuthenticationFailureHandler(new LoginFailureHandler());
        return customAuthenticationFilter;
    }

    private AuthenticationManager getAuthenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(securityUtil.bCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailService);
        return new ProviderManager(provider);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers( "/error", "/error/*", "/img/**", "/favicon.ico", "/resources/**");
    }
}

