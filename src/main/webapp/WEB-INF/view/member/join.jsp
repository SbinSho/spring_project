<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
	<title>Hello</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	
	<link href="<c:url value='/css/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
	<link href="<c:url value='/css/join.css'/>" rel="stylesheet">
	
</head>
<body>
	<div class="signup-form">
	    <form:form commandName="memberVO" id="join_form" method="post">
			<h2>회원가입</h2>
			<p class="hint-text">Create your account. It's free and only takes a minute.</p>
			<div class="form-group">
	        	<label for="user_id">아이디</label>
				<form:input  path="user_id" id="user_id" class="form-control" placeholder="아이디" />
				<form:errors path="user_id"  />
				<span id="userid_check"></span>
	        </div>
	        <div class="form-group">
	        	<label for="user_name">이름</label>
				<form:input path="user_name" id="user_name" class="form-control" placeholder="이름"/>
				<form:errors path="user_name"/>
				<span id="username_check"></span>
	        </div>
	        <div class="form-group">
	        	<label for="user_nickname">닉네임</label>
				<form:input  path="user_nickname" id="user_nickname" class="form-control" placeholder="닉네임"/>
				<form:errors path="user_nickname" />
				<span id="usernickname_check"></span>
	        </div>
	        <div class="form-group">
	        	<label for="user_email">이메일</label>
				<form:input  type="email" path="user_mail" id="user_mail" class="form-control" placeholder="이메일"/>
				<form:errors path="user_mail"/>
				<span id="useremail_check"></span>
	        </div>
			<div class="form-group">
				<label for="user_pwd">비밀번호</label>
	            <form:password path="user_pwd" id="user_pwd" class="form-control" placeholder="비밀번호" />
	            <form:errors path="user_pwd"/>
	           	<span id="password_check"></span>
	        </div>
			<div class="form-group">
				<label for="user_con_pwd">비밀번호 재확인</label>
	            <input type="password" class="form-control" id="user_con_pwd" placeholder="비밀번호 확인">
	           	<span id="password_con_check"></span>
	        </div>
			<div class="form-group">
	            <button type="submit" class="btn btn-primary btn-lg btn-block">회원가입</button>
	        </div>
	    </form:form>
		<div class="text-center">
			Already have an account? <a href="<c:url value='/member/login'/>">로그인</a>
		</div>
	</div>
</body>

<script>
	



</script>

</html>