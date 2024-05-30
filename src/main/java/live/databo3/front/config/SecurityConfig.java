package live.databo3.front.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.front.adaptor.AuthAdaptor;
import live.databo3.front.auth.filter.ExceptionHandlingFilter;
import live.databo3.front.auth.filter.JwtAuthenticationFilter;
import live.databo3.front.auth.filter.TokenRenewalFilter;
import live.databo3.front.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
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
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers(
                        "/logout",
                        "/assets/**",
                        "/error"
                ).permitAll()
                .antMatchers(
                        "/login",
                        "/oauth/**",
                        "/register",
                        "/search-password",
                        "/id-check",
                        "/email/**"
                ).hasRole("ANONYMOUS")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/owner/**").hasRole("OWNER")
                .antMatchers("/viewer/**").hasRole("VIEWER")
                .anyRequest().authenticated()
                .and().addFilterAfter(jwtAuthenticationFilter(), FilterSecurityInterceptor.class);;
        http
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .logout().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

        http.exceptionHandling().accessDeniedPage("/errors/403");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(tokenRenewalFilter(null), JwtAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlingFilter(), TokenRenewalFilter.class);
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
    public ExceptionHandlingFilter exceptionHandlingFilter() {
        return new ExceptionHandlingFilter(excludePath());
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, objectMapper,excludePath());
    }

    @Bean
    public TokenRenewalFilter tokenRenewalFilter(AuthAdaptor authAdaptor) {
        return new TokenRenewalFilter(authAdaptor, jwtUtil, objectMapper,excludePath());
    }

    @Bean
    public String[] excludePath() {
        return new String[]{
                "/logout",
                "/pre-login/.*",
                "/oauth/.*",
                "/static/.*",
                "/errors/.*",
                "/error",
                "/assets/.*",
                "/favicon.ico/*"
        };
    }
}
