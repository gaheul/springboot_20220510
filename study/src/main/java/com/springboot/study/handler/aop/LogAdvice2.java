//package com.springboot.study.handler.aop;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.CodeSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class LogAdvice2 {
//	
//	private static final Logger LOGGER = LoggerFactory.getLogger(LogAdvice2.class);
//	
//	@Around("within(com.springboot.study..*)")
//	public Object logging(ProceedingJoinPoint pjp) throws Throwable {
//		
//		Map<String, Object> params = getParams(pjp);
//		
//		long startAt = System.currentTimeMillis();
//		
//		LOGGER.info("---------Adivce call: {},({}) = {}",pjp.getSignature().getDeclaringTypeName(),pjp.getSignature().getName(),
//				params);
//		
//		Object result = pjp.proceed();
//		
//		long endAt = System.currentTimeMillis();
//		
//		LOGGER.info("---------Adivce end:{},({}) = {} ({}ms)",pjp.getSignature().getDeclaringType(),pjp.getSignature().getName(),result,endAt - startAt);
//		
//		return result;
//	}
//	
//	private Map<String, Object> getParams(ProceedingJoinPoint pjp){
//		Map<String, Object> params = new HashMap<String, Object>();
//		Object[] args = pjp.getArgs();
//		String[] argNames = ((CodeSignature)pjp.getSignature()).getParameterNames();
//		
//		for(int i=0; i<args.length; i++) {
//			params.put(argNames[i], args[i]);
//		}
//		return params;
//		
//	}
//	
//}
