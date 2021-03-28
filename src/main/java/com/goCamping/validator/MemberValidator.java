package com.goCamping.validator;


import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.goCamping.domain.MemberVO;

@Component
public class MemberValidator implements Validator {
	
	private static final String emailRegExp = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private Pattern pattern;
	
	public MemberValidator() {
		pattern = Pattern.compile(emailRegExp);
	}

	// Validator가 검증할 수 있는 타입인지 검사
	@Override
	public boolean supports(Class<?> clazz) {
		return MemberVO.class.isAssignableFrom(clazz);
	}

	// 첫 번째 파라미터로 전달받은 객체를 검증하고 오류 결과를 Errors에 담는 기능을 한다.
	@Override
	public void validate(Object target, Errors errors) {
		
		MemberVO regMbr = (MemberVO) target;
		
		if(regMbr.getUser_mail() == null || regMbr.getUser_mail().trim().isEmpty()) {
			errors.rejectValue("user_mail", "bad");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user_name", "requierd");
		ValidationUtils.rejectIfEmpty(errors, "user_pwd", "requierd");
		
	}

	
	
}
