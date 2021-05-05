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
	<p class="replyC">댓글 최신순</p>
	<hr>
	<div class="mb-3 reply">

	</div>
	<div class="btn-toolbar" role="toolbar"	aria-label="Toolbar with button groups">
		<div class="btn-group m-auto reply_button_group" role="group" aria-label="First group">
		</div>
	</div>
	
	<p>댓글 작성</p>
	<hr>
	<textarea class="w-100" rows="5" name="content" id="reply_content"></textarea>
	<div class="text-right">
		<span id="counter">0 / 1000</span>
	</div>
	<div class="text-right">
		<button type="button" class="btn btn-light" onclick="reply_write();">댓글 작성</button>
	</div>
	
</div>

<script>
	var contextPath = "${pageContext.request.contextPath}" == "" ? "/" : "${pageContext.request.contextPath}";

	var bno = "${boardVO.bno}";
	
	$(document).ready(function() {
		getReply();
	});
	
	
	$('#reply_content').keyup(function(){
	    var content = $(this).val();
	    $('#counter').html("" +content.length+ " / 1000");    //글자수 실시간 카운팅

	    if (content.length > 1000){
	        alert("최대 1000자까지 입력 가능합니다.");
	        $(this).val(content.substring(0, 1000));
	        $('#counter').html("1000 / 1000");
	    }
	});
	
	function getReply(reply_page) {
		
		if(reply_page == null || reply_page == ""){
			reply_page = 1;
		}
		
		$.ajax({
			type: "GET",
			url: contextPath + "board/reply/list/" + bno +"?page="+reply_page,
			success: function(data){
				var html = "";
				if(data.replyList.length >= 1){
					for(i = 0; i < data.replyList.length; i++){
						
						var timestamp = data.replyList[i].regdate;
						var date = new Date(timestamp);
						
						html += "<div class='text-left mb-3'>" + data.replyList[i].writer + "</div>";
						html += "<div class='mb-3' id='" + data.replyList[i].rno + "_content' style='white-space:pre;'>";
						html += 		data.replyList[i].content;
						html += "</div>";
						html += "<div class='text-right mb-3'>";
						html += 		date.getFullYear() + "/" + (date.getMonth()+1) + "/" + date.getDate()+ " " + date.getHours() + ":" + date.getMinutes();
						html += "</div>";
						if( "${loginUser.id}" != ""){
							if( "${loginUser.id}" ==  data.replyList[i].writer){
								html += "<div class='text-right " + data.replyList[i].rno + "_button'>";
								html += 		"<button class='btn btn-success mr-3' onclick=" + "reply_edit(" + "'" + data.replyList[i].writer + "'," + data.replyList[i].rno + ","+ reply_page +");>수정</button>";
								html += 		"<button class='btn btn-danger'>삭제</button>";
								html += "</div>";	
							}
						}
						html += "<hr>";
					
					}
					
				} else {
					html = "등록된 댓글이 존재하지 않습니다.";
				}
				
				var html_button = "";
				
				if(data.pageMaker.prev){
					html_button += "<button type='button' class='btn btn-secondary'";
					html_button += "onclick='getReply("+ (data.pageMaker.startPage-1) +");'";
					html_button += ">이전</button>";
				}
			
				for(i = data.pageMaker.startPage; i < data.pageMaker.endPage; i++){
					
					if( reply_page == i){
						html_button += "<button type='button' class='btn btn-success' id='selBtn'"; 
						html_button += "onclick='getReply("+ i +");'>";
						html_button +=  i + "</button>";
					} else {
						html_button += "<button type='button' class='btn btn-btn-secondary'"; 
						html_button += "onclick='getReply("+ i +");'>";
						html_button +=  i + "</button>";
					}
					
				}
				
				if( data.pageMaker.next && data.PageMaker.endPage > 0){
					html_button += "<button type='button' class='btn btn-secondary'";
					html_button += "onclick='getReply("+ ( data.pageMaker.endPage + 1) +");'";
					html_button += ">다음</button>";
				}
				
				
				$(".reply_button_group").html(html_button);
				
				if(data.replyCount > 0){
					$(".replyC").html("댓글 최신순 ("+data.replyCount+")");
				}
				
				$(".reply").html(html);
			},
			error: function(error){
				alert("관리자에게 문의 바랍니다!");
			}
			
			
		});
		
	}
	
	function reply_write() {
		// 사용자가 입력한 댓글
		var content = $("#reply_content").val();
		// 현재 보고 있는 댓글 페이지
		var replyPage = $("#selBtn").text();
		
		if( "${loginUser.id}" == "" || "${loginUser.id}" == null){
			alert("로그인 후 이용 가능합니다.");
		} else {
			$.ajax({
				type: "POST",
				url: contextPath + "board/reply/write/" + bno +"?user_id=${loginUser.id}",
				data: { content : content },
				success: function(data){
					if(data.result == "OK"){
						alert("댓글 작성 완료!");
						getReply(replyPage);
					}
					else if(data.result == "FAIL"){
						alert("댓글 작성 실패! 관리자에게 문의바랍니다!");
					}
					else if(data.result == "ERROR"){
						alert("오류 발생! 관리자에게 문의바랍니다!");
					}
				},
				error: function(error) {
					alert("관리자에게 문의 바랍니다!");	
				}
			})
		}
		
	}
	
	$('#edit_textarea').keyup(function(){
	    var content = $(this).val();
	    $('#edit_counter').html("" +content.length+ " / 1000");    //글자수 실시간 카운팅

	    if (content.length > 1000){
	        alert("최대 1000자까지 입력 가능합니다.");
	        $(this).val(content.substring(0, 1000));
	        $('#edit_counter').html("1000 / 1000");
	    }
	});
	
	function reply_edit(writer, rno, replyPage) {
		
		var html = "";
		
		var content = $("#" + rno + "_content").html();
		
		$("#" + rno + "_content").html(
				"<textarea class='w-100' rows='3' name='content' id='edit_textarea'>" + content + "</textarea>"
				+"<div class='text-right'><span id='edit_counter'>"+ content.length +" / 1000</span></div>");
		
		html += "<button class='btn btn-success mr-3' onclick=" + "reply_update(" + "'" + writer + "'," + rno + "," + replyPage + ");>수정완료</button>";
		html += "<button class='btn btn-danger' onclick='getReply(" + replyPage + ");'>수정취소</button>";
		
		$("."+rno+"_button").html(html);
	}
	
	function reply_update(writer, rno, replyPage) {
		
		var content = $("#edit_textarea").val();
		
		if( "${loginUser.id}" != writer){
			alert("잘못된 접근 입니다.");
		} 
		else {
			$.ajax({
				type: "POST",
				url: contextPath + "board/reply/edit?user_id=${loginUser.id}",
				data: { content : content, rno : rno },
				success: function(data){
					if(data.result == "OK"){
						alert("댓글 수정 완료!");
						getReply(replyPage);
					}
					else if(data.result == "FAIL"){
						alert("댓글 수정 실패! 관리자에게 문의바랍니다!");
					}
					else if(data.result == "ERROR"){
						alert("오류 발생! 관리자에게 문의바랍니다!");
					}
				},
				error: function(error) {
					alert("관리자에게 문의 바랍니다!");	
				}
			})
		}
		
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
