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
	
	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	
	<link href="<c:url value='/css/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
	<link href="<c:url value='/css/join.css'/>" rel="stylesheet">
	
</head>
<body>
	<div class="signup-form">
	    <form:form commandName="memberVO" id="join_form" method="post" onsubmit="return form_check();">
			<h2>회원가입</h2>
			<p class="hint-text">Create your account. It's free and only takes a minute.</p>
			<div class="form-group">
	        	<label for="user_id">아이디</label>
				<form:input  path="user_id" id="user_id" class="form-control" placeholder="아이디" />
				<form:errors path="user_id"  />
	        </div>
	        <div class="form-group">
				<button type="button" id="id_check" class="btn btn-secondary">중복확인</button>  
	        </div>	        
	        <div class="form-group">
	        	<label for="user_name">이름</label>
				<form:input path="user_name" id="user_name" class="form-control" placeholder="이름"/>
				<form:errors path="user_name"/>
	        </div>
	        <div class="form-group">
	        	<label for="user_nickname">닉네임</label>
				<form:input  path="user_nickname" id="user_nickname" class="form-control" placeholder="닉네임"/>
				<form:errors path="user_nickname" />
	        </div>
	        <div class="form-group">
				<button type="button" id="nick_check" class="btn btn-secondary">중복확인</button>	        
	        </div>	        
	        <div class="form-group">
			    <label for="user_email">이메일</label>
				<form:input  type="email" path="user_mail" id="user_mail" class="form-control" placeholder="이메일"/>
				<form:errors path="user_mail"/>
	        </div>
	        <div class="form-group div_auth" style="display: none;">
	        	<label for="auth_code">인증코드</label>
	        	<input type="text" id="auth_code" class="form-control">
	        	<br>
	        	<button type="button" class="btn btn-secondary">확인하기</button>
	        </div>
	        <div class="form-group">
				<button type="button" id="email_check" class="btn btn-secondary">인증하기</button>	        
	        </div>
			<div class="form-group">
				<label for="user_pwd">비밀번호</label>
	            <form:password path="user_pwd" id="user_pwd" class="form-control" placeholder="비밀번호" />
	            <form:errors path="user_pwd"/>
	        </div>
			<div class="form-group">
				<label for="user_con_pwd">비밀번호 재확인</label>
	            <input type="password" class="form-control" id="user_con_pwd" placeholder="비밀번호 확인">
           		<span id="password_con_check"></span>	            
	        </div>
			<div class="form-group">
	            <button type="submit" class="btn btn-primary btn-lg btn-block">회원가입</button>
	        </div>
	    </form:form>
		<div class="text-center">
			Already have an account? <a href="<c:url value='/member/login'/>">로그인</a>
		</div>
	</div>
</body>

<script>
	
	var idFlag = false;
	var nickFlag = false;
	var mailFlag = false;
	var pwFlag = false;
	var pw_chFlag = false;
	
	let flag_map = new Map();
	flag_map
	
	// 아이디 유효성 검사
	$("#id_check").click(function() {
		
		// 중복확인 or 다시입력 텍스트 확인을 위한 구문
		var id_text = $("#id_check").html();
		
		if( id_text == "다시입력"){
			
			$("#user_id").removeAttr("readonly");
			$("#id_check").html("중복확인");
			idFlag = false;
			
		} else if( id_text == "중복확인"){
			
			// 아이디 유효성 검사
			var isID = /^[a-z0-9][a-z0-9_\-]{4,19}$/;
			var user_id = $("#user_id").val();
			
			if(user_id == ""){
				alert("아이디를 입력해주세요.");
				return false;
			}
			
			if(!isID.test(user_id))	{
				alert("5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
	            return false;
			}
			
			$("#user_id").attr("readonly", "true");
			$("#id_check").html("다시입력");
			
	// 		$.ajax({
	// 			type: "POST",
	// 			url: "/member/idCheck",
	// 			data : { user_mail : user_mail },
	// 			success: function(data) {
					
	// 				if(data == 1){
	// 					alert("이미 사용중인 아이디 입니다.");
	// 					idFlag = false;
	// 				} else{
						
	// 					if(confirm("사용가능한 닉네임 입니다. 사용하시겠습니까?")){
	// 						$("#user_id").attr("readonly", "true");
	// 					}
						
	// 					idFlag = true;
	// 				}
	// 			},
	// 			error: function(error) {
	// 				alert("오류 발생! 관리자에게 문의 바랍니다.");
	// 			}
	// 		});
			
		} else {
			alert("오류 발생! 관리자에게 문의바랍니다.");
			return false;
		}
		
	});
	
	
	// 닉네임 유효성 검사 및 중복확인
	$("#nick_check").click(function() {
		
		// 중복확인 or 다시입력 텍스트 확인을 위한 구문
		var nick_text = $("#nick_check").html();
		
		alert(nick_text);
		
		if(nick_text == "다시입력"){
			
			$("#user_nickname").removeAttr("readonly");
			$("#nick_check").html("중복확인");	
			nickFlag = false;
			
			return false;
			
		} else if( nick_text == "중복확인"){
			
			var isNICK = /[0-9]|[a-z]|[A-Z]|[가-힣]/;
			var user_nickname = $("#user_nickname").val();
			
			if(user_nickname == ""){
				alert("닉네임을 입력해주세요.");
				return false;
			}
			
			if(!isNICK.test(user_nickname)){
				alert("2~10자의 한글, 영문, 숫자만 사용할 수 있습니다.");
				return false;
			}
			
			$("#user_nickname").attr("readonly", "true");
			$("#nick_check").html("다시입력");
			
// 			$.ajax({
// 				type: "POST",
// 				url: "/member/nickCheck",
// 				data : { user_mail : user_mail},
// 				success: function(data) {
					
// 					if(data == 1){
// 						alert("이미 사용중인 메일입니다.");
// 						nickFlag = false;
// 					} else{
						
// 						alert("인증코드가 전송되었습니다.");
// 						$(".div_auth").show( 1000 );
// 						nickFlag = true;
// 					}
// 				},
// 				error: function(error) {
// 					alert("오류 발생! 관리자에게 문의 바랍니다.");
// 				}
// 			});

		} else {
			
			alert("오류발생! 관리자에게 문의바랍니다.");
			return false;
			
		}
		
	});

	
	$("#email_check").click(function() {
		var isMAIL = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		
		
		var user_mail = $("#user_mail").val();
		
// 		$.ajax({
// 			type: "POST",
// 			url: "/member/mailCheck",
// 			data : { user_mail : user_mail},
// 			success: function(data) {
				
// 				if(data == 1){
// 					alert("이미 사용중인 메일입니다.");
// 					mailFlag = false;
// 				} else{
					
// 					alert("인증코드가 전송되었습니다.");
// 					$(".div_auth").show( 1000 );
// 					mailFlag = true;
// 				}
// 			},
// 			error: function(error) {
// 				alert("오류 발생! 관리자에게 문의 바랍니다.");
// 			}
// 		});
		
		alert("인증코드가 전송되었습니다.");
		$(".div_auth").show( 1000 );
	});
	
	
	
	// 비밀번호 확인란 체크
	$("#user_con_pwd").blur(function(){
		
		var password = $("#user_pwd").val();
		var confirm_password = $("#user_con_pwd").val();

		if( password == confirm_password){
			$("#password_con_check").css("color", "green");
			$("#password_con_check").html("패스워드 확인 완료!");
			pw_chFlag = true;
			
		} else {
			$("#password_con_check").css("color", "red");
			$("#password_con_check").html("비밀번호를 확인해주세요.");
			pw_chFlag = false;
			
		}

	});
	
	function form_check() {
		
		let myMap = new Map()
		myMap.set(0, 'zero')
		myMap.set(1, 'one')
		
		myMap.forEach(function(value, key) {
			console.log(key + ' = ' + value)
		})
		
		myMap.set(0,'zzzz')
		myMap.set(1,'oooo')
		
		myMap.forEach(function(value, key){
			console.log(key + ' = ' + value)
		})
		
		return false;
	}
	
	
	
// 	// naver
//     //region define, setter
//     var idFlag = false;
//     var pwFlag = false;
//     var authFlag = false;
//     var submitFlag = false;

//     var properties = {
//         keyboard: [{id:"id"}, {id:"pswd1", secureMode:true}, {id:"pswd2", secureMode:true}],
//         modeProperties: {
//             mode: 4
//         }
//     };
//     var desk = new sofa.Koop(properties);

//     $(document).ready(function() {
//         defaultScript();

//         if ($("#yy").val() != "") {
//             checkBirthday();
//         }

//         //region unreal id
//         $("#id").blur(function() {
//             idFlag = false;
//             checkId("first");
//         });

//         $("#pswd1").blur(function() {
//             pwFlag = false;
//             checkPswd1();
//         }).keyup(function(event) {
//             checkShiftUp(event);
//         }).keypress(function(event) {
//             checkCapslock(event);
//         }).keydown(function(event) {
//             checkShiftDown(event);
//         });

//         $("#pswd2").blur(function() {
//             checkPswd2();
//         }).keyup(function(event) {
//             checkShiftUp(event);
//         }).keypress(function(event) {
//             checkCapslock2(event);
//         }).keydown(function(event) {
//             checkShiftDown(event);
//         });

//         $("#name").blur(function() {
//             checkName();
//         });

//         $("#name1").blur(function() {
//             checkName();
//         });

//         $("#name2").blur(function() {
//             checkName();
//         });

//         $("#yy").blur(function() {
//             checkBirthday();
//         });

//         $("#mm").change(function() {
//             checkBirthday();
//         });

//         $("#dd").blur(function() {
//             checkBirthday();
//         });

//         $("#gender").change(function() {
//             checkGender();
//         });

//         $("#email").blur(function() {
//             checkEmail();
//         });

//         $("#phoneNo").blur(function() {
//             checkPhoneNo();
//         });

//         $("#btnSend").click(function() {
//             sendSmsButton();
//             return false;
//         });

//         $("#authNo").blur(function() {
//             authFlag = false;
//             checkAuthNo();
//         });

//         $("#tabPrtsMobile").click(function() {
//             showJuniverMobileTab();
//             return false;
//         });

//         $("#tabPrtsIpin").click(function() {
//             showJuniverIpinTab();
//             return false;
//         });
//         //endregion

//         //region prts mobile
//         $("#pagree_all").click(function() {
//             setPrtsTerms();
//         })
//         $("#pagree_01").click(function() {
//             viewPrtsTerms();
//         })
//         $("#pagree_02").click(function() {
//             viewPrtsTerms();
//         })
//         $("#pagree_03").click(function() {
//             viewPrtsTerms();
//         })
//         $("#pagree_04").click(function() {
//             viewPrtsTerms();
//         })
//         $("#pagree_05").click(function() {
//             viewPrtsTerms();
//         })

//         $("#pname").blur(function() {
//             checkPrtsName();
//         });

//         $("#pyy").blur(function() {
//             checkPrtsBirthday();
//         });

//         $("#pmm").change(function() {
//             checkPrtsBirthday();
//         });

//         $("#pdd").blur(function() {
//             checkPrtsBirthday();
//         });

//         $("#pgender").change(function() {
//             checkPrtsGender();
//         });

//         $("#pphoneNo").blur(function() {
//             checkPrtsPhoneNo();
//         });

//         $("#btnPrtsSend").click(function() {
//             sendPrtsSmsButton();
//             return false;
//         })

//         $("#pauthNo").blur(function() {
//             authFlag = false;
//             checkPrtsAuthNo();
//         });
//         //endregion

//         //region ipin popup
//         $("#iagree_all").click(function() {
//             checkIpinAgree();
//         });

//         $("#btnIpinPopup").click(function() {
//             openIpinPopup();
//         });

//         $("#iphoneNo").blur(function() {
//             checkIpinPhoneNo();
//         });

//         $("#btnIpinSend").click(function() {
//             sendIpinSmsButton();
//             return false;
//         })

//         $("#iauthNo").blur(function() {
//             authFlag = false;
//             checkIpinAuthNo();
//         });
//         //endregion

//         $("#btnJoin").click(function(event) {
//             clickcr(this, 'sup.signup', '', '', event);
//             submitClose();
//             if(idFlag && pwFlag && authFlag) {
//                 mainSubmit();
//             } else {
//                 setTimeout(function() {
//                     mainSubmit();
//                 }, 700);
//             }
//         });

//     });
//     //endregion

//     //region mainSubmit
//     function mainSubmit() {
//         var joinMode = $("#joinMode").val();

//         if(joinMode == "juniverMobile") {
//             if (!checkPrtsMobileInput()) {
//                 submitOpen();
//                 return false;
//             }
//         } else if(joinMode == "juniverIpin") {
//             if (!checkPrtsIpinInput()) {
//                 submitOpen();
//                 return false;
//             }
//         } else {
//             if (!checkUnrealInput()) {
//                 submitOpen();
//                 return false;
//             }
//         }

//         if(idFlag && pwFlag && authFlag) {
//             try {
//                 desk.f(function(a) {
//                     $("#nid_kb2").val(a);
//                     $("#join_form").submit();
//                 });
//             } catch (e) {
//                 $("#nid_kb2").val("join v2 error: " + e.name + ", " + e.message);
//                 $("#join_form").submit();
//             }
//         } else {
//             submitOpen();
//             return false;
//         }
//     }

//     function checkUnrealInput() {
//         if (checkId('join')
//                 & checkPswd1()
//                 & checkPswd2()
//                 & checkName()
//                 & checkBirthday()
//                 & checkGender()
//                 & checkEmail()
//                 & checkPhoneNo()
//                 & checkAuthNo()
//         ) {
//             return true;
//         } else {
//             return false;
//         }
//     }

//     function checkPrtsMobileInput() {
//         if (checkId('join')
//                 & checkPswd1()
//                 & checkPswd2()
//                 & checkName()
//                 & checkBirthday()
//                 & checkGender()
//                 & checkEmail()
//                 & checkPrtsAgree()
//                 & checkPrtsName()
//                 & checkPrtsBirthday()
//                 & checkPrtsGender()
//                 & checkPrtsPhoneNo()
//                 & checkPrtsAuthNo()
//         ) {
//             return true;
//         } else {
//             return false;
//         }
//     }

//     function checkPrtsIpinInput() {
//         if (checkId('join')
//                 & checkPswd1()
//                 & checkPswd2()
//                 & checkName()
//                 & checkBirthday()
//                 & checkGender()
//                 & checkEmail()
//                 & checkIpinAgree()
//                 & checkIpinPopup()
//                 & checkIpinPhoneNo()
//                 & checkIpinAuthNo()
//         ) {
//             return true;
//         } else {
//             return false;
//         }
//     }
//     //endregion

//     //region unreal 가입
//     function checkId(event) {
//         if(idFlag) return true;

//         var id = $("#id").val();
//         var oMsg = $("#idMsg");
//         var oInput = $("#id");

//         if ( id == "") {
//             showErrorMsg(oMsg,"필수 정보입니다.");
//             setFocusToInputObject(oInput);
//             return false;
//         }

//         var isID = /^[a-z0-9][a-z0-9_\-]{4,19}$/;
//         if (!isID.test(id)) {
//             showErrorMsg(oMsg,"5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
//             setFocusToInputObject(oInput);
//             return false;
//         }

//         idFlag = false;
//         $.ajax({
//             type:"GET",
//             url: "/user2/joinAjax.nhn?m=checkId&id=" + id ,
//             success : function(data) {
//                 var result = data.substr(4);

//                 if (result == "Y") {
//                     if (event == "first") {
//                         showSuccessMsg(oMsg, "멋진 아이디네요!");
//                     } else {
//                         hideMsg(oMsg);
//                     }
//                     idFlag = true;
//                 } else {
//                     showErrorMsg(oMsg, "이미 사용중이거나 탈퇴한 아이디입니다.");
//                     setFocusToInputObject(oInput);
//                 }
//             }
//         });
//         return true;
//     }

//     function checkPswd1() {
//         if(pwFlag) return true;

//         var id = $("#id").val();
//         var pw = $("#pswd1").val();
//         var oImg = $("#pswd1Img");
//         var oSpan = $("#pswd1Span");
//         var oMsg = $("#pswd1Msg");
//         var oInput = $("#pswd1");

//         if (pw == "") {
//             showErrorMsg(oMsg,"필수 정보입니다.");
//             setFocusToInputObject(oInput);
//             return false;
//         }
//         if (isValidPasswd(pw) != true) {
//             showPasswd1ImgByStep(oImg, oSpan, 1);
//             showErrorMsg(oMsg,"8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
//             setFocusToInputObject(oInput);
//             return false;
//         }

//         pwFlag = false;
//         $.ajax({
//             type:"GET",
//             url: "/user2/joinAjax.nhn?m=checkPswd&id=" + escape(encodeURIComponent(id)) + "&pw=" + escape(encodeURIComponent(pw)) ,
//             success : function(data) {
//                 var result = data.substr(4);
//                 if (result == 1) {
//                     showPasswd1ImgByStep(oImg, oSpan, 1);
//                     showErrorMsg(oMsg,"8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
//                     setFocusToInputObject(oInput);
//                     return false;
//                 } else if (result == 2) {
//                     showPasswd1ImgByStep(oImg, oSpan, 2);
//                     showErrorMsg(oMsg,"8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
//                     setFocusToInputObject(oInput);
//                 } else if (result == 3) {
//                     showPasswd1ImgByStep(oImg, oSpan, 3);
//                     oMsg.hide();
//                 } else if (result == 4) {
// //                     showPasswd1ImgByStep(oI…

</script>

</html>