<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>

<head>

<!-- Required meta tags -->
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<meta name="description" content="">
<meta name="author" content="">

<title>Business Frontpage - Start Bootstrap Template</title>


<!-- Custom styles for this template -->
<link href="/css/business-frontpage.css" rel="stylesheet">

<script src="<c:url value='/jquery/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/smarteditor/js/service/HuskyEZCreator.js'/>"
	charset="utf-8"></script>
</head>

<%@ include file="../inc/top.jsp"%>


<div class="container">
	<h2>게시판 수정</h2>
	<form:form commandName="boardVO" id="frm" enctype="multipart/form-data" method="post">
		<input type="hidden" id="array_fileDel" name="array_fileDel[]" />
		<div class="mb-3">
			<label for="title">제목</label>
			<form:input path="title" class="form-control" value="${ title }"
				placeholder="제목을 입력해 주세요" />
			<form:errors path="title" />
		</div>
		<div class="mb-3">
			<label for="writer">작성자</label>
			<form:input path="writer" class="form-control" value="${ writer }"
				readonly="true" />
			<form:errors path="writer" />
		</div>
		<div class="mb-3">
			<label for="file">첨부파일</label>&nbsp;<button type="button" class="btn btn-sm btn-success ml-3" onclick="file_button_add();">추가</button><button type="button" class="btn btn-sm btn-danger ml-3" onclick="file_button_del();">삭제</button>
			<hr>
			<c:forEach var="file" items="${ board_fileList }" varStatus="status">
				<p id="uploadfile${ status.index + 1 }">${file.ORG_FILE_NAME}&nbsp;&nbsp;(${file.FILE_SIZE} kb)<button type="button" class="btn btn-sm btn-danger ml-3" onclick="remove_file(${ status.index + 1 }, ${ file.FILE_NO });">삭제</button></p>
			</c:forEach>
		</div>
		<div class="mb-3" id="file_add">
		</div>
		<div class="mb-3">
			<label for="content">내용</label>
			<form:textarea path="content" class="form-control" rows="5"
				value="${ content }" placeholder="내용을 입력해 주세요"></form:textarea>
			<form:errors path="content" />
		</div>
		<div class="text-right">
			<button type="button" class="btn btn-sm btn-primary" id="edit">수정하기</button>
			<button type="button" class="btn btn-sm btn-primary" id="list"
				onclick="history.back();">수정취소</button>
		</div>
	</form:form>
</div>
<script>
	// 컨텍스트 경로 반환
	function Path() {
		var contextPath = "${pageContext.request.contextPath}" == "" ? "/" : "${pageContext.request.contextPath}"; 
		return contextPath;
	}
	
	// 첨부 파일 버튼  현재 갯수
	var file_button_count = ${board_FileListCount};
	// 첨부 파일 버튼 최소 갯수
	var file_button_countMin = ${board_FileListCount};
	// 첨부 파일 번호
	var file_number = ${board_FileListCount};
	// 첨부 파일 버튼 추가
	function file_button_add() {
		
		if(file_button_count >= 6){
			alert("첨부파일은 최대 6개 까지 가능합니다.");
			return false;
		}
		
		$("#file_add").append("<span id='uploadfile" + (++file_number) +"' class='mr-3'><input type='file' name='uploadfile" + file_number + "'/><button type='button' class='btn btn-sm btn-danger' onclick='file_cancel("+file_number+");'>취소</button></span>");
		file_button_count++;
	}
	// 첨부 파일 버튼 삭제
	function file_button_del() {
		
		
		if(file_button_count <= file_button_countMin) {
			return false;
		}
		
		$("#uploadfile" + file_number).remove();
		file_button_count--;
		file_number--;
		
	}
	// 첨부파일 취소
	function file_cancel(cancel_num) {
		$("input[name=uploadfile"+ cancel_num +"]").val('');
	}
	// 첨부파일 삭제
	var array = new Array();
	function remove_file(index, file_no) {
		
		array.push(file_no);
		$("#array_fileDel").attr("value", array);
		
		$("#uploadfile"+index).remove();
		if(file_button_count > 0){
			file_button_count--;
		}
		if(file_button_countMin > 0){
			file_button_countMin--;
		}
	}
	
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "content",
		sSkinURI: Path() + "smarteditor/SmartEditor2Skin.html",
		fCreator: "createSEditor2"
	});
		
	//전송버튼
	$("#edit").click(function(){
		//id가 smarteditor인 textarea에 에디터에서 대입
	    oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
	    //폼 submit
	    $("#frm").submit();
	})
	</script>
<%@ include file="../inc/footer.jsp"%>