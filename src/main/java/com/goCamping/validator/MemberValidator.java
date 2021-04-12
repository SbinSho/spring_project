package com.goCamping.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MemberValidator implements Validator {
	
	protected static final String isId = "^[a-z0-9][a-z0-9_\\\\-]{4,19}$";
	protected static final String isName = "^[가-힣]{2,6}$";
	protected static final String isNick = "^([a-zA-Z0-9|가-힣]).{1,10}$";
	protected static final String isPw = "^[a-zA-Z0-9]{8,20}$";
	protected static final String isMail = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
			"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	
	// Validator가 검증할 수 있는 타입인지 검사
	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	// 첫 번째 파라미터로 전달받은 객체를 검증하고 오류 결과를 Errors에 담는 기능을 한다.
	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
