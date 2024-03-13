
## 🔥 Stacks 🔥

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
-  

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

	plugin팅

## 페이지 별 기능
