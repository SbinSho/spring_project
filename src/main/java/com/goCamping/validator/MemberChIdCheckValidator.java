package com.goCamping.validator;


import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.goCamping.dto.MemberChIdDTO;

public class MemberChIdCheckValidator extends MemberValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return MemberChIdDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		MemberChIdDTO memberChIdDTO = (MemberChIdDTO) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user_id", "NotBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ch_id", "NotBlank");
		
		if(!Pattern.matches(isId, memberChIdDTO.getUser_id()) || 
				!Pattern.matches(isId, memberChIdDTO.getCh_id())) {
			errors.reject("effect");
		}
		
	}
	
}
