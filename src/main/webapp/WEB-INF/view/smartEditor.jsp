<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link id="contextPath" data-path="<c:url value='/'/>" />
		<link rel="stylesheet" href="<c:url value='/css/bootstrap/bootstrap.min.css'/>">
		
		<script src="<c:url value='/jquery/jquery.min.js'/>"></script>
		<script src="<c:url value='/css/bootstrap/bootstrap.bundle.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/smarteditor/js/service/HuskyEZCreator.js'/>" charset="utf-8"></script>
		
		<title>Insert title here</title>
	</head>
	<body>
		<form action="/submit" method="post" id="frm">
			<textarea name="editor" id="editor" rows="10" cols="100">에디터에 기본으로 삽입할 글(수정 모드)이 없다면 이 value 값을 지정하지 않으시면 됩니다.</textarea>
			<input type="button" id="savebutton" value="서버전송" />
		</form>
		
	</body>
	<script>
	// 컨텍스트 경로 반환
	function Path() {
		var contextPath = "${pageContext.request.contextPath}" == "" ? "/" : "${pageContext.request.contextPath}"; 
		return contextPath;
	}
	
		var oEditors = [];
		nhn.husky.EZCreator.createInIFrame({
			oAppRef: oEditors,
			elPlaceHolder: "editor",
			sSkinURI: Path() + "smarteditor/SmartEditor2Skin.html",
			fCreator: "createSEditor2"
		});
		
	    //전송버튼
	    $("#savebutton").click(function(){
	        //id가 smarteditor인 textarea에 에디터에서 대입
	        oEditors.getById["editor"].exec("UPDATE_CONTENTS_FIELD", []);
	        //폼 submit
	        $("#frm").submit();
	    })
	</script>
</html>