package com.springboot.study.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimerAop {
	
	private final Logger LOGGER = LoggerFactory.getLogger(TimerAop.class);
	
	//pointcut을 지정해서 메소드로 사용가능
	@Pointcut("execution(* com.springboot.study..*.*(..))")
	private void pointcut() {}
	
	@Pointcut("@annotation(com.springboot.study.annotation.Timer)")
	private void enableTimer() {}
	
	@Around("pointcut() && enableTimer()")
	public void around(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Object result = joinPoint.proceed();
		
		stopWatch.stop();
		LOGGER.info("실행시간: {}({}) = {} ({}ms)", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(),result, stopWatch.getTotalTimeSeconds()); //start-stop시간
		
	}
}
