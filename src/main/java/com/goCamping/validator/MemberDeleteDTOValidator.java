package com.goCamping.validator;


import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.goCamping.dto.MemberChIdDTO;
import com.goCamping.dto.MemberChPassDTO;
import com.goCamping.dto.MemberDeleteDTO;

public class MemberDeleteDTOValidator extends MemberValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return MemberDeleteDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		MemberDeleteDTO memberDeleteDTO = (MemberDeleteDTO) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user_id", "NotBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user_pwd", "NotBlank");
		
		if(!Pattern.matches(isId, memberDeleteDTO.getUser_id()) || 
				!Pattern.matches(isPw, memberDeleteDTO.getUser_pwd()) ) {
			errors.reject("effect");
		}
		
	}
	
}
