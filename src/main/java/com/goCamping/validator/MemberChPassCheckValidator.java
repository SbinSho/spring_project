package com.goCamping.validator;


import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.goCamping.dto.MemberChIdDTO;
import com.goCamping.dto.MemberChPassDTO;

public class MemberChPassCheckValidator extends MemberValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return MemberChPassDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		MemberChPassDTO memberChPassDTO = (MemberChPassDTO) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user_id", "NotBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user_pwd", "NotBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ch_user_pwd", "NotBlank");
		
		if(!Pattern.matches(isId, memberChPassDTO.getUser_id()) || 
				!Pattern.matches(isPw, memberChPassDTO.getUser_pwd()) ||
				!Pattern.matches(isPw, memberChPassDTO.getCh_user_pwd()) ) {
			errors.reject("effect");
		}
		
	}
	
}
