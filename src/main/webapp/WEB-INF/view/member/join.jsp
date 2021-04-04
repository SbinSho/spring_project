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

</head>
<body>
	<div class="signup-form">
		<form:form commandName="memberDTO">
			<input type="hidden" id="RSAModulus" value="${RSAModulus}" /><!-- 서버에서 전달한값을 셋팅한다. -->
			<input type="hidden" id="RSAExponent" value="${RSAExponent}" /><!-- 서버에서 전달한값을 셋팅한다. -->
			<h2>회원가입</h2>
			<p class="hint-text">Create your account. It's free and only
				takes a minute.</p>
			<div class="form-group">
				<label for="user_id">아이디</label>
				<form:input path="user_id" id="user_id" class="form-control" placeholder="아이디" />
				<form:errors path="user_id" />
			</div>
			<div class="form-group">
				<button type="button" id="id_check" class="btn btn-secondary">중복확인</button>
			</div>
			<div class="form-group">
				<label for="user_name">이름</label>
				<form:input path="user_name" id="user_name" class="form-control" placeholder="이름" />
				<form:errors path="user_name"/>
				<span id="username_check"></span>
			</div>
			<div class="form-group">
				<label for="user_nickname">닉네임</label>
				<form:input path="user_nickname" id="user_nickname" class="form-control" placeholder="닉네임" />
				<form:errors path="user_nickname" />
			</div>
			<div class="form-group">
				<button type="button" id="nick_check" class="btn btn-secondary">중복확인</button>
			</div>
			<div class="form-group">
				<label for="user_email">이메일</label>
				<form:input type="email" path="user_mail" id="user_mail" class="form-control" placeholder="이메일" />
				<form:errors path="user_mail" />
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
				<label for="user_pwd">비밀번호</label>
				<form:password path="user_pwd" id="user_pwd" class="form-control" placeholder="비밀번호" />
				<form:errors path="user_pwd" />
				<span id="password_check"></span>
			</div>
			<div class="form-group">
				<label for="user_con_pwd">비밀번호 재확인</label> <input type="password" class="form-control" id="user_con_pwd" placeholder="비밀번호 확인">
				<span id="password_con_check"></span>
			</div>
			<div class="form-group">
				<button type="button" class="btn btn-primary btn-lg btn-block" onclick="form_check();">회원가입</button>
			</div>
			<div class="text-center">
				Already have an account? <a href="<c:url value='/member/login'/>">로그인</a>
			</div>
		</form:form>
	</div>
	
	<form id="JoinForm" method="post">
		<input type="hidden" id="en_userID" name="en_userID"/>
		<input type="hidden" id="en_userName" name="en_userName"/>
		<input type="hidden" id="en_userNickName" name="en_userNickName" />
		<input type="hidden" id="en_userMail" name="en_userMail"/>
		<input type="hidden" id="en_userPwd" name="en_userPwd"/>
	</form>
</body>

<!-- RSA 자바스크립트 라이브러리 -->
<script type="text/javascript" src="/js/rsa/jsbn.js"></script>
<script type="text/javascript" src="/js/rsa/rsa.js"></script>
<script type="text/javascript" src="/js/rsa/prng4.js"></script>
<script type="text/javascript" src="/js/rsa/rng.js"></script>

<script>
	
	var idFlag = false;
	var nameFlag = false;
	var nickFlag = false;
	var mailFlag = false;
	var pwFlag = false;
	var pw_con_Flag = false;
	
	// 아이디 유효성 검사
	$("#id_check").click(function() {
		
		// 중복확인 or 다시입력 텍스트 확인을 위한 구문
		var id_text = $("#id_check").text();
		
		if( id_text == "다시입력"){
			
			$("#user_id").removeAttr("readonly");
			$("#id_check").text("중복확인");
			$("#id_check").attr("class", "btn btn-danger");
			idFlag = false;
			
		} else if( id_text == "중복확인"){
			
			// 아이디 유효성 검사
			var isID = /^[a-z0-9][a-z0-9_\-]{4,19}$/;
			var user_id = $("#user_id").val();
			
			if(user_id == ""){
				alert("아이디를 입력해주세요.");
				return false;
			}
			
			if(isID.test(user_id))	{
				$.ajax({
					type: "POST",
					url: "/member/idCheck",
					data : { user_id : user_id },
					success: function(data) {
						
						if(data == 1){
							
							alert("이미 사용중인 아이디 입니다.");
							
						} else {
							
							if(confirm("사용가능한 닉네임 입니다. 사용하시겠습니까?")){
								$("#user_id").attr("readonly", "true");
								$("#id_check").text("다시입력");
								$("#id_check").attr("class", "btn btn-success");
								idFlag = true;
							}
						}
					},
					error: function(error) {
						alert("오류 발생! 관리자에게 문의 바랍니다.");
					}
				});
			} else {
				alert("5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
			}
		}
		
	});
	
	// 이름 유효성 체크
	$("#user_name").blur(function(){
		
		// 한글 또는 영문 사용하기(혼용X)
		var isName = /^[가-힣]{2,6}$/;
		var user_name = $("#user_name").val();
		
		if (isName.test(user_name)){
			
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
	
	
	// 닉네임 유효성 검사 및 중복확인
	$("#nick_check").click(function() {
		
		// 중복확인 or 다시입력 텍스트 확인을 위한 구문
		var nick_text = $("#nick_check").text();
		
		if(nick_text == "다시입력"){
			
			$("#user_nickname").removeAttr("readonly");
			$("#nick_check").text("중복확인");
			$("#nick_check").attr("class", "btn btn-danger");
			nickFlag = false;
			
		} else if( nick_text == "중복확인"){
			
			var isNICK = /[0-9]|[a-z]|[A-Z]|[가-힣]/;
			var user_nickname = $("#user_nickname").val();
			
			if(user_nickname == ""){
				alert("닉네임을 입력해주세요.");
				return false;
			}
			
			if(isNICK.test(user_nickname)){
				$.ajax({
					type: "POST",
					url: "/member/nickCheck",
					data : { user_nickname : user_nickname },
					success: function(data) {
						if(data == 1){
							alert("이미 사용중인 닉네임 입니다.");
						} else{
							if(confirm("사용가능한 닉네임 입니다. 사용하시겠습니까?")){
								$("#user_nickname").attr("readonly", "true");
								$("#nick_check").text("다시입력");
								$("#nick_check").attr("class", "btn btn-success");
								nickFlag = true;
							}
						}
					},
					error: function(error) {
						alert("오류 발생! 관리자에게 문의 바랍니다.");
					}
				});
			} else {
				alert("2~10자의 한글, 영문, 숫자만 사용할 수 있습니다.");
			}
		} 
		
	});

	// 메일 유효성 검사 및 중복확인
	$("#mail_check").click(function() {
		
		var isMAIL = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		var user_mail = $("#user_mail").val();
		var mail_text = $("#mail_check").text();
		
		if(mail_text == "다시입력"){
			
			$("#user_mail").removeAttr("readonly");
			$("#mail_check").text("인증하기");
			$("#mail_check").attr("class", "btn btn-danger");
			
			mailFlag = false;
			
		} else if(mail_text == "인증하기"){
			
			if(user_mail == ""){
				alert("메일을 입력해주세요.");
				return false;
			}
			
			
			if(isMAIL.test(user_mail)){
// 				$.ajax({
// 					type: "POST",
// 					url: "/member/mailCheck",
// 					data : { user_mail : user_mail },
// 					success: function(data) {
// 						if(data == 1){
							
// 							alert("이미 사용중인 메일입니다.");
							
// 						} else if(data == 0){
							
// 							alert("인증코드가 전송되었습니다.");
							
							$("#user_mail").attr("readonly", "true");
							$("#mail_check").hide();
							$(".div_auth").show( 1000 );
							
// 						} else {
// 							alert("이메일 보내기 실패, 관리자에게 문의 바랍니다.");
// 						}
// 					},
// 					error: function(error) {
// 						alert("에러 발생! 관리자에게 문의 바랍니다.");
// 					}
// 				});
				
			} 
			
			else {
				alert("메일 형식에 맞지 않습니다.");
			}
		}
		
	});
	
	// 메일 인증코드 확인하기
	$("#auth_check").click(function() {
// 		var auth_code = $("#auth_code").val();
			
// 		if( auth_code == ""){
// 			alert("인증코드를 입력해주세요!");
// 			return false;
// 		}
		
// 		$.ajax({
// 			type:"POST",
// 			url: "/member/authCheck",
// 			data: {auth_code : auth_code},
// 			success: function(data){
// 				if(data == 1){
						
					alert("인증완료!");

					$("#auth_code").val('');
					$(".div_auth").hide(1000);
						
					$("#mail_check").text("다시입력");
					$("#mail_check").attr("class", "btn btn-success");
					$("#mail_check").show(); 
						
					mailFlag = true;
						
// 				} else {
// 					alert("인증번호가 일치하지 않습니다.");
// 				}
// 			},
// 			error: function(error){
// 				alert("관리자에게 문의 바랍니다!");
// 			}
				
// 		});
			
	})
	
	
	// 비밀번호 유효성 검증
	$("#user_pwd").blur(function() {
		
		// 영문, 숫자 조합의 8~20자리
		var isPw = /^[a-zA-Z0-9]{8,20}$/;
		var user_pwd = $("#user_pwd").val();
		
		if (isPw.test(user_pwd)){
			
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
	$("#user_con_pwd").blur(function(){
		
		var user_pwd = $("#user_pwd").val();
		var user_con_pwd = $("#user_con_pwd").val();

		if( user_pwd == user_con_pwd){
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
		
		var user_id = $("#user_id").val();
		var user_name = $("#user_name").val();
		var user_nickname = $("#user_nickname").val();
		var user_mail = $("#user_mail").val();
		var user_pwd = $("#user_pwd").val();
		

		if ( user_id == "" || user_name == "" || user_nickname == "" || user_pwd == "") {

			alert("입력되지 않은 정보가 존재합니다.");
			return false;
		}		
			
// 		if (idFlag && nameFlag && nickFlag && mailFlag && pwFlag && pw_con_Flag) {
			
			var confirm_check = confirm("현재 입력된 정보로 가입하시겠습니까?");
			
			if(confirm_check == true){
				
				submitEncryptedForm(user_id, user_name, user_nickname, user_mail, user_pwd);
				
				return false;
			}

// 		} 
		
// 		else {
// 			alert("중복확인 및 메일 인증코드를 다시 확인해주세요!");
// 			return false;
// 		}
		
	}
	
	function submitEncryptedForm(user_id, user_name, user_nickname, user_mail, user_pwd){
		
		// RSA 암호화 생성
		var rsa = new RSAKey();
		rsa.setPublic($("#RSAModulus").val(), $("#RSAExponent").val());
		
		// 암호화된 비밀번호
		var en_id = rsa.encrypt(user_id);
		var en_name = rsa.encrypt(user_name);
		var en_nickname = rsa.encrypt(user_nickname);
		var en_mail = rsa.encrypt(user_mail);
		var en_pwd = rsa.encrypt(user_pwd);
		
		$("#en_userID").val(en_id);
		$("#en_userName").val(en_name);
		$("#en_userNickName").val(en_nickname);
		$("#en_userMail").val(en_mail);
		$("#en_userPwd").val(en_pwd);
		
		$("#JoinForm").submit();
	}


</script>

</html>