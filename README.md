
## 🔥 Stacks 🔥

<div align="center">
<h3>Framework</h3>
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<br>
<h3>DB</h3>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<br>
<h3>Communication</h3>
<br>
<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

</div>

## Navigation
- [프로젝트 생성 및 개발 환경](#프로젝트-생성-및-개발-환경)
- [bulid.gradle](#bulid)


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
- DB : H2 `jdbc:h2:tcp://localhost/~/shop`
       MySQL `jdbc:mysql://localhost:3306/shop?serverTimezone=UTC`

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
		implementation 'org.springframework.boot:spring-boot-starter-web'
		implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	
		// QueryDSL
		implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
		annotationProcessor "com.querydsl:querydsl-apt:5.0.0:jakarta"
		annotationProcessor "jakarta.annotation:jakarta.annotation-api"
		annotationProcessor "jakarta.persistence:jakarta.persistence-api"
	
		runtimeOnly 'com.h2database:h2'
		runtimeOnly 'com.mysql:mysql-connector-j'
	
		compileOnly 'org.projectlombok:lombok'
		annotationProcessor 'org.projectlombok:lombok'
		testImplementation 'org.springframework.boot:spring-boot-starter-test'
	}
	
	tasks.named('test') {
		useJUnitPlatform()
	}


