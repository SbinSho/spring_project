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
	
	<script src="https://code.jquery.com/jquery-3.5.1.js"></script>

</head>  

<%@ include file="../inc/top.jsp" %>


<div class="container">
	<h2>게시판</h2>
	<hr>
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
	<div class="text-right mb-3">
		<c:if test="${ loginUser.id eq boardVO.writer }">
			<button type="button" class="btn btn-sm btn-primary" id="button_edit" onclick="location.href='/board/edit/${ boardVO.bno }?user_id=${ loginUser.id }&page=${ page }'">수정</button>
			<button type="button" class="btn btn-sm btn-primary" id="button_delete" onclick="delete_cehck()">삭제</button>
		</c:if>
		<button type="button" class="btn btn-sm btn-primary" id="list" onclick="location.href='<c:url value="/board/list?page=${page}"/>';">목록</button>
	</div>
	<p>댓글 최신순</p>
	<hr>
	<div class="mb-3 reply">

	</div>
	
	<p>댓글 작성</p>
	<hr>
	<textarea class="w-100" rows="3" style="resize: none;" name="content" id="content"></textarea>
	<div class="text-right">
		<button type="button" class="btn btn-light" onclick="">댓글 작성</button>
	</div>
	
</div>

<script>
	var contextPath = "${pageContext.request.contextPath}" == "" ? "/" : "${pageContext.request.contextPath}";

	var bno = "${boardVO.bno}";
	
	$(document).ready(function() {
		
		getReply();
		
	});
	
	function getReply() {
		
		$.ajax({
			type: "GET",
			url: contextPath + "board/reply/list/" + bno,
			contentType: "application/json; charset=UTF-8",
			success: function(data){
				
				var html = "";
				if(data.length >= 1){
					for(i = 0; i < data.length; i++){
						
						
						var timestamp = data[i].regdate;
						var date = new Date(timestamp);
						
						html += "<div class='text-left mb-3'>"+ data[i].writer +"</div>";
						html += "<div class='mb-3'>";
						html += 		data[i].content;
						html += "</div>";
						html += "<div class='text-right mb-3'>";
						html += 		date.getFullYear() + "/" + (date.getMonth()+1) + "/" + date.getDate()+ " " + date.getHours() + ":" + date.getMinutes();
						html += "</div>";
						html += "<div class='text-right'>";
						html += 		"<button class='btn btn-success mr-3'>수정</button>";
						html += 		"<button class='btn btn-danger'>삭제</button>";
						html += "</div>";	
						html += "<hr>";
					
					}
					
				} else {
					html = "등록된 댓글이 존재하지 않습니다.";
				}
				
				$(".reply").html(html);
			},
			error: function(error){
				alert("관리자에게 문의 바랍니다!");
			}
			
			
		});
		
	}

	function delete_cehck() {
		if(confirm("정말로 삭제 하시겠습니까?")){
			location.href='/board/delete/${boardVO.bno}?user_id=${ loginUser.id }&page=' + ${page};
		} else {
			return false;
		}
	}

	
	
	
</script>

 <%@ include file="../inc/footer.jsp"%>
