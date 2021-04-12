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
	<link href="<c:url value='/css/join.css'/>" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="signup-form">
		<h2 class="edit_title">회원정보</h2>
        <div class="form-group">
        	<label for="user_name">아이디</label>
			<input id="user_name" type="text" class="form-control" value="${ memberVO.user_id }" readonly/>
        </div>

        <div class="form-group">
        	<label for="user_id">이름</label>
			<input id="user_id" class="form-control" value="${ memberVO.user_name }" readonly/>
        </div>
        <div class="form-group">
        	<label for="user_id">닉네임</label>
			<input id="user_id" class="form-control" value="${ memberVO.user_nickname }" readonly/>
        </div>
        <div class="form-group">
        	<label for="user_id">이메일</label>
			<input id="user_id" class="form-control" value="${ memberVO.user_mail }" readonly/>
        </div>
		<div class="form-group">
            <button type="button" onclick="location.href='<c:url value='/member/edit/chid?user_id=${ memberVO.user_id }'/>'" class="btn btn-primary btn-lg btn-block">아이디 변경</button>
        </div>
		<div class="form-group">
            <button type="button" onclick="location.href='<c:url value='/member/edit/chpass?user_id=${ memberVO.userid }'/>'" class="btn btn-primary btn-lg btn-block">비밀번호 변경</button>
        </div>
		<div class="form-group">
            <button type="button" onclick="location.href='<c:url value='/'/>'" class="btn btn-danger btn-lg btn-block">수정취소</button>
        </div>
</div>

</body>
</html>