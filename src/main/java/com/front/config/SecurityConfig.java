package com.front.config;


import com.front.auth.adaptor.AuthAdaptor;
import com.front.auth.filter.JwtAuthenticationFilter;
import com.front.auth.filter.TokenRenewalFilter;
import com.front.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * Security 설정 클래스
 *
 * @author 양현성
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests()
                .antMatchers(
                        "/login",
                        "/register",
                        "/passwordSearch",
                        "/oauth/**",
                        "/css/**",
                        "/js/**",
                        "/static/**",
                        "/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and();
        http
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .logout().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterAfter(jwtAuthenticationFilter(), SecurityContextPersistenceFilter.class);
        http.addFilterBefore(tokenRenewalFilter(null), JwtAuthenticationFilter.class);
        return http.build();
    }


    /**
     * 비밀번호 암호화, 검증에 필요한 빈
     *
     * @return BCryptPasswordEncoder 반환
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    @Bean
    public TokenRenewalFilter tokenRenewalFilter(AuthAdaptor authAdaptor) {
        return new TokenRenewalFilter(authAdaptor,jwtUtil);
    }
}
