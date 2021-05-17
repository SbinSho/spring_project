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

![회원_시스템](https://github.com/SbinSho/JSPModel1_Project/blob/master/img/회원시스템.png)

> 게시판 시스템

![게시판_시스템](https://github.com/SbinSho/JSPModel1_Project/blob/master/img/게시판시스템.png)


### ERD

![ERD](https://github.com/SbinSho/JSPModel1_Project/blob/master/img/BUSANTOUR_ERD.png)


### 구현 기능
* 로그인, 로그아웃 기능
* 회원가입, 회원수정, 회원탈퇴 기능
* 일반 게시판, 이미지 게시판, 파일 업로드 게시판 기능
* 게시판 댓글 기능
* 게시판 키워드 검색기능
* 메인 페이지 슬라이드 기능
* jsoup 라이브러리 이용한 부산관광 공지사항 가져오기 기능
* 카카오 우편 API이용하여 편리한 주소입력 기능
* JavaMail API를 사용한 기능
    - 비밀번호 찾기
    - 메일 전송

### 핵심 기능
#### 1. jsoup 라이브러리를 이용한 부산관광 홈 페이지의 최근 10개의 공지사항 가져오기 기능
 - 개발 동기
    - 사용자에게 필요한 기능을 추가하기 위해 고민하던 중, jsoup 라이브러리를 알게되어 웹 크롤링을 상업적 목적이 아닌 실습 목적으로 구현
 - 기능 설명
    - 부산관광 홈페이지에 공지사항 게시판의 내용을 웹 크롤링하여 BsanTour홈페이지로 가져오고, 사용자가 원하는 게시판의 글을 클릭시 부산관광 홈페이지의 공지사항 내용을 확인할수 있는 기능

> 부산관광 최근 공지사항

![jsoupimg](https://github.com/SbinSho/JSPModel1_Project/blob/master/img/jsoupimg.png)


> Code ( Jsoup 사용 준비 )

* /WebContent/notice/notice.jsp

![jsoupCode1](https://github.com/SbinSho/JSPModel1_Project/blob/master/img/JsoupCode1.png)

* Code 설명 ( jsoup 사용 준비 )
    * 프로젝트 WEB-INF -> lib 폴더에 jsoup.1.11.3.jar 파일 추가, 크롤링을 원하는 페이지 주소를 변수 link에 입력함
    * Jsoup의 Document 객체를 선언하고, connect("웹 데이터를 가져올 주소").get()를 이용하여 크롤링할 페이지의 정보를 가져옴
    * 크롤링할 페이지에서 가져오고 싶은 HTML 요소를 선택해 Elements 객체를 선언하고, select("원하는 요소")를 사용해 요소를 선택

> Code ( Jsoup 사용 부분 )

* /WebContent/notice/notice.jsp

![jsoupCode2](https://github.com/SbinSho/JSPModel1_Project/blob/master/img/JsoupCode2.png)

* Code 설명 ( jsoup 사용 부분 )
    * 크롤링 페이지의 a태그에 연결된 주소는 현재 홈 페이지(BsanTour)에서 그대로 사용하면 링크주소가 맞지 않음
        * 그러므로 a 태그의 링크주소를 변경하기 위해, 변수 str에 크롤링 페이지의 a링크 주소를 가져와 입력
    * jsoup 사용 준비에서 선택한 table_tr의 첫 번째 td태그를 선택하여 Elements 객체인 tTag를 만들고, 현재 페이지에 맞춰 변수 str을 이용해 a 태그의 링크 주소 변환
    * 모든 작업이 끝난 tTag를 out.println()로 웹에 출력

> [ Github에 저장된 Jsoup 코드 확인하기 ](https://github.com/SbinSho/JSPModel1_Project/blob/master/WebContent/notice/notice.jsp)    
    
#### 2. JavaMail API를 이용한 메일 전송 기능
- 개발 동기
    - 관리자에게 문의 메일이나 사용자가 페이지 이용 중 메일 서비스가 필요할 경우, 간단한 메일정도는 바로 보낼수 있는 메일 기능이 있으면 편리하지 않을까하는 생각에 메일 기능을 구현
- 기능 설명
    - JavaMail API를 사용하여, 사용자가 원하는 메일 주소에 메일을 전송할수 있게 만든 기능
    - 사용자는 자기 메일을 입력하고, 현재 입력한 메일의 비밀번호 등 필요한 입력값을 작성하면 메일 전송 가능

> 관리자 및 사용자간 메일 전송

![adim_mail](https://github.com/SbinSho/JSPModel1_Project/blob/master/img/admin_mail.png)       
      
      
> Code ( 입력받은 데이터로 JavaMail API 사용 )

* /WebContent/mail/sendMail.jsp

![mail](https://github.com/SbinSho/JSPModel1_Project/blob/master/img/mail.png)

* Code 설명 ( 입력받은 데이터로 JavaMail API 사용 )
    * 메일 전송시 필요한 설정들을 Properties 클래스를 작성하기 위해 객체를 선언, 그리고 메일을 전송하기 위해 필요한 설정을 객체에 입력
    * SMTP 프로토콜을 이용하여 사용할 메일의 아이디와 비밀번호로 Authenticator 객체를 생성
    * 메일과 관련된 작업을 처리하기 위해 새로운 세션을 생성하고, 메시지 작성을 위한 MimeMessag 객체 선언 후( 생성한 세션 ses를 객체 선언시 사용  ) 보내는 사람, 받는 사람, 제목, 내용과 같이 메일과 관련된 내용을 지정
    * 다 작성된 MimeMessage를 Transport의 send("작성이 완료된 메시지 객체")를 이용해 메일을 전송

> [Github에 저장된 메일전송 코드 확인하기](https://github.com/SbinSho/JSPModel1_Project/blob/master/WebContent/mail/sendMail.jsp)
        
#### 3. JavaMail API를 이용한 비밀번호 찾기를 구현
- 개발 동기
    - 사용자들이 서비스 이용하다 비밀번호를 잊어버리는 경우 생각하여 JavaMail API를 통해 비밀번호 찾기 기능을 구현
- 기능 설명
    - 사용자가 비밀번호를 찾기 위해서는 JavaMail API 이용한 메일 인증코드를 받아 인증을 해야함
    - 사용자에게 메일인증 코드를 JavaMail API를 이용하여 전송하고, 사용자는 메일을 확인하여 전송된 인증코드를 입력하면 현재 비밀번호를 확인 가능
    - 이 떄 인증코드 비교는 처음 생성된 랜덤값을 현재 세션에 저장한 후 비교하는 방법을 사용
        
        
> 비밀번호 찾기 기능

![mail_service](https://github.com/SbinSho/JSPModel1_Project/blob/master/img/%EB%B9%84%EB%B0%80%EB%B2%88%ED%98%B8%EC%B0%BE%EA%B8%B0.PNG)
    

> Code ( Random 함수 사용 )

* /WebContent/member/passwdMail.jsp

![key](https://github.com/SbinSho/JSPModel1_Project/blob/master/img/key.png)


![key_mail](https://github.com/SbinSho/JSPModel1_Project/blob/master/img/key_mail.png)

* Code 설명 ( Random 함수 사용 )
    * 난수 발생을 위한 Random 클래스를 생성
        * 생성된 클래스에 중복된 난수값 발생 방지를 위해, currentTimeMillis()를 이용해 현재시각을 seed 값으로 지정
    * 반복문을 이용하여 5번 반복동안, 26미만의 난수 발생 후 숫자 97을 더하고, char 형변환 후 문자열 key에 입함
    * 메일 전송 작업은 위의 '입력받은 데이터로 JavaMail API 사용'과 동일함
       
> [Github에 저장된 패스워드 체크 코드 확인하기](https://github.com/SbinSho/JSPModel1_Project/blob/master/WebContent/member/passwdMail.jsp)    
    
#### 4. 카카오 우편 API
- 개발 동기
    - 사용자가 주소를 입력할때 편리하게 주소를 입력할 수 있도록, 카카오 우편API를 이용한 집 주소 입력 기능을 구현
- 기능 설명
    - 본인의 집 지번 주소 또는 도로명 주소를 검색하고, 조건에 일치하는 주소를 선택하면 자동으로 주소가 웹 페이지에 입력됨
    - 입력된 주소에서 상세주소만 따로 작성하면 편리하게 집주소를 입력가능


> 우편번호 검색 기능

![kakaoAPI](https://github.com/SbinSho/JSPModel1_Project/blob/master/img/kakao.png)
    
> Code ( API 사용 )

* /WebContent/js/join.js

![kakaoAPI](https://github.com/SbinSho/JSPModel1_Project/blob/master/img/kakaoAPI.png)
      
> [Github에 저장된 KaKao API 코드 확인하기](https://github.com/SbinSho/JSPModel1_Project/blob/master/WebContent/js/join.js)
        
## 마치며
### 프로젝트의 부족한점
- java, DB, css 등 모든 부분에서 불필요하게 중복된 코드가 많이 삽입됨
- 실제 서비스 단계에서 사용자가 보기엔, 전체적인 디자인이 너무 심플하다. ( 너무 옜날 페이지 같음.. )
- 데이터 보안에 관련된 공부가 많이 부족, 사용자 데이터를 안전하게 처리하기 위한 공부 필수적으로 필요!

