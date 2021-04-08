package com.goCamping.validator;



import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.goCamping.dto.MemberLoginDTO;

public class MemberLoginValidator implements Validator {
	
	// Validator가 검증할 수 있는 타입인지 검사
	@Override
	public boolean supports(Class<?> clazz) {
		return MemberLoginDTO.class.isAssignableFrom(clazz);
	}

	// 첫 번째 파라미터로 전달받은 객체를 검증하고 오류 결과를 Errors에 담는 기능을 한다.
	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user_id", "NotBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user_pwd", "NotBlank");
		
	}

	
	
}
