package com.goCamping.validator;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;

public class MemberMailCheckValidator extends MemberValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return String.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		String user_mail = (String) target;
		
		if(user_mail == null || user_mail.trim().isEmpty()) {
			errors.reject("NotBlank");
		}
		else {
			if(!Pattern.matches(isMail, user_mail)) {
				errors.reject("effect.user_mail");
			}
		}
		
	}
	
}
