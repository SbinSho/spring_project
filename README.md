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

DB 유저 아이디 및 비밀번호를 properties 파일을 만들어 설정 파일에서 사용하기 위함 ( git에 프로젝트 올리시 보안이 유리, 편리함 )

* 파일 업로드 처리

![fileUpload](https://github.com/SbinSho/spring_project/blob/master/img/root-context(fileUpload).png)

게시판 작성시 사용자가 원하는 파일을 업로드 할 수 있도록 도와주는 multipartResolver를 사용하기 위함


#### datasource-context.xml

* datasource 설정

![datasource](https://github.com/SbinSho/spring_project/blob/master/img/datasource-context(datasource).png)


Mybatis를 이용하여 쿼리를 편리하게 작성 및 관리하고, DB 연동을 위한 datasource 설정 파일

* transactionManager 설정

![transaction](https://github.com/SbinSho/spring_project/blob/master/img/datasource-context(transaction).png)

두 개의 DB 작업을 하나의 작업 단위로 묶기 위해 트랜잭션 매니저를 사용, 다이내믹 프록시를 적용할 인터페이스가 없기 때문에, 클래스 프록시 모드 사용을 위해 proxy-target-class="true" 설정

        
## 마치며
### 프로젝트의 부족한점
- 공통 관심 기능을 분리하여 반복되는 부분 줄이지 못한 점, 예를 들어 RSA 암호화 알고리즘으로 받은 암호문을 복호화 처리 하는 부분
- 처음 프로젝트 생성 후 권한 확인 및 로그인 처리를 Interceptor로 처리하였는데, spring security 뒤늦게 알고 적용하게 되어, 비효율적으로 Interceptor와 spring security를 같이 사용하게 됨

