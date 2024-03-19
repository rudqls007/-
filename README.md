
## 타임리프

#### [🍃타임리프 기본 기능](https://www.notion.so/aaaae6d2756346a683620bbcd5c1fe0c?pvs=4)
#### [🍃타임리프 템플릿 조각 및 템플릿 레이아웃](https://www.notion.so/ab9bdd1e6f3e4e359f992b5e3f0f582a?pvs=4)
#### [🍃타임리프 스프링 통합 기능](https://www.notion.so/0e9a1344ba5f45468940ce56333bc4cb?pvs=4)


## 🔧 Stacks

<div align="center">
<h3>Language</h3>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<br>
<h3>Framework</h3>
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white">
<img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
<img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">
<br>
<h3>DB</h3>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<br>
<h3>Communication</h3>
<br>
<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

</div>

## ➤ Navigation
- [프로젝트 소개](#프로젝트-소개)
- [프로젝트 생성 및 개발 환경](#프로젝트-생성-및-개발-환경)
- [bulid.gradle](#bulid)
- [개발 기간 및 작업 관리](#개발-기간-및-작업-관리)
- [트러블 슈팅](#트러블-슈팅)
- [페이지 별 기능](#페이지-별-기능)

## 프로젝트 소개
- SpringBoot와 JPA를 통해 이커머스 쇼핑 웹 사이트를 개발합니다.
- 기존 HTML코드를 변경하지 않고 경제적으로 유지보수 하기 쉬운 Thymeleaf를 사용해 페이지를 구현합니다.
- 스프링 시큐리티를 이용하여 회원 가입 및 로그인을 구현하고 관리자 페이지에 접근 관한을 부여하는 서비스를 제공합니다.

## 프로젝트 생성 및 개발 환경
- 'https://start.spring.io/' 프로젝트 생성
    - SpringBoot `3.2.3`
    - Gradle Groovy `7.6.1`
    - java `17`
    - Dependencies
        - WEB : `Spring Web`
        - SQL : `Spring Data JPA`  `H2 Database` `MySQL`
        - DEVELOPER TOOLS : `Lombok`
        - SECURITY : `Spring Security`
        - I/O : `Validation`
        - External Library : `com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.7`
        - TEMPLATE ENGINES : `Thymeleaf`, `Thymeleaf Extras Springsecurity5`
- IDE : IntelliJ
- DB
   - H2 `jdbc:h2:tcp://localhost/~/shop`
   - MySQL `jdbc:mysql://localhost:3306/shop?serverTimezone=UTC`

## bulid

	plugins {
	id 'java'
	id 'org.springframework.boot' version '3.2.3'
	id 'io.spring.dependency-management' version '1.1.4'
	}
	
	group = 'toy'
	version = '0.0.1-SNAPSHOT'
	
	java {
		sourceCompatibility = '17'
	}
	
	configurations {
		compileOnly {
			extendsFrom annotationProcessor
		}
	}
	
	repositories {
		mavenCentral()
	}
	
	dependencies {
		implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'

	// Thymeleaf Layout Dialect
	implementation 'nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect'

	// spring-security
	implementation 'org.springframework.boot:spring-boot-starter-security'

	// Bean Validation
	implementation 'org.springframework.boot:spring-boot-starter-validation'


	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.7'

	// QueryDSL
	implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
	annotationProcessor "com.querydsl:querydsl-apt:5.0.0:jakarta"
	annotationProcessor "jakarta.annotation:jakarta.annotation-api"
	annotationProcessor "jakarta.persistence:jakarta.persistence-api"

	// Spring-boot-devtools
	developmentOnly 'org.springframework.boot:spring-boot-devtools'



	runtimeOnly 'com.h2database:h2'
	runtimeOnly 'com.mysql:mysql-connector-j'

	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	}
	
	tasks.named('test') {
		useJUnitPlatform()
	}





	
## 개발 기간 

- 2024-03-12 ing ~

## 트러블 슈팅

⛔ WebSecurityConfigurationAdater 상속 오류

1. 문제 발생
- WebSecurityConfigurationAdater 상속 오류
SpringBoot 3.2.3 버전에서 Spring Security의 WebSecurityConfigurationAdater를 SecurityConfig 클래스에서 상속 받으려고 할 때 오류가 발생

2. 문제 원인
- WebSecurityConfigurerAdapter 유형 Deprecated
Spring 공식 홈페이지에서 Spring Security 5.7.1 이상 또는 Spring Boot 2.7.0 이상부터는 사용되지 않는다고 함.

3. 문제 해결 시도
- WebSecurityConfigurerAdapter 에 대한 구글링 및 Spring Security 공식 문서 참고

4. 해결 방법
- Before
  
	  @Configuration
		public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		    @Override
		    protected void configure(HttpSecurity http) throws Exception {
			http
			    .authorizeHttpRequests((authz) -> authz
				.anyRequest().authenticated()
			    )
			    .httpBasic(withDefaults());
		    }

- After

  
		package toy.project.config;
		
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;
		import org.springframework.security.config.annotation.web.builders.HttpSecurity;
		import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
		import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
		import org.springframework.security.crypto.password.PasswordEncoder;
		import org.springframework.security.web.SecurityFilterChain;
		import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
		
		@Configuration
		@EnableWebSecurity
		public class SecurityConfig  {
	
		    @Bean
		    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		        http.formLogin()
		                .loginPage("/members/login")
		                .defaultSuccessUrl("/")
		                .usernameParameter("email")
		                .failureUrl("/members/login/error")
		                .and()
		                .logout()
		                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
		                .logoutSuccessUrl("/")
		        ;
		
		        http.authorizeRequests()
		            저장했을 경우, 데이터베이스가 해킹 당하면 고객의 회원 정보가 그대로 노출됨.
		    *  이를 해결하기 위해 BCryptPasswordEncoder의 해시 함수를 이용해서 비밀번호를 암호화하여 저장하고 @Bean으로 등록 */
		    @Bean
		    public PasswordEncoder passwordEncoder() {
		        return new BCryptPasswordEncoder();
		    }
		}

변경 전 방식은 상속을 받아 메서드를 오버라이딩해서 설정하고 클래스 내부에 설정 정보를 저장하는 반면에,
변경 후 방식은 모든 것들을 Bean으로 등록해서 스프링 컨테이너가 직접 관리할 수 있도록 변경이 되었음.

⛔ SecurityConfig -> SecurityFilterChain http.formLogin(), and(), logout() is deprecated and marked for removal

1. 문제 발생
- http.formLogin(), and(), logout() is deprecated and marked for removal

2. 문제 원인
- 6.1 버전부터 Before 문법은 사용할 수 없다고 함.

3. 문제 해결 시도

- Spring Security 공식 문서 참고 ( https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/form.html )
- SecurityFilterChain 구글링

4. 해결 방법

- Before
		
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage("/members/login")
			.defaultSuccessUrl("/")
			.usernameParameter("email")
			.failureUrl("/members/login/error")
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
			.logoutSuccessUrl("/");
		}

- After
  
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		
		http.formLogin((formLogin) -> formLogin
			.usernameParameter("loginId")
			.failureUrl("/members/login/error")
			.loginPage("/member/login")
			.defaultSuccessUrl("/"))
			.logout((logout) -> logout
			.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
			.logoutSuccessUrl("/"));
		
		return http.build();

  		}


## 페이지 별 기능
