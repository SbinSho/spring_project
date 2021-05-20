package com.goCamping.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LogAspect {
	
	private static final Logger logger = LogManager.getLogger(LogAspect.class);
	
	// 현재 요청 받은 컨트롤러 이름
	private static String controllerName;
	// 현재 요청 받은 컨틀롤러의 메소드명
	private static String methdoName;

	// 대상 객체의 메서드 실행 전, 후 또는 익셉션 발생 시점에 공통 기능을 싱핼하는데 사용된다.
	@Around("execution(public * com.goCamping.controller..*(..))")
	public Object logger(ProceedingJoinPoint joinPoint) throws Throwable {
		// 성능 측정을 위한 나노타임 반환
		long start = System.nanoTime();
		
		try {
			// 메소드의 클래스명을 가져오기
			String temp = joinPoint.getSignature().getDeclaringTypeName();
			
			// 현재 요청 받은 컨트롤러 이름
			controllerName = temp.substring(temp.lastIndexOf(".") + 1);
			// 현재 요청 받은 컨틀롤러의 메소드명
			methdoName = joinPoint.getSignature().getName() + "()";
			
			
			logger.info("===============================================");
			logger.info(controllerName + "의 " + methdoName + " 메소드 실행!");
			
			Object result = joinPoint.proceed();
			
			return result;
			
		} finally {
			
			long finish = System.nanoTime();
			
			logger.info(controllerName + "의 " + methdoName + " 메소드 종료!");
			logger.info("실행 시간 (nanoTime) : " + ((finish - start)));
			logger.info("실행 시간 (초) : " + ((finish - start)/1000000000));
			logger.info("===============================================\n\n");
		}
		
		
	}
	
	
}
