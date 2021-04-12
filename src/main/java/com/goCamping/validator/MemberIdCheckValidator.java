package com.goCamping.validator;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;

public class MemberIdCheckValidator extends MemberValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return String.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		String user_id = (String) target;
		
		if(user_id == null || user_id.trim().isEmpty()) {
			System.out.println("null");
			errors.reject("NotBlank");
		}
		else {
			
			if(!Pattern.matches(isId, user_id)) {
				System.out.println("pattern");
				errors.reject("effect.user_id");
			};
		}
		
	}
	
}
