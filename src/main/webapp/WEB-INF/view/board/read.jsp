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
	<h2>게시판</h2>
	<div class="mb-3">
		<label for="title">제목</label>
		<span class="form-control" id="title">${ boardVO.title }</span>
	</div>
	<div class="mb-3">
		<label for="writer">작성자</label>
		<span class="form-control" id="writer">${ boardVO.writer }</span>
	</div>
	<div class="mb-3">
		<label for="content">내용</label>
		<span class="form-control" id="content">${ boardVO.content }</span>
	</div>
	<div class="text-right">
		<button type="button" class="btn btn-sm btn-primary" id="list" onclick="history.back()">목록</button>
	</div>
</div>


 <%@ include file="../inc/footer.jsp"%>