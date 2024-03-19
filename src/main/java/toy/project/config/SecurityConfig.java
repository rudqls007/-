package toy.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import toy.project.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

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





//        http.authorizeRequests()
//                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
//                .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//        ;


        http.exceptionHandling(authenticationManager -> authenticationManager
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}