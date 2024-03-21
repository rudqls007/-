package toy.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import toy.project.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MemberService memberService;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin((formLogin) -> formLogin
                // 로그인 시 사용할 파라미터로 loginId 사용
                .usernameParameter("loginId")
                // 로그인 실패 시 이동할 페이지 지정
                .failureUrl("/members/login/error")
                // 로그인 페이지 설정
                .loginPage("/members/login")
                // 로그인 성공 시 메인페이지로 이동
                .defaultSuccessUrl("/"))
                .logout((logout) -> logout
                 // 로그아웃 url 설정
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                 // 로그아웃 성공 시 메인페이지로 이동
                .logoutSuccessUrl("/")
                 // 기존에 생성된 사용자 세션도 invaildateHttpSession을 통해 삭제하도록 설정
                .invalidateHttpSession(true));





        // 시큐리티 처리에 HttpServletRequest 활용
        http
                .csrf(csrf ->csrf.ignoringRequestMatchers("/mail/**", "/members/check/**"))
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                // permitAll() : 모든 사용자가 인증(로그인) 없이 해당 경로에 접근할 수 있도록 지정함.
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .requestMatchers("/", "/members/**", "/item/**", "/images/**","/favicon.ico").permitAll()
                .requestMatchers(HttpMethod.POST).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/mail/**")).permitAll()
                // /admin으로 시작하는 경로는 해당 계정이 ADMIN Role일 경우에만 접근 가능하도록 설정함.
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // 위에서 설정한 코드들의 경로를 제외한 나머지 경로들은 모두 인증을 요구하도록 설정.
                .anyRequest().authenticated());





        http.exceptionHandling(authenticationManager -> authenticationManager
                // 인증되지 않은 사용자가 리소스에 접근하였을 때 수행되는 핸들러를 등록함.
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}