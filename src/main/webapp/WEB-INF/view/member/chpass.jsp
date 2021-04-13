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
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	 
	<script type="text/javascript" src="/js/rsa/jsbn.js"></script>
	<script type="text/javascript" src="/js/rsa/rsa.js"></script>
	<script type="text/javascript" src="/js/rsa/prng4.js"></script>
	<script type="text/javascript" src="/js/rsa/rng.js"></script>
	<link href="<c:url value='/css/join.css'/>" rel="stylesheet">

</head>
<body>
<div class="signup-form">
    <form>
    	<input type="hidden" id="RSAModulus" value="${RSAModulus}" /><!-- 서버에서 전달한값을 셋팅한다. -->
		<input type="hidden" id="RSAExponent" value="${RSAExponent}" /><!-- 서버에서 전달한값을 셋팅한다. -->
        <div class="form-group">
        	<label for="user_pwd">현재 비밀번호</label>
        	<input type="password" id="user_pwd" class="form-control" />
        </div>

        <div class="form-group">
        	<label for="ch_userpwd">새 비밀번호</label>
        	<input type="password" id="ch_user_pwd" class="form-control" />
        	<span id="password_check"></span>
        </div>
        <div class="form-group">
        	<label for="ch_user_pwd_confirm">새 비밀번호 확인</label>
        	<input type="password" id="ch_user_pwd_confirm" class="form-control">
        	<span id="password_con_check"></span>
        </div>
		<div class="form-group">
            <button type="button" class="btn btn-primary btn-lg btn-block" onclick="form_check();">수정하기</button>
        </div>
		<div class="form-group">
            <button type="button" onclick="history.back();" class="btn btn-danger btn-lg btn-block">뒤로가기</button>
        </div>
    </form>
</div>

<script type="text/javascript">

	var chFlag = false;
	var chConFlag = false;
	
	// 영문, 숫자 조합의 8~20자리
	var isPw = /^[a-zA-Z0-9]{8,20}$/;
	
	// 컨텍스트 경로 반환
	function Path() {
		var contextPath = "${pageContext.request.contextPath}" == "" ? "/" : "${pageContext.request.contextPath}"; 
		return contextPath;
	}
	
	
	$("#ch_user_pwd").blur(function(){
		var ch_user_pwd = $("#ch_user_pwd").val();
		
		if (isPw.test(ch_user_pwd)){
			
			$("#password_check").css("color", "green");
			$("#password_check").text("비밀번호 사용 가능!");
			chFlag = true;
		}
		else {
			
			$("#password_check").css("color", "red");
			$("#password_check").text("숫자와 영문자 조합으로 8~20자리를 사용해야 합니다.");
			chFlag = false;
		}
		
	})
	
	
	
	$("#ch_user_pwd_confirm").blur(function (){
		
		var ch_user_pwd = $("#ch_user_pwd").val();
		var ch_user_pwd_confirm = $("#ch_user_pwd_confirm").val();

		if( ch_user_pwd == ch_user_pwd_confirm){
			$("#password_con_check").css("color", "green");
			$("#password_con_check").html("비밀번호 확인 완료!");
			chConFlag = true;
			
		} else {
			$("#password_con_check").css("color", "red");
			$("#password_con_check").text("비밀번호를 확인해주세요.");
			chConFlag = false;
			
		}		
	})
	
	function form_check() {
		
		var user_pwd = $("#user_pwd").val();
		var ch_user_pwd = $("#ch_user_pwd").val();
		var ch_user_pwd_confirm = $("#ch_user_pwd_confirm").val();
		
		if(user_pwd == "" || ch_user_pwd == "" || ch_user_pwd_confirm == ""){
			alert("공백은 입력 할 수 없습니다.");
			return false;
		}
		
		if (chFlag && chConFlag){
			
			var confirm_check = confirm("비밀번호를 변경하시겠습니까?");
			
			if(confirm_check){
				submitEncryptedForm(user_pwd, ch_user_pwd);
			}			
		} 
		else {
			alert("비밀번호를 다시 확인 해주세요.");
		}
	}
	
	// 데이터 암호화 및 서버 전송
	function submitEncryptedForm(user_pwd, ch_user_pwd){
		
		// RSA 암호화 생성
		var rsa = new RSAKey();
		rsa.setPublic($("#RSAModulus").val(), $("#RSAExponent").val());
		
		var user_id = "${ user_id }";
		
		// 암호화된 폼 데이터
		var en_pwd = rsa.encrypt(user_pwd);
		var en_chpwd = rsa.encrypt(ch_user_pwd);
		
		var form = {
				'user_pwd' : en_pwd,
				'ch_user_pwd' : en_chpwd
		}
		
		$.ajax({
			type:"POST",
			url: Path() + "member/edit/chpass?user_id=" + user_id,
			data: JSON.stringify(form),
			dataType : "json",
			contentType: "application/json; charset=UTF-8",
			success: function(data){
				if(data.result == "OK"){
					alert("비밀번호 변경 완료!");
					location.href = Path() + "member/edit/info?user_id=" + user_id;
				}
				else if (data.result == "DB_ERROR"){
					alert("오류 발생!");
					location.href= Path() + "member/edit/chpass?user_id=" + user_id;
				}
				else if (data.result == "ERROR"){
					alert("오류 발생!");
					location.href= Path() + "member/edit/chpass?user_id=" + user_id;
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