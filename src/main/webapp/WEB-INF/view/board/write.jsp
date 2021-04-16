<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>

<head>

    <!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

	<meta name="description" content="">
	<meta name="author" content="">
	
	<title>Business Frontpage - Start Bootstrap Template</title>

	
	<!-- Custom styles for this template -->
	<link href="/css/business-frontpage.css" rel="stylesheet">
	
	<script type="text/javascript" src="/js/rsa/jsbn.js"></script>
	<script type="text/javascript" src="/js/rsa/rsa.js"></script>
	<script type="text/javascript" src="/js/rsa/prng4.js"></script>
	<script type="text/javascript" src="/js/rsa/rng.js"></script>
</head>  

<%@ include file="../inc/top.jsp" %>


<div class="container">
	<h2>불법영업 문의</h2>
	<form action="/unregistered/write" method="post">
		<div class="mb-3">
			<label for="unregistered_title">제목</label> <input type="text" class="form-control"
				name="unregistered_title" id="unregistered_title" placeholder="제목을 입력해 주세요">
		</div>
		<div class="mb-3">
			<p>공개여부</p>
			<input type="radio" id="unregistered_public" name="unregistered_public" value="true">&nbsp;공개
			<input type="radio" id="unregistered_public" name="unregistered_public" value="false" checked="checked" class="ml-5">&nbsp;비공개
		</div>
		<div class="mb-3">
			<label for="user_id">작성자</label>
			<input type="text"	class="form-control" name="user_id" id="user_id" value="${ user_id }" readonly>
		</div>
<!-- 		<div class="mb-3"> -->
<!-- 			<p>첨부파일</p> -->
<!-- 			<input type="file" name="file1" id="file1"> -->
<!-- 			<br> -->
<!-- 			<input type="file" name="file2" id="file2"> -->
<!-- 		</div> -->
		<div class="mb-3">
			<label for="unregistered_content">내용</label>
			<textarea class="form-control" rows="5" name="unregistered_content" id="unregistered_content" placeholder="내용을 입력해 주세요"></textarea>
		</div>
		<div class="text-right">
			<button type="button" class="btn btn-sm btn-primary" id="write" onclick="form_check()">글쓰기</button>
			<button type="button" class="btn btn-sm btn-primary" id="list" onclick="location.href='/board/write?user_id=${user_id}'">목록</button>
		</div>
	</form>
</div>


 <%@ include file="../inc/footer.jsp"%>