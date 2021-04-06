<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto:400,700">
<title>Hello</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>

<link href="<c:url value='/css/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
<link href="<c:url value='/css/join.css'/>" rel="stylesheet">

<!-- RSA 자바스크립트 라이브러리 -->
<script type="text/javascript" src="/js/rsa/jsbn.js"></script>
<script type="text/javascript" src="/js/rsa/rsa.js"></script>
<script type="text/javascript" src="/js/rsa/prng4.js"></script>
<script type="text/javascript" src="/js/rsa/rng.js"></script>

</head>
<body>
	<div class="signup-form">
		<form>
			<input type="hidden" id="RSAModulus" value="${RSAModulus}" /><!-- 서버에서 전달한값을 셋팅한다. -->
			<input type="hidden" id="RSAExponent" value="${RSAExponent}" /><!-- 서버에서 전달한값을 셋팅한다. -->
			<h2>회원가입</h2>
			<p class="hint-text">Create your account. It's free and only
				takes a minute.</p>
			<div class="form-group">
				<label for="id">아이디</label>
				<input type="text" id="id" class="form-control" placeholder="아이디" />
			</div>
			<div class="form-group">
				<button type="button" id="id_check" class="btn btn-secondary">중복확인</button>
			</div>
			<div class="form-group">
				<label for="name">이름</label>
				<input type="text" id="name" class="form-control" placeholder="이름" />
				<span id="username_check"></span>
			</div>
			<div class="form-group">
				<label for="nickname">닉네임</label>
				<input type="text" id="nickname" class="form-control" placeholder="닉네임" />
			</div>
			<div class="form-group">
				<button type="button" id="nick_check" class="btn btn-secondary">중복확인</button>
			</div>
			<div class="form-group">
				<label for="mail">이메일</label>
				<input type="email" id="mail" class="form-control" placeholder="이메일" />
			</div>
			<div class="form-group div_auth" style="display: none;">
				<label for="auth_code">인증코드</label> <input type="text"
					id="auth_code" class="form-control"> <br>
				<button type="button" id="auth_check" class="btn btn-secondary">확인하기</button>
			</div>
			<div class="form-group">
				<button type="button" id="mail_check" class="btn btn-secondary">인증하기</button>
			</div>
			<div class="form-group">
				<label for="pwd">비밀번호</label>
				<input type="password" id="pwd" class="form-control" placeholder="비밀번호" />
				<span id="password_check"></span>
			</div>
			<div class="form-group">
				<label for="con_pwd">비밀번호 재확인</label>
				<input type="password" class="form-control" id="con_pwd" placeholder="비밀번호 확인">
				<span id="password_con_check"></span>
			</div>
			<div class="form-group">
				<button type="button" class="btn btn-primary btn-lg btn-block" onclick="form_check();">회원가입</button>
			</div>
			<div class="text-center">
				Already have an account? <a href="<c:url value='/member/login'/>">로그인</a>
			</div>
		</form>
	</div>
	
	<form id="JoinForm" method="post">
		<input type="hidden" id="user_id" name="user_id"/>
		<input type="hidden" id="user_name" name="user_name"/>
		<input type="hidden" id="user_nickname" name="user_nickname" />
		<input type="hidden" id="user_mail" name="user_mail"/>
		<input type="hidden" id="user_pwd" name="user_pwd"/>
	</form>
</body>

<script>
	
	var idFlag = false;
	var nameFlag = false;
	var nickFlag = false;
	var mailFlag = false;
	var pwFlag = false;
	var pw_con_Flag = false;
	
	var text_check = function (input, button, is, flag, effect_text) {
		
		// 중복확인 대상이 되는 input 태그
		var input_value = input.val();
		// 중복확인 버튼 태그
		var button_text = button.text();
		
		if ( button_text == "다시입력"){
			
			input.removeAttr("readonly");
			button.text("중복확인");
			button.attr("class", "btn btn-danger");
			flag = false;
			
			return false;
			
		} else if( button_text == "중복확인" || button_text == "인증하기"){
			
			if(input_value == ""){
				
				alert("공백은 입력할 수 없습니다.");
				return false;			
			} 
			
			if(!is.test(input_value)){
				
				alert(effect_text);
				return false;
			}
			
			return true;
		}
		
	};
	
	// 아이디 유효성 검사
	$("#id_check").click(function() {
		
		// 아이디 input 태그
		var input_tag = $("#id");
		// 아이디 중복확인 button 태그
		var button_tag = $("#id_check");
		// 아이디 정규식
		var isID = /^[a-z0-9][a-z0-9_\-]{4,19}$/;
		var effect_text = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.";
		
		var result = text_check(input_tag, button_tag, isID, idFlag, effect_text);
		
		if(result == true){
			
			var user_id = input_tag.val();
			
			$.ajax({
				type: "POST",
				url: "/member/idCheck",
				data : { user_id : user_id },
				success: function(data) {
					
					if(data == 1){
						
						alert("이미 사용중인 아이디 입니다.");
						
					} else {
						
						if(confirm("사용가능한 아이디 입니다. 사용하시겠습니까?")){
							input_tag.attr("readonly", "true");
							button_tag.text("다시입력");
							button_tag.attr("class", "btn btn-success");
							idFlag = true;
						}
					}
				},
				error: function(error) {
					alert("오류 발생! 관리자에게 문의 바랍니다.");
				}
			});
			
		}
	});
	
	
	// 닉네임 유효성 검사 및 중복확인
	$("#nick_check").click(function() {
		
		// 닉네임 input 태그
		var input_tag = $("#nickname");
		// 닉네임 중복확인 button 태그
		var button_tag = $("#nick_check");
		// 닉네임 정규식
		var isNICK = /^([a-zA-Z0-9|가-힣]).{1,10}$/;
		var effect_text = "2~10자의 한글, 영문, 숫자만 사용할 수 있습니다.";
		
		var result = text_check(input_tag, button_tag, isNICK, nickFlag, effect_text);
		
		if(result){
			
			var user_nickname = input_tag.val();
			
			$.ajax({
				type: "POST",
				url: "/member/nickCheck",
				data : { user_nickname : user_nickname },
				success: function(data) {
					if(data == 1){
						
						alert("이미 사용중인 닉네임 입니다.");
						
					} else{
						
						if(confirm("사용가능한 닉네임 입니다. 사용하시겠습니까?")){
							input_tag.attr("readonly", "true");
							button_tag.text("다시입력");
							button_tag.attr("class", "btn btn-success");
							nickFlag = true;
						}
					}
				},
				error: function(error) {
					alert("오류 발생! 관리자에게 문의 바랍니다.");
				}
			});
			
		}
		
		
	});

	// 메일 유효성 검사 및 중복확인
	$("#mail_check").click(function() {

		// 메일 input 태그
		var input_tag = $("#mail");
		// 메일 중복확인 button 태그
		var button_tag = $("#mail_check");
		// 정규식
		var isMAIL = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		var effect_text = "메일 형식에 맞지 않습니다.";
		
		var result = text_check(input_tag, button_tag, isMAIL, mailFlag, effect_text);
			
		if(result){
			
			var user_mail = input_tag.val();
			
			$.ajax({
				type: "POST",
				url: "/member/mailCheck",
				data : { user_mail : user_mail },
				success: function(data) {
					if(data == 1){
						
						alert("이미 사용중인 메일입니다.");
						
					} else if(data == 0){
							
						alert("인증코드가 전송되었습니다.");
						
						input_tag.attr("readonly", "true");
						button_tag.hide();
						$(".div_auth").show( 1000 );
						
					} else {
						alert("이메일 보내기 실패, 관리자에게 문의 바랍니다.");
					}
				},
				error: function(error) {
					alert("에러 발생! 관리자에게 문의 바랍니다.");
				}
			});
		} 
	});
	
	// 메일 인증코드 확인하기
	$("#auth_check").click(function() {
		var auth_code = $("#auth_code").val();
		
		if( auth_code == ""){
			alert("인증코드를 입력해주세요!");
			return false;
		}
		
		$.ajax({
			type:"POST",
			url: "/member/authCheck",
			data: {auth_code : auth_code},
			success: function(data){
				if(data == 1){
						
					alert("인증완료!");

					$("#auth_code").val('');
					$(".div_auth").hide(1000);
						
					$("#mail_check").text("다시입력");
					$("#mail_check").attr("class", "btn btn-success");
					$("#mail_check").show(); 
						
					mailFlag = true;
						
				} else {
					alert("인증번호가 일치하지 않습니다.");
				}
			},
			error: function(error){
				alert("관리자에게 문의 바랍니다!");
			}
				
		});
			
	})
	
	// 이름 유효성 체크
	$("#name").blur(function(){
		
		// 한글 또는 영문 사용하기(혼용X)
		var isName = /^[가-힣]{2,6}$/;
		var name = $("#name").val();
		
		if (isName.test(name)){
			
			$("#username_check").css("color", "green");
			$("#username_check").text("멋진 이름이네요!");
			nameFlag = true;
			
		}
		else {
			
			$("#username_check").css("color", "red");
			$("#username_check").text("이름은 한글로 2~6자 내로 입력해주세요!");
			nameFlag = false;
			
		}		
		
		
	})
	
	// 비밀번호 유효성 검증
	$("#pwd").blur(function() {
		
		// 영문, 숫자 조합의 8~20자리
		var isPw = /^[a-zA-Z0-9]{8,20}$/;
		var pwd = $("#pwd").val();
		
		if (isPw.test(pwd)){
			
			$("#password_check").css("color", "green");
			$("#password_check").text("비밀번호 사용 가능!");
			pwFlag = true;
		}
		else {
			
			$("#password_check").css("color", "red");
			$("#password_check").text("숫자와 영문자 조합으로 8~20자리를 사용해야 합니다.");
			pwFlag = false;
		}
		
	})
	
	// 비밀번호 확인란 체크
	$("#con_pwd").blur(function(){
		
		var pwd = $("#pwd").val();
		var con_pwd = $("#con_pwd").val();

		if( pwd == con_pwd){
			$("#password_con_check").css("color", "green");
			$("#password_con_check").html("비밀번호 확인 완료!");
			pw_con_Flag = true;
		} else {
			$("#password_con_check").css("color", "red");
			$("#password_con_check").text("비밀번호를 확인해주세요.");
			pw_con_Flag = false;
		}
	});
	
	
	// 폼 데이터값 확인
	function form_check() {
		
		var id = $("#id").val();
		var name = $("#name").val();
		var nickname = $("#nickname").val();
		var mail = $("#mail").val();
		var pwd = $("#pwd").val();
		

		if ( id == "" || name == "" || nickname == "" || mail == "" || pwd == "") {

			alert("입력되지 않은 정보가 존재합니다.");
			return false;
		}		
			
		if (idFlag && nameFlag && nickFlag && mailFlag && pwFlag && pw_con_Flag) {
			
			var confirm_check = confirm("현재 입력된 정보로 가입하시겠습니까?");
			
			if(confirm_check == true){
				
				submitEncryptedForm(id, name, nickname, mail, pwd);
				return false;
			}

		} 
		
		else {
			alert("중복확인 및 메일 인증코드를 다시 확인해주세요!");
			return false;
		}
		
	}
	
	function submitEncryptedForm(id, name, nickname, mail, pwd){
		
		// RSA 암호화 생성
		var rsa = new RSAKey();
		rsa.setPublic($("#RSAModulus").val(), $("#RSAExponent").val());
		
		// 암호화된 폼 데이터
		var en_id = rsa.encrypt(id);
		var en_name = rsa.encrypt(name);
		var en_nickname = rsa.encrypt(nickname);
		var en_mail = rsa.encrypt(mail);
		var en_pwd = rsa.encrypt(pwd);
		
		
		
		$("#user_id").val(en_id);
		$("#user_name").val(en_name);
		$("#user_nickname").val(en_nickname);
		$("#user_mail").val(en_mail);
		$("#user_pwd").val(en_pwd);
		
		console.log(en_id);
		console.log(en_name);
		console.log(en_nickname);
		console.log(en_mail);
		console.log(en_pwd);
		
		$("#JoinForm").submit();
	}


</script>

</html>