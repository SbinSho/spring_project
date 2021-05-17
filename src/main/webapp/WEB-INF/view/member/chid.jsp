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
	<link rel="stylesheet" href="<c:url value='/css/join.css'/>">
	
	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
  	<script src="/css/bootstrap/bootstrap.bundle.min.js"></script>
  	
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
        <div class="form-group">
        	<label for="id">현재 아이디</label>
        	<input id="id" value="${ user_id }" readonly class="form-control" />
        </div>

        <div class="form-group">
        	<label for="ch_id">변경 할 아이디</label>
        	<input id="ch_id" class="form-control" />
        	<span id="id_check"></span>
        </div>
		<div class="form-group">
            <button type="button" class="btn btn-primary btn-lg btn-block" onclick="form_check()">아이디 변경하기</button>
        </div>
		<div class="form-group">
            <button type="button" onclick="history.back();" class="btn btn-danger btn-lg btn-block">뒤로가기</button>
        </div>
    </form>
    
    <div style="display: none;">
	    <form>
	    	<input type="hidden" id="user_id">
	    	<input type="hidden" id="ch_user_id">
	    </form>
    </div>
</div>

<script type="text/javascript">

	var Flag = false;
	
	$(document).ready(function() {
		
        var csrfToken = "${_csrf.token}";
        var csrfHeader = "${_csrf.headerName}";
        
	    // ajax 요청하기 전 호출되는 이벤트 ( 토큰값 설정 하기 )
		$(document).ajaxSend(function (e, xhr, options){
			xhr.setRequestHeader(csrfHeader, csrfToken);
		});
	});
	
	// 컨텍스트 경로 반환
	function Path() {
		var contextPath = "${pageContext.request.contextPath}" == "" ? "/" : "${pageContext.request.contextPath}"; 
		return contextPath;
	}

	$("#ch_id").blur(function() {
	
		var user_id = $("#id").val();
		var ch_id = $("#ch_id").val();
		var isId = /^[a-z0-9][a-z0-9_\-]{4,19}$/;
		
		if(!isId.test(ch_id)){
			
			$("#id_check").text("5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
			$("#id_check").css('color', 'red');
			
		} else {
			
			$.ajax({
				type: "POST",
				async : false,
				url:  Path() + "member/check/idCheck",
				data : { value : ch_id },
				success: function(data) {
					if(data == 1){
							
						$("#id_check").text( "중복된 아이디 입니다.");
						$("#id_check").css("color", "red");
									
					} else if( data == 0 ){
									
						$("#id_check").text( "사용가능한 아이디 입니다.");
						$("#id_check").css("color", "green");
						Flag = true;
						
					} else if( data == -1){
						alert("오류 발생! 관리자에게 문의 바랍니다.");
						location.href= Path() + "member/edit/chid?user_id=" + user_id;
					} else{
						alert("중복 확인 실패! 관리자에게 문의 바랍니다.");
					}
				},
				error: function(error) {
					alert("오류 발생! 관리자에게 문의 바랍니다.");
				}
			});
		}
		
	})
	

	// 폼 데이터값 확인
	function form_check() {
		
		var id = $("#id").val();
		var ch_id = $("#ch_id").val();
		
		if ( ch_id == "") {
			alert("입력되지 않은 정보가 존재합니다.");
			return false;
		}
		
		if (Flag) {
			
			var confirm_check = confirm("아이디를 변경하시겠습니까?");
			if(confirm_check == true){
				submitEncryptedForm(id, ch_id);
				return false;
			}
		} 
		
		else {
			alert("중복확인 다시 확인해주세요!");
			return false;
		}
		
	}
	
	// 데이터 암호화 및 서버 전송
	function submitEncryptedForm(user_id, ch_user_id){
		
		// RSA 암호화 생성
		var rsa = new RSAKey();
		rsa.setPublic($("#RSAModulus").val(), $("#RSAExponent").val());
		
		// 암호화된 폼 데이터
		var en_id = rsa.encrypt(user_id);
		var en_chid = rsa.encrypt(ch_user_id);
		
		var form = {
				'user_id' : en_id,
				'ch_id' : en_chid
		}
		
		$.ajax({
			type:"POST",
			url: Path() + "member/edit/chid?user_id=" + user_id,
			data: JSON.stringify(form),
			dataType : "json",
			contentType: "application/json; charset=UTF-8",
			success: function(data){
				if(data.result == "OK"){
					alert("아이디 변경 완료!");
					location.href = Path() + "member/edit/info?user_id=" + ch_user_id;
				}
				else if (data.result == "DB_ERROR"){
					alert("중복확인을 다시 해주세요!");
					DB_ERROR();
				} 
				else if (data.result == "ERROR"){
					alert("오류 발생!");
					location.href= Path() + "member/edit/chid?user_id=" + user_id;
				}
				
			},
			error: function(error){
				alert("관리자에게 문의 바랍니다!");
			}
		});
		
	}
	
	// DB 에러 발생
	function DB_ERROR() {
		
		Flag = false;
		
		$("#id_check").text("재확인 필요");
		$("#id_check").css("color", "red");
		
	}
	
</script>

	
</body>
</html>