package com.goCamping.validator;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.goCamping.dto.MemberJoinDTO;

public class MemberJoinDTOValidator extends MemberValidator {
	
	// Validator가 검증할 수 있는 타입인지 검사
	@Override
	public boolean supports(Class<?> clazz) {
		return MemberJoinDTO.class.isAssignableFrom(clazz);
	}

	// 첫 번째 파라미터로 전달받은 객체를 검증하고 오류 결과를 Errors에 담는 기능을 한다.
	@Override
	public void validate(Object target, Errors errors) {
		
		MemberJoinDTO memberJoinDTO = ( MemberJoinDTO ) target;
		
		// 전달받은 target 객체의 데이터 담기 위해 사용
		Map<String, String> memberJoinDTO_Map = new HashMap<String, String>();
		// 정규식 검사를 위해, 정규식 표현을 담기 위해 사용
		Map<String, String> RegExp_Map = new HashMap<String, String>();
		
		// 현재 객체의 데이터를 저장
		memberJoinDTO_Map.put("user_id", memberJoinDTO.getUser_id());
		memberJoinDTO_Map.put("user_name", memberJoinDTO.getUser_name());
		memberJoinDTO_Map.put("user_nickname", memberJoinDTO.getUser_nickname());
		memberJoinDTO_Map.put("user_mail", memberJoinDTO.getUser_mail());
		memberJoinDTO_Map.put("user_pwd", memberJoinDTO.getUser_pwd());
		
		// 정규식 표현을 저장
		RegExp_Map.put("user_id", isId);
		RegExp_Map.put("user_name", isName);
		RegExp_Map.put("user_nickname", isNick);
		RegExp_Map.put("user_mail", isMail);
		RegExp_Map.put("user_pwd", isPw);
		
		// map에 담긴 데이터 현재 전달 받은 객체 필드를 key로 필드에 저장된 데이터를 value 값으로 가지고 있다.
		// key ( 객체 필드명 ) : value ( 필드에 담긴 데이터 )
		for(String key : memberJoinDTO_Map.keySet()) {
			
			// 현재 필드 null 값 또는 공백의 값인지 체크
			if(memberJoinDTO_Map.get(key) == null || memberJoinDTO_Map.get(key).trim().isEmpty()) {
				// null 또는 공백일 경우 에러코드 저장
				errors.rejectValue(key, "NotBlank");
			} 
			else {
				// 정규식 표현에 맞는 데이터 인지 검사
				if(!Pattern.matches(RegExp_Map.get(key), memberJoinDTO_Map.get(key))) {
					// 정규식 표현에 맞지 않으면 에러코드 저장
					errors.rejectValue(key, "effect." + key);
				}
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "auth_code", "NotBlank");
	}
		
}
