## 개요 :wave:

* 프로젝트 명 : spring_project
<br><br>
* 일정 : 2021.04 ~ 2021.05
<br><br>
* 프로젝트 목적
    - SpringFramework를 이해하고 습득하기 위해, 홈 페이지 개발 기초에 필요한 회원가입, 로그인, 게시판, 파일 업로드, 웹 사이트 보안 처리를 구현 한 웹 사이트 입니다.

## 프로젝트 정보 :punch:
### 개발 환경
* language : HTML5 + CSS3 , javascript, java
* framework : spring, bootstrap
* library : jQuery
* server : Apache Tomcat 9.0
* DB : MySQL 5.6
* Tool : Eclipse

### 실행 환경
* java8
* eclipse-oxygen

### 회원 시스템 & 게시판 시스템

> 회원 시스템

![회원_시스템](https://github.com/SbinSho/spring_project/blob/master/img/유스케이스(회원).png)

> 게시판 시스템

![게시판_시스템](https://github.com/SbinSho/spring_project/blob/master/img/유스케이스(게시판).png)

* member : 회원 관련 테이블
* board : 게시판 관련 테이블
* board_reply : 댓글 관련 테이블
* board_file : 파일 업로드 관련 테이블


### ERD

![ERD](https://github.com/SbinSho/spring_project/blob/master/img/spring_project_ERD.png)


### 구현 기능

* 로그인, 로그아웃, 회원 권한 확인 기능 ( spring-security 사용 )
* 비밀번호 DB 저장 시 암호화 처리 ( bcryptPasswordEncoder 사용 )
* 회원가입시 이메일 인증 처리 ( JavaMailSenderImpl 사용 ), 파일 업로드 처리 ( multipartResolver 사용 )
* 회원가입, 회원수정, 회원탈퇴 기능 
* 게시판 기능 ( 작성, 수정, 삭제, Interceptor를 이용해서 권한 확인 ), 게시판 댓글 기능
* 클라이언트와 서버 HTTP로 통신 간 평문을 암호화 하기 위해, RSA 암호 알고리즘 사용
* 클라이언트 응답 요청만을 위한 @RestController 구현

### Spring 환경설정 ( xml 방식 )

* root-context.xml
    - datasource-context.xml : Mybatis를 이용한 DB 연동 및 처리를 위한 설정 파일
    - spring-security.xml : spring security 사용을 위한 설정 파일
    - mail-context.mxl : 회원가입에 필요한 메일 인증 처리를 위한 설정 파일
    - message-context.xml : messageSource 사용을 위한 설정 파일
    
---

#### root-context.xml
    
* properties 파일 사용

![properties](https://github.com/SbinSho/spring_project/blob/master/img/root-context(properties).png)

DB 유저 아이디 및 비밀번호를 properties 파일을 만들어 설정 파일에서 사용하기 위한 설정 ( git에 프로젝트 올리시 보안에 유리, 편리함 )

* 파일 업로드 처리

![fileUpload](https://github.com/SbinSho/spring_project/blob/master/img/root-context(fileUpload).png)

게시판 작성시 사용자 원하는 파일을 업로드 할 수 있도록 도와주는 multipartResolver를 사용하기 위한 설정

---

#### datasource-context.xml

* datasource 설정

![datasource](https://github.com/SbinSho/spring_project/blob/master/img/datasource-context(datasource).png)


Mybatis를 이용하여 쿼리를 편리하게 작성 및 관리하고, DB 연동을 위한 datasource 설정 파일, 테이블 별로 mybatis mapper 파일 작성

* transactionManager 설정

![transaction](https://github.com/SbinSho/spring_project/blob/master/img/datasource-context(transaction).png)

두 개의 DB 작업을 하나의 작업 단위로 묶기 위해 트랜잭션 매니저를 사용 ( 예를 들어 게시판 글 조회 및 조회수 증가 ), 다이내믹 프록시를 적용할 인터페이스가 없기 때문에, 클래스 프록시 모드 사용을 위해 proxy-target-class="true" 설정

---

#### spring-security.xml

* spring security 사용을 위해 필터 등록 ( web.xml )

![web](https://github.com/SbinSho/spring_project/blob/master/img/spring-security(web).png)

spring security는 여러가지 인증 절차를 거쳐 로그인을 허용하게 되는데, 여러가지 인증 절차를 수행할 springSecurityFilterChain을 등록한다.
필터는 dispatcherservlet으로 가기전 적용됨, Interceptor와 하는일은 비슷하나 적용시기가 다르다.

---

* 비밀번호 암호화 처리를 위한 bcryptPasswordEncoder 설정

![pass](https://github.com/SbinSho/spring_project/blob/master/img/spring-security(passencoder).png)

---

* http 설정

![http](https://github.com/SbinSho/spring_project/blob/master/img/spring-security(http).png)

* http : spring security 4.x 버전부터 use-expressions 기본 속성 값이 false에서 true로 변경됨에 따라 생략.
* intercpet-url 
    - 초기 프로젝트 작성 시 Interceptor를 이용해 url 접근 권한을 체크 했었고, 실습 목적으로 spring security를 도입 후 회원 로그인, 회원가입, 회원 수정 부분만 접근 권한 체크함.
    - 이외에 나머지 모든 부분은 권한 없이 접근 가능함( 나머지 접근 권한 처리는 Interceptor로 처리함 )
* form-login
    - username-parameter : 로그인 form 태그의 아이디 혹은 이메일 name 속성 값 ( default: username )
    - password-parameter : 로그인 form 태그의 비밀번호 name 속성 값 ( default: password )
    - authentication-success-handler-ref : 로그인 성공 후 핸들러 사용 [loginSuccessHandler 코드 확인](https://github.com/SbinSho/spring_project/blob/master/src/main/java/com/goCamping/authentication/LoginSuccessHandler.java)
    - authentication-failure-handler-ref : 로그인 실패 후 핸들러 사용 [loginFailureHandler 코드 확인](https://github.com/SbinSho/spring_project/blob/master/src/main/java/com/goCamping/authentication/LoginFailureHandler.java)
    
* logout : 로그아웃 처리 관련 설정
* frame-options : 네이버 에디터 사용을 위한 옵션 설정
* access-denied-handler : 인증되지 않은 권한으로 URL 요청 및 에러 발생시 홈 화면으로 이동
* session-management
    - invalid-session-url : 세션이 끊겼을 경우 홈 화면으로 이동
    - session-fixation-protection : 세션 고정 공격 방어를 위해 설정 ( 세션 아이디 변경하고, 세션의 내용은 그대로 유지하는 방식 )
    
___ 

* authentication-manager 설정
    
![http](https://github.com/SbinSho/spring_project/blob/master/img/spring-security(authentication).png)

* authentication-provider
    - 디폴트 provider 사용하지 않고, 커스텀 provider 사용
    - DB에 저장되어 있는 암호화된 비밀번호를 비교하기 위해 사용
    
[provider 코드 확인](https://github.com/SbinSho/spring_project/blob/master/src/main/java/com/goCamping/authentication/CustomAuthenticationProvider.java)
        
## 마치며
### 프로젝트의 부족한점
- 공통 관심 기능을 분리하여 반복되는 부분 줄이지 못한 점, 예를 들어 RSA 암호화 알고리즘으로 받은 암호문을 복호화 처리 하는 부분
- 처음 프로젝트 생성 후 권한 확인 및 로그인 처리를 Interceptor로 처리하였는데, spring security 뒤늦게 알고 적용하게 되어, 비효율적으로 Interceptor와 spring security를 같이 사용하게 됨

