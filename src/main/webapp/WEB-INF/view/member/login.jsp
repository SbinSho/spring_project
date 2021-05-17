<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<!-- saved from url=(0051)https://getbootstrap.com/docs/4.5/examples/sign-in/ -->
<html lang="en">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v4.1.1">
    <title>Hello</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/sign-in/">

    <!-- Bootstrap core CSS -->
	<link href="<c:url value='/css/bootstrap/bootstrap.min.css'/>" rel="stylesheet" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">


	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <!-- Favicons -->
	<link rel="apple-touch-icon" href="https://getbootstrap.com/docs/4.5/assets/img/favicons/apple-touch-icon.png" sizes="180x180">
	<link rel="icon" href="https://getbootstrap.com/docs/4.5/assets/img/favicons/favicon-32x32.png" sizes="32x32" type="image/png">
	<link rel="icon" href="https://getbootstrap.com/docs/4.5/assets/img/favicons/favicon-16x16.png" sizes="16x16" type="image/png">
	<link rel="manifest" href="https://getbootstrap.com/docs/4.5/assets/img/favicons/manifest.json">
	<link rel="mask-icon" href="https://getbootstrap.com/docs/4.5/assets/img/favicons/safari-pinned-tab.svg" color="#563d7c">
	<link rel="icon" href="https://getbootstrap.com/docs/4.5/assets/img/favicons/favicon.ico">
	<meta name="msapplication-config" content="/docs/4.5/assets/img/favicons/browserconfig.xml">
	<meta name="theme-color" content="#563d7c">

	<!-- RSA 자바스크립트 라이브러리 -->
	<script type="text/javascript" src="/js/rsa/jsbn.js"></script>
	<script type="text/javascript" src="/js/rsa/rsa.js"></script>
	<script type="text/javascript" src="/js/rsa/prng4.js"></script>
	<script type="text/javascript" src="/js/rsa/rng.js"></script>
	
    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
    <!-- Custom styles for this template -->
    <link href="<c:url value='/css/signin.css'/>" rel="stylesheet">
  </head>
<body class="text-center">
	<form class="form-signin">
		<input type="hidden" id="RSAModulus" value="${RSAModulus}" /><!-- 서버에서 전달한값을 셋팅한다. -->
		<input type="hidden" id="RSAExponent" value="${RSAExponent}" /><!-- 서버에서 전달한값을 셋팅한다. -->
	  	<img class="mb-4" src="./Signin Template Â· Bootstrap_files/bootstrap-solid.svg" alt="" width="72" height="72">
	  	<div class="form-group">
	  		<label for="id">아이디</label>
	  		<c:choose>
	  			<c:when test="${ not empty user_id }">
					<input type="text" id="id" class="form-control" value="${ user_id }">
	  			</c:when>
	  			<c:when test="${ not empty id }">
	  				<input type="text" id="id" class="form-control" value="${ id }">
	  			</c:when>
	  			<c:otherwise>
	  				<input type="text" id="id" class="form-control">
	  			</c:otherwise>
	  		</c:choose>
			<span style="color: red"></span>
		</div>
	    <div class="form-group">
	        <label for="pwd">비밀번호</label>
			<input type="password" id="pwd" class="form-control"/>
			<span style="color: red" id="check"></span>
	    </div>
	    <div class="form-group">
	    	<c:if test="${ not empty errormsg }">
        		<p style="color : red;"> ${ errormsg } </p>
        		<c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session"/>
	    	</c:if>
	    </div>
		<div class="checkbox mb-3">
		<c:choose>
			<c:when  test="${ empty cookie_check }">
			   <label for="reid">
			      <input type="checkbox" id="reid" name="reid" /> 아이디 기억하기
			    </label>
			</c:when>
			<c:otherwise>
				<label for="reid">
			      <input type="checkbox" id="reid" name="reid" checked="checked"/> 아이디 기억하기
			    </label>
			</c:otherwise>
		</c:choose>
		</div>
	  	<button class="btn btn-lg btn-primary btn-block" type="button" onclick="form_check();">로그인</button>
	  	<p class="mt-5 mb-3 text-muted">Â© 2017-2020</p>
	</form>
	
	<form id="LoginForm" action="/member/login" method="post">
		<input type="hidden" id="user_id" name="user_id" />
		<input type="hidden" id="user_pwd" name="user_pwd" />
		<input type="hidden" id="user_reId" name="user_reId" />
		<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
	</form>
	
<script>

	var result = "${result}";
	
	if(result == "member_login_fail"){
		alert("아이디 또는 비밀번호를 확인해주세요!");
	} else if( result == "error"){
		alert("오류 발생!");
	}
	
	
	// 폼 데이터 값 확인
	function form_check() {
		var id = $("#id").val();
		var pwd = $("#pwd").val();
		var reid;
		
		if($("#reid").is(":checked")){
			reid = "true";
		} else {
			reid="false"
		}
		
		
		if( id == "" || pwd == ""){
			alert("입력되지 않은 정보가 존재합니다.");
		} else {
			submitEncryptedForm(id, pwd, reid);
		}
		
		return false;
		
	}
	
	
	function submitEncryptedForm(id, pwd, reid){
		
		// RSA 암호화 생성
		
		// 스프링 시큐리티 사용위해 잠시 주석
			
// 		var rsa = new RSAKey();
// 		if( $("#RSAModulus").val() == "" ||  $("#RSAExponent").val() == ""){
// 			location.href="/member/login";		
// 		} else {
			
// 			rsa.setPublic($("#RSAModulus").val(), $("#RSAExponent").val());
			
			// 암호화된 폼 데이터
// 			var user_id = rsa.encrypt(id);
// 			var user_pwd = rsa.encrypt(pwd);
// 			var user_reId = rsa.encrypt(reid);
			
// 			$("#user_id").val(user_id);
// 			$("#user_pwd").val(user_pwd);
// 			$("#user_reId").val(user_reId);

			$("#user_id").val(id);
			$("#user_pwd").val(pwd);
			$("#user_reId").val(reid);
			
			
			$("#LoginForm").submit();
			
// 		}
	}
	
</script>
</body>
</html>