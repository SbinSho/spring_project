package com.goCamping.validator;



import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.goCamping.dto.MemberLoginDTO;

public class MemberLoginValidator implements Validator {
	
	private static final String isId = "^[a-z0-9][a-z0-9_\\\\-]{4,19}$";
	private static final String isPw = "^[a-zA-Z0-9]{8,20}$";
	
	// Validator가 검증할 수 있는 타입인지 검사
	@Override
	public boolean supports(Class<?> clazz) {
		return MemberLoginDTO.class.isAssignableFrom(clazz);
	}

	// 첫 번째 파라미터로 전달받은 객체를 검증하고 오류 결과를 Errors에 담는 기능을 한다.
	@Override
	public void validate(Object target, Errors errors) {
		
		MemberLoginDTO memberLoginDTO = (MemberLoginDTO) target;
		
		if(memberLoginDTO.getUser_id() == null || memberLoginDTO.getUser_id().trim().isEmpty()) {
			errors.rejectValue("user_id", "NotBlank");
		} 
		else {
			if(!Pattern.matches(isId, memberLoginDTO.getUser_id())) {
				errors.rejectValue("user_id", "effect.user_id");
			}
		}
		
		if(memberLoginDTO.getUser_pwd() == null || memberLoginDTO.getUser_pwd().trim().isEmpty()) {
			errors.rejectValue("user_pwd", "NotBlank");
		} 
		else {
			if(!Pattern.matches(isId, memberLoginDTO.getUser_pwd())) {
				errors.rejectValue("user_pwd", "effect.user_pwd");
			}
		}
		
		
	}

	
	
}
