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
	
	<script src="<c:url value='/jquery/jquery.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/smarteditor/js/service/HuskyEZCreator.js'/>" charset="utf-8"></script>
</head>  

<%@ include file="../inc/top.jsp" %>


<div class="container">
	<h2>게시판 글쓰기</h2>
	<form:form commandName="boardWriteDTO" id="frm">
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
			<button type="button" class="btn btn-sm btn-primary" id="write">글쓰기</button>
			<button type="button" class="btn btn-sm btn-primary" id="list" onclick="history.back();">목록</button>
		</div>
	</form:form>
</div>

	<script>
	// 컨텍스트 경로 반환
	function Path() {
		var contextPath = "${pageContext.request.contextPath}" == "" ? "/" : "${pageContext.request.contextPath}"; 
		return contextPath;
	}
	
		var oEditors = [];
		nhn.husky.EZCreator.createInIFrame({
			oAppRef: oEditors,
			elPlaceHolder: "content",
			sSkinURI: Path() + "smarteditor/SmartEditor2Skin.html",
			fCreator: "createSEditor2"
		});
		
	    //전송버튼
	    $("#write").click(function(){
	        //id가 smarteditor인 textarea에 에디터에서 대입
	        oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
	        //폼 submit
	        $("#frm").submit();
	    })
	</script>

 <%@ include file="../inc/footer.jsp"%>