
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
	
	dependencyManagement {
		imports {
			mavenBom 'org.springframework.security:spring-security-bom:6.2.3'
		}
	}
	
	dependencies {
		implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'

	// Thymeleaf Layout Dialect
	implementation 'nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect'

	// spring-security
	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation "org.springframework.security:spring-security-web"
	implementation "org.springframework.security:spring-security-config"

	// Bean Validation
	implementation 'org.springframework.boot:spring-boot-starter-validation'

	// 스프링 시큐리티 6
	implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6'

	/* JWT */
	implementation 'com.auth0:java-jwt:4.4.0'

	// 이메일 인증
	implementation 'org.springframework.boot:spring-boot-starter-mail'
	// javax mail
	// https://mvnrepository.com/artifact/com.sun.mail/jakarta.mail
	implementation group: 'com.sun.mail', name: 'jakarta.mail', version: '2.0.1'

	//Oauth2 로그인
	implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'


	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.7'

	// QueryDSL
	implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
	annotationProcessor "com.querydsl:querydsl-apt:5.0.0:jakarta"
	annotationProcessor "jakarta.annotation:jakarta.annotation-api"
	annotationProcessor "jakarta.persistence:jakarta.persistence-api"

	// ModelMapper
	implementation 'org.modelmapper:modelmapper:3.2.0'


	// Spring-boot-devtools
	developmentOnly 'org.springframework.boot:spring-boot-devtools'


	runtimeOnly 'com.h2database:h2'
	runtimeOnly 'com.mysql:mysql-connector-j'

	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	testAnnotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.security:spring-security-test:6.2.2'

	}
	
	tasks.named('test') {
		useJUnitPlatform()
	}







	
## 성능 개선

1.JPA ( default_batch_fetch_size )

![스크린샷 2024-04-11 183757](https://github.com/rudqls007/toy/assets/111556581/992044fa-e2d4-4238-a4aa-32eca7bf1613)

OrderService 클래스에 구현한 getOrderList() 메소드에서 for문을 순회하면서 order.getOrderItems()를 호출할 때마다 orders 리스트의 사이즈 만큼의 쿼리문이 실행됨.
만약 사이즈가 1000이었다면 1000번의 쿼리문이 더 실행되는 것임.

![스크린샷 2024-04-11 183402](https://github.com/rudqls007/toy/assets/111556581/7c7412fa-2af3-4f60-8914-62ca0f881101)

현재 order_id에 하나의 주문 번호가 조건으로 설정되는 것을 볼 수 있고, 만약 orders의 주문 아이디를 "where order_id in (1, 2, 3, 4, 5)"
in 쿼리로 한번에 조회할 수 있다면 1000개의 쿼리를 하나의 쿼리로 조회할 수 있음.

![image](https://github.com/rudqls007/toy/assets/111556581/a13d706e-91ac-4f71-a021-46e563970ef5)

default_batch_fetch_size 옵션으로 조회 쿼리를 지정한 사이즈 만큼 한 번에 조회할 수 있음.

![스크린샷 2024-04-11 183543](https://github.com/rudqls007/toy/assets/111556581/7110f756-256d-446a-a824-c6c15373320b)


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

⛔ Name for argument of type [java.lang.String] not specified, and parameter name information not available via reflection. Ensure that the compiler uses the '-parameters' flag

1. 문제 발생
- Name for argument of type [java.lang.String] not specified, and parameter name information not available via reflection. Ensure that the compiler uses the '-parameters' flag

2. 문제 원인
- 매개 변수 인식 문제

3. 문제 해결 시도
- 컴파일 시점에 -parameters 옵션 적용 (해결되지 않음.)
  
	1. IntelliJ IDEA에서 File -> Settings를 연다. (Mac은 IntelliJ IDEA -> Settings)
	2. Build, Execution, Deployment → Compiler → Java Compiler로 이동한다.
	3. Additional command line parameters라는 항목에 다음을 추가한다. - parameters
	4. out 폴더를 삭제하고 다시 실행한다. 꼭 out 폴더를 삭제해야 다시 컴파일이 일어난다.

- Gradle 빌드(해결되지 않음.)

4. 해결 방법
- @RequestParam 이름을 생략하지 않고 적어주었더니 해결 완료.

⛔ [Querydsl] Attempt to recreate a file for type

![image](https://github.com/rudqls007/toy/assets/111556581/f34abe79-af2f-4818-a8f4-add8e2e135e4)

1. 문제 발생
- [Querydsl] Attempt to recreate a file for type

2. 문제 원인
- Q클래스 파일 중복 에러

3. 문제 해결 시도
- [Querydsl] Attempt to recreate a file for type 에 대한 구글링
- gradle -> build -> clean -> bulid (해결되지 않음.)
- Settings -> Build, Execution, Deployment -> Build Tools -> Gradle(해결되지 않음.)

![image](https://github.com/rudqls007/toy/assets/111556581/3203fa5d-05a9-4645-9a89-9b89af8b8408)

![image](https://github.com/rudqls007/toy/assets/111556581/6d0c64d6-6cf6-4239-9617-48a0eeda9ddf)

Gradle - > IntelliJ IDEA 변경


4. 해결 방법

![image](https://github.com/rudqls007/toy/assets/111556581/ff14304a-1cc3-4454-9e01-0703f4a225c4)

- Q클래스 저장 경로인 build 아래에 있는 파일 삭제 후 삭제된 상태에서 프로젝트 실행하면 해결됨 !


## 기능


### 소셜 로그인 (구글)
 
 - Spring Security
 - OAuth2 인증 방식 사용

웹 보안 설정 업데이트:

이제는 WebSecurityConfigurerAdapter가 더 이상 권장되지 않으며, 대신 
SecurityFilterChain을 빈으로 등록하는 방식으로 변경되었음.

커스텀 OAuth2UserService 구현:

기존의 DefaultOAuth2UserService를 확장하여 새로운 커스텀 OAuth2UserService를 구현하였 
고, 이를 통해 UserDetails 메서드를 사용하여 실제 OAuth2User를 구현함.

보안 설정 업데이트:

위의 내용들을 반영하여 SecurityConfig를 설정함.

회원 로드 기능 구현:

MemberService에서는 loadUserByUsername 메서드를 구현하여 회원을 찾을 수 있도록 함.

컨트롤러 업데이트:

MemberController에서는 UserDetails 형식으로 반환하는 경우는 폼 로그인이며, OAuth2 로그 
인의 경우는 OAuth2User 타입으로 반환함.


![제목 없는 동영상 - Clipchamp로 제작](https://github.com/rudqls007/toy/assets/111556581/ef16af14-7691-4f61-a47d-a44512bd5a61)

   
![image](https://github.com/rudqls007/toy/assets/111556581/a34366a9-588b-4307-8b99-82b034f94f4f)

