package com.goCamping.validator;


import org.springframework.validation.Errors;

public class MemberCodeCheckValidator extends MemberValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return String.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		String auth_code = (String) target;
		
		if(auth_code == null || auth_code.trim().isEmpty()) {
			errors.reject("NotBlank");
		} 
		else if(auth_code.length() != 5) {
			errors.reject("length.auth_code");
		}
	}
	
}
