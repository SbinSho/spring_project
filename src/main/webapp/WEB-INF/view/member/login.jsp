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
	  	<img class="mb-4" src="./Signin Template Â· Bootstrap_files/bootstrap-solid.svg" alt="" width="72" height="72">
	  	<div class="form-group">
	  		<label for="id">아이디</label>
			<input type="text" id="id" class="form-control"/>
			<span style="color: red"><form:errors path="id"/></span>
		</div>
	    <div class="form-group">
	        <label for="pwd">비밀번호</label>
			<input type="password" id="pwd" class="form-control"/>
			<span style="color: red"><form:errors path="pwd"/></span>
			<span style="color: red" id="check"></span>
	    </div>
		<div class="checkbox mb-3">
		    <label for="useCookie">
		      <input type="checkbox" name="useCookie" /> 아이디 기억하기
		    </label>
		</div>
	  	<button class="btn btn-lg btn-primary btn-block" type="submit">로그인</button>
	  	<p class="mt-5 mb-3 text-muted">Â© 2017-2020</p>
	</form>
	
	<script type="text/javascript">
		var result = '${ result }';	
		
		if(result == "error"){
			$("#check").html("아이디 또는 비밀번호를 다시 확인해주세요!");
		}


	</script>
</body>
</html>