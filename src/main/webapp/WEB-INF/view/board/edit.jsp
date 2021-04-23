<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
	<h2>게시판 수정</h2>
	<form:form commandName="boardVO" >
		<div class="mb-3">
			<label for="title">제목</label> 
			<form:input path="title" class="form-control" value="${ title }" placeholder="제목을 입력해 주세요" />
			<form:errors path="title"/>
		</div>
		<div class="mb-3">
			<label for="writer">작성자</label>
			<form:input path="writer" class="form-control" value="${ writer }" readonly="true" />
			<form:errors path="writer" />
		</div>
		<div class="mb-3">
			<label for="content">내용</label>
			<form:textarea path="content" class="form-control" rows="5" value="${ content }" placeholder="내용을 입력해 주세요"></form:textarea>
			<form:errors path="content"/>
		</div>
		<div class="text-right">
			<button type="submit" class="btn btn-sm btn-primary" id="edit">수정하기</button>
			<button type="button" class="btn btn-sm btn-primary" id="list" onclick="history.back();">수정취소</button>
		</div>
	</form:form>
</div>

 <%@ include file="../inc/footer.jsp"%>