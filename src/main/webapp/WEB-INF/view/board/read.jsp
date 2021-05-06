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
	
	<style type="text/css">
	
		#reply_writer{
			font-size: 14px;
		}
		
		#reply_contentStyle{
			font-szie: 11px;
		}
		
		#reply_regdate{
			font-size : 11px;
			color: gray;
		    font-weight: bold;
		}
	</style>

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
		<span id="counter">0 / 300</span>
	</div>
	<div class="text-right">
		<button type="button" class="btn btn-light" onclick="reply_write();">댓글 작성</button>
	</div>
	
</div>

<script>
	$(document).ready(function() {
		getReply();
	});
	// 컨텍스트 패스 경로
	var contextPath = "${pageContext.request.contextPath}" == "" ? "/" : "${pageContext.request.contextPath}";
	// 현재 게시판 번호
	var bno = "${boardVO.bno}";
	// 댓글 작성시 글자수 갯수 체크
	$('#reply_content').keyup(function(){
	    var content = $(this).val();
	    $('#counter').html("" +content.length+ " / 300");    //글자수 실시간 카운팅

	    if (content.length > 300){
	        alert("최대 300자까지 입력 가능합니다.");
	        $(this).val(content.substring(0, 300));
	        $('#counter').html("1000 / 300");
	    }
	});
	
	// 게시판 삭제
	function delete_cehck() {
		if(confirm("정말로 삭제 하시겠습니까?")){
			location.href='/board/delete/${boardVO.bno}?user_id=${ loginUser.id }&page=' + ${page};
		} else {
			return false;
		}
	}
	
	// 게시판 댓글 불러오기
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
						
						var timestamp_regdate = data.replyList[i].regdate;
						var date_regdate = new Date(timestamp_regdate);
						var timestamp_lastUpdate = data.replyList[i].last_update;
						
						var content = data.replyList[i].content;

						html += "<div class='text-left mb-3'><span id='reply_writer'>작성자 : " + data.replyList[i].writer + "</span></div>";
						html += "<div class='mb-3' id='" + data.replyList[i].rno + "_content' style='word-break:break-all;'>";
						html += 		"<pre>" + data.replyList[i].content + "</pre>";
						html += "</div>";
						html += "<div class='text-right mb-3'><span id='reply_regdate'>";
						html += 		"작성일 : " + date_regdate.getFullYear() + "/" + (date_regdate.getMonth()+1) + "/" + date_regdate.getDate()+ " " + date_regdate.getHours() + ":" + date_regdate.getMinutes();
						html += "<br>";
						if(timestamp_lastUpdate != null){
							var date_lastUpdate = new Date(timestamp_lastUpdate);
							html += 	"최종 수정일 : " + date_lastUpdate.getFullYear() + "/" + (date_lastUpdate.getMonth()+1) + "/" + date_lastUpdate.getDate()+ " " + date_lastUpdate.getHours() + ":" + date_lastUpdate.getMinutes();
						}
						html += "</span></div>";
						if( "${loginUser.id}" != ""){
							if( "${loginUser.id}" ==  data.replyList[i].writer){
								html += "<div class='text-right' id='"+ data.replyList[i].rno +"_button'>";
								html += 		"<button class='btn btn-success mr-3' onclick=" + "reply_edit(" + "'" + data.replyList[i].writer + "'," + data.replyList[i].rno + ","+ reply_page +");>수정</button>";
								html += 		"<button class='btn btn-danger' onclick=" + "reply_delete(" + "'" + data.replyList[i].writer + "'," + data.replyList[i].rno + ","+ reply_page +");>삭제</button>";
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
				
				if( data.pageMaker.next && data.pageMaker.endPage > 0){
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
	
	// 게시판 댓글 작성
	function reply_write() {
		// 사용자가 입력한 댓글
		var content = $("#reply_content").val();
		// 현재 보고 있는 댓글 페이지
		var replyPage = $("#selBtn").text();
		
		var text = "작성";
		
		if( "${loginUser.id}" == "" || "${loginUser.id}" == null){
			alert("로그인 후 이용 가능합니다.");
		} 
		else if( edit_ButtonCount > 0){
			alert("현재 수정중인 댓글이 존재합니다.");
		}
		else {
			var form = {
					'writer' : "${loginUser.id}",
					'content' : content
			}
			
			$.ajax({
				type: "POST",
				url: contextPath + "board/reply/write/" + bno +"?user_id=${loginUser.id}",
				data: JSON.stringify(form),
				dataType : "json",
				contentType: "application/json; charset=UTF-8",
				success: function(data){
					result_check(data, text, replyPage);
				},
				error: function(error) {
					alert("관리자에게 문의 바랍니다!");	
				}
			})
		}
		
	}
	
	// 다른 수정 버튼 클릭하지 못하게 만들기 위한 count 전역 변수
	var edit_ButtonCount = 0;
	
	// 게시판 댓글 수정
	function reply_edit(writer, rno, replyPage) {
		
		if(edit_ButtonCount == 0){
			var html = "";
			
			var content = $("#" + rno + "_content").html();
			var replace_content = content.replace("<pre>", "").replace("</pre>", "");
			
			$("#" + rno + "_content").html(
					"<textarea class='w-100' rows='5' name='content' id='edit_textarea'>" + replace_content + "</textarea>"
					+"<div class='text-right'><span id='edit_counter'>"+ content.length +" / 1000</span></div>");
			
			html += "<button class='btn btn-success mr-3' onclick=" + "reply_update(" + "'" + writer + "'," + rno + "," + replyPage + ");>수정완료</button>";
			html += "<button class='btn btn-danger' onclick='reply_cancel(" + replyPage + ");'>수정취소</button>";
			
			// 현재 수정중인 댓글의 부모 div 태그 선택 후 html 삽입
			$("#"+ rno +"_button").html(html);
			
			// 댓글 수정시 글자수 갯수 체크
			$("#edit_textarea").keyup(function(){
				
			    var content = $(this).val();
			    $("#edit_counter").html("" +content.length+ " / 300");    //글자수 실시간 카운팅

			    if (content.length > 1000){
			        alert("최대 300자까지 입력 가능합니다.");
			        $(this).val(content.substring(0, 300));
			        $('#edit_counter').html("1000 / 300");
			    }
			});

			edit_ButtonCount = 1;
			
		} else {
			alert("현재 수정중인 댓글이 존재 합니다.");
		}
		
	}
	
	function reply_cancel(replyPage) {
		
		edit_ButtonCount = 0;
		getReply(replyPage);
		
	}
	
	function reply_update(writer, rno, replyPage) {
		
		var content = $("#edit_textarea").val();
		
		var text = "수정";
		
		// textarea 개행 문자 치환
		var replace_content = content.replace(/(?:\r\n|\r|\n)/g, "<br>");
		
		if( "${loginUser.id}" != writer){
			alert("잘못된 접근 입니다.");
		} 
		else {
			$.ajax({
				type: "POST",
				url: contextPath + "board/reply/edit?user_id=${loginUser.id}",
				data: { content : replace_content, rno : rno, writer : writer },
				success: function(data){
					result_check(data, text, replyPage);
				},
				error: function(error) {
					alert("관리자에게 문의 바랍니다!");	
				}
			})
		}
		
	}
	
	function reply_delete(writer, rno, replyPage) {
		
		var text = "삭제";
		
		if("${loginUser.id}" == writer){
			$.ajax({
				url : contextPath + "board/reply/delete?user_id=${loginUser.id}",
				type : "POST",
				data : { writer : writer, rno : rno},
				success: function(data){
					result_check(data, text, replyPage);
				},
				error: function(error){
					alert("관리자에게 문의 바랍니다!");
				}
				
			})
			
		} else {
			alert("잘못된 접근입니다.");
		}
		
	}

	// 댓글 작성, 수정, 삭제 공통 부분 함수로 구현
	function result_check(data, text, replyPage){
		if(data.result == "OK"){
			alert("댓글 "+text+" 완료!");
			getReply(replyPage);
			if(text == "수정"){
				edit_ButtonCount = 0;
			}
			if(text == "작성"){
				$("#reply_content").val("");
			}
		}
		else if(data.result == "FAIL" || data.result == "ERROR"){
			alert("댓글 "+text+" 실패!");
			getReply(replyPage);
		}
	}
	

	
	
	
</script>

 <%@ include file="../inc/footer.jsp"%>
