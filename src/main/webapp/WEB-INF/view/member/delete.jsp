<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
	<title>Hello</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link href="<c:url value='/css/join.css'/>" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	
	<script type="text/javascript" src="/js/rsa/jsbn.js"></script>
	<script type="text/javascript" src="/js/rsa/rsa.js"></script>
	<script type="text/javascript" src="/js/rsa/prng4.js"></script>
	<script type="text/javascript" src="/js/rsa/rng.js"></script>	
</head>
<body>
<div class="signup-form">
		<h2 class="edit_title">회원탈퇴</h2>
		<input type="hidden" id="RSAModulus" value="${RSAModulus}" /><!-- 서버에서 전달한값을 셋팅한다. -->
		<input type="hidden" id="RSAExponent" value="${RSAExponent}" /><!-- 서버에서 전달한값을 셋팅한다. -->
        <div class="form-group">
        	<label for="user_pwd">비밀번호 입력</label>
			<input id="user_pwd" type="password" class="form-control"/>
        </div>
		<div class="form-group">
            <button type="button" onclick="form_check()" class="btn btn-danger btn-lg btn-block">회원탈퇴</button>
        </div>
		<div class="form-group">
            <button type="button" onclick="history.back();" class="btn btn-secondary btn-lg btn-block">돌아가기</button>
        </div>
</div>
<script type="text/javascript">

	// 컨텍스트 경로 반환
	function Path() {
		var contextPath = "${pageContext.request.contextPath}" == "" ? "/" : "${pageContext.request.contextPath}"; 
		return contextPath;
	}
	
	$(document).ready(function() {
		
        var csrfToken = "${_csrf.token}";
        var csrfHeader = "${_csrf.headerName}";
        
	    // ajax 요청하기 전 호출되는 이벤트 ( 토큰값 설정 하기 )
		$(document).ajaxSend(function (e, xhr, options){
			xhr.setRequestHeader(csrfHeader, csrfToken);
		});
	});
	
	
	function form_check() {
		
		var user_pwd = $("#user_pwd").val();
		
		if( user_pwd == "" ){
			alert("공백은 입력 할 수 없습니다.");
			return false;
		}
		
			
		var confirm_check = confirm("회원탈퇴 하시겠습니까?");
			
		if(confirm_check){
			submitEncryptedForm(user_pwd);
		}			
	}
	
	// 데이터 암호화 및 서버 전송
	function submitEncryptedForm(user_pwd){
		
		// RSA 암호화 생성
		var rsa = new RSAKey();
		rsa.setPublic($("#RSAModulus").val(), $("#RSAExponent").val());
		
		var user_id = "${ user_id }";
		
		// 암호화된 폼 데이터
		var en_pwd = rsa.encrypt(user_pwd);
		
		var form = {
				'user_pwd' : en_pwd,
		}
		
		$.ajax({
			type:"POST",
			url: Path() + "member/edit/delete?user_id=" + user_id,
			data: JSON.stringify(form),
			dataType : "json",
			contentType: "application/json; charset=UTF-8",
			success: function(data){
				if(data.result == "OK"){
					alert("회원탈퇴 완료!");
					location.href = Path();
				}
				else if (data.result == "DB_ERROR"){
					alert("오류 발생!");
					location.href= Path() + "member/edit/delete?user_id=" + user_id;
				}
				else if (data.result == "ERROR"){
					alert("오류 발생!");
					location.href= Path() + "member/edit/delete?user_id=" + user_id;
				}
				
			},
			error: function(error){
				alert("관리자에게 문의 바랍니다!");
			}
		});
		
	}
</script>
</body>
</html>