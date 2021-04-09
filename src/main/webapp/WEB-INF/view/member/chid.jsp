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
	
	<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
  	<script src="/resources/css/bootstrap/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="signup-form">
    <form:form commandName="memberIdDTO" method="post" onsubmit="return submit_check();">
        <div class="form-group">
        	<label for="userid">현재 아이디</label>
        	<form:input path="userid" id="userid" value="${ userid }" readonly="true" class="form-control" />
        	<form:errors path="userid" />
        </div>

        <div class="form-group">
        	<label for="ch_userid">변경 할 아이디</label>
        	<form:input path="ch_userid" id="ch_userid" value="${ ch_userid }" class="form-control" />
        	<form:errors path="ch_userid" />
        </div>
		<div class="form-group">
            <button type="button" onclick="idCheck();" id="overlap_button" class="btn btn-primary btn-lg btn-block">아이디 중복 검사</button>
        </div>
		<div class="form-group">
            <button type="submit" class="btn btn-primary btn-lg btn-block">아이디 변경하기</button>
        </div>
		<div class="form-group">
            <button type="button" onclick="history.back();" class="btn btn-danger btn-lg btn-block">뒤로가기</button>
        </div>
    </form:form>
</div>

<script type="text/javascript">

	var result = "${result}";

	if(result == "error"){
		alert("잘못된 입력값이 존재합니다. 다시 입력해주세요.\n 증상이 지속 된다면 관리자에게 문의바랍니다.");
	}

	// ID 유효성 검증을 위한 변수
	var vaild_Flag;

	// ID 중복 검사를 위한 변수
	var overlap_Flag;

	// submit 전 처음 중복검사 및 유효성 검사했던 아이디가 맞는지 검사를 위한 변수
	// 중복검사 후 다른 아이디를 입력시 vaild_Flag, overlap_Flag; 이기 때문에 한번더 검사해야함
	var chId_Flag;

	// 아이디 중복 검사
	function idCheck() {
		// naver ID 정규식 검사
		var isId = /^[a-z0-9][a-z0-9_\-]{4,19}$/;
		var ch_userid = $("#ch_userid").val();

		if( ch_userid == ""){
			alert("아이디를 입력해주세요!");
			return false;
		}
		
		if(!(isId.test(ch_userid))){
			
			vaild_Flag = false;
			
			alert("5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
			 
			return false;
		}

		vaild_Flag = true;
		
		$.ajax({
			type : "POST",
			data : { id : ch_userid },
			url : "/member/idCheck",
			dataType: 'json',
			success : function(data){

				if(data == 1){
					
					overlap_Flag = false;
					alert("이미 사용중이거나 탈퇴한 아이디입니다.");
					
				}
				else {
					
					$("#overlap_button").html("중복검사 완료");
					$("#overlap_button").attr('class','btn btn-success btn-lg btn-block');
					overlap_Flag = true;
					
					chId_Flag = $("#ch_userid").val();
					alert("멋진 아이디네요!");
				}
			},
			error : function(error){
				alert("에러 발생 관리자에게 문의바랍니다.");
			}
			
		});
	
	}
	
	function submit_check(){

		var ch_userid = $("#ch_userid").val();
		
		if( vaild_Flag == true && overlap_Flag == true){
			
			// 중복검사 완료후에 사용자가 다시 아이디 변경시 submit 되는걸 방지하기 위한 구문이다.
			// 주석처리 한 이유는 아래에 jquery를 이용하여 텍스트 변경 감지구문을 추가하여
			// 텍스트 변경 발생시 무조건 Flag값들을 false로 지정 및 버튼 색상을 빨간색으로 바꿔 
			// 사용자도 중복 재검사가 필요한 상황을 인지하게함으로 불필요한 코드라고 판단함
			// 오류 발생을 줄이기 위한 목적으로 사용가능 할꺼 같기도하다. 왜냐하면 submit 전에 한번더 체크하기 때문
			/* if( chId_Flag == ch_userid){
				
				return true;
			}
			
			else {
				
				alert("아이디 중복검사를 다시 해주세요!");

				vaild_Flag = false;
				overlap_Flag = false;
				chId_Flag = "";
				
				return false;
			} */

			return true
		}

		alert("아이디 중복검사를 체크해주세요!");
		return false;
		
	}

	// 아이디 중복검사 완료 후 텍스트 변경시 실시간으로 텍스트 변경을 감지하여 다시 중복검사 받게 하기 위한 코드
	$("#ch_userid").on("propertychange change keyup paste input", function(){

		valid_Flag = false;
		overlap_Flag = false;
		chId_Flag = "";
		
		$("#overlap_button").html("아이디 중복검사 필요!")
		$("#overlap_button").attr('class','btn btn-danger btn-lg btn-block');

	});
	
</script>

	
</body>
</html>