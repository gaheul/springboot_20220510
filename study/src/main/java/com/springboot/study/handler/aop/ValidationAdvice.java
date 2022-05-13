package com.springboot.study.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.springboot.study.handler.ex.CustomValidationApiException;


/*
 * Aspect: 여러 핵심 기능에 적용될 관심사 모듈을 의미한다. Aspect에는 구체적인 기능을 구현한 Advice와 Advice가 어디(Target)에서 적용될지 결정하는 PointCut을 포함
 *         
 * Target: 공통 기능을 부여할 대상. 즉, 핵심 기능을 담당하는 비즈니스 로직이고, 어떤 관심사들과도 관계를 맺지않는다.(해당 메소드안에는 선언x)
 * 
 * Advice: 공통 기능을 담은 구현체. Advice는 Aspect가 무엇을 언제 적용할 지를 정의하는 것
 * 
 * PointCut: 공통 기능이 적용될 대상을 결정하는 것
 * 
 * JoinPoint: Advice가 적용될 지점을 의미. Spring에서는 메서드에만 제공이 됨 ->메서드
 */

@Aspect
@Component
public class ValidationAdvice {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ValidationAdvice.class);
	
			//execution세부적으로 메소드 지정가능한것 / * :리턴타입(Integer,User,User*....) /controller..:controller하위에 있느 ㄴ모든 
	@Around("execution(* com.springboot.study.web.controller.api.*Controller.*(..))")
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {//ProceedingJoinPoint target의 모든정보
//		LOGGER.info("내가 작성한 로그:{},{}",proceedingJoinPoint.getSignature().getDeclaringTypeName(), 
//				    proceedingJoinPoint.getSignature().getName()); //typename:class,interface명  getname:메소드명
		
		Object[] args = proceedingJoinPoint.getArgs(); //proceedingJoinPoint->모든 매개변수를 들고옴(해당 메소드마다 매개변수다름).getargs : 매개변수의 모든값
		for(Object arg : args) {
			if(arg instanceof BindingResult) {//매개변수의 원래 타입 -> bindingresult인지 확인
				BindingResult bindingResult = (BindingResult) arg;
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<String, String>();
					for(FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					LOGGER.info("Validation AOP 실행됨");
					
					throw new CustomValidationApiException("유효성 검사 실패", errorMap); //생성
				}
			}
		}
		
		
		
		
		
		return proceedingJoinPoint.proceed(); //filter-chain -> 해당target메소드가 실행 이전(전처리)
	}
}
