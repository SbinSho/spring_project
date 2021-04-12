package com.goCamping.validator;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;

public class MemberNickCheckValidator extends MemberValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return String.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		String user_nickname = (String) target;
		
		
		if(user_nickname == null || user_nickname.trim().isEmpty()) {
			errors.reject("NotBlank");
		}
		else {
			if(!Pattern.matches(isNick, user_nickname)) {
				errors.reject("effect.user_nickname");
			};
		}
		
	}
	
}
