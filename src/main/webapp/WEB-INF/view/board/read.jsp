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
		<p>첨부 파일</p>
		<hr>
		<c:choose>
			<c:when test="${ empty board_fileList }">
				<p>첨부파일이 존재하지 않습니다.</p>
			</c:when>
			<c:otherwise>
				<c:forEach var="file" items="${ board_fileList }">
					<a href="<c:url value='/board/fileDownload/${ file.FILE_NO }'/>">${file.ORG_FILE_NAME}</a>&nbsp;&nbsp;(${file.FILE_SIZE} kb)<br>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="mb-3" style="word-break:break-all; ">
		<p>내용</p>
		<hr>
		<span id="content">${ boardVO.content }</span>
	</div>
	<div class="text-right">
		<c:if test="${ loginUser.id eq boardVO.writer }">
			<button type="button" class="btn btn-sm btn-primary" id="button_edit" onclick="location.href='/board/edit/${ boardVO.bno }?user_id=${ loginUser.id }&page=${ page }'">수정</button>
			<button type="button" class="btn btn-sm btn-primary" id="button_delete" onclick="delete_cehck()">삭제</button>
		</c:if>
		<button type="button" class="btn btn-sm btn-primary" id="list" onclick="location.href='<c:url value="/board/list?page=${page}"/>';">목록</button>
	</div>
</div>

<script>
	var contextPath = "${pageContext.request.contextPath}" == "" ? "/" : "${pageContext.request.contextPath}";
	function delete_cehck() {
		if(confirm("정말로 삭제 하시겠습니까?")){
			location.href='/board/delete/${boardVO.bno}?user_id=${ loginUser.id }&page=' + ${page};
		} else {
			return false;
		}
	}

</script>

 <%@ include file="../inc/footer.jsp"%>
