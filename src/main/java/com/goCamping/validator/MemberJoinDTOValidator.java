package com.goCamping.validator;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.goCamping.dto.MemberJoinDTO;

public class MemberJoinDTOValidator implements Validator {
	
	private static final String isId = "^[a-z0-9][a-z0-9_\\\\-]{4,19}$";
	private static final String isName = "^[가-힣]{2,6}$";
	private static final String isNick = "^([a-zA-Z0-9|가-힣]).{1,10}$";
	private static final String isPw = "^[a-zA-Z0-9]{8,20}$";
	private static final String isMail = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
			"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
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
		Map<String, String> map = new HashMap<String, String>();
		// 정규식 검사를 위해, 정규식 표현을 담기 위해 사용
		Map<String, String> RegExp_Map = new HashMap<String, String>();
		
		// 현재 객체의 데이터를 저장
		map.put("user_id", memberJoinDTO.getUser_id());
		map.put("user_name", memberJoinDTO.getUser_name());
		map.put("user_nickname", memberJoinDTO.getUser_nickname());
		map.put("user_mail", memberJoinDTO.getUser_mail());
		map.put("user_pwd", memberJoinDTO.getUser_pwd());
		
		// 정규식 표현을 저장
		RegExp_Map.put("user_id", isId);
		RegExp_Map.put("user_name", isName);
		RegExp_Map.put("user_nickname", isNick);
		RegExp_Map.put("user_mail", isMail);
		RegExp_Map.put("user_pwd", isPw);
		
		// map에 담긴 데이터 현재 전달 받은 객체 필드를 key로 필드에 저장된 데이터를 value 값으로 가지고 있다.
		// key ( 객체 필드명 ) : value ( 필드에 담긴 데이터 )
		for(String key : map.keySet()) {
			
			// 현재 필드 null 값 또는 공백의 값인지 체크
			if(map.get(key) == null || map.get(key).trim().isEmpty()) {
				// null 또는 공백일 경우 에러코드 저장
				errors.rejectValue(key, "NotBlank");
			} 
			else {
				// 정규식 표현에 맞는 데이터 인지 검사
				if(!Pattern.matches(RegExp_Map.get(key), map.get(key))) {
					// 정규식 표현에 맞지 않으면 에러코드 저장
					errors.rejectValue(key, "effect." + key);
				}
			}
		}
	}
		
}
