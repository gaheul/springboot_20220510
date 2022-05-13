package com.springboot.study.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAdvice {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogAdvice.class);
	
			//within: 해당 패키지 내의 모든 메서드에 적용  /study..*:study내에 있는 모든 메서드
	@Around("within(com.springboot.study..*)") //study안에 있는 모든 메소드 실행
	public Object logging(ProceedingJoinPoint pjp) throws Throwable {
		long startAt = System.currentTimeMillis(); //현재시간
		
		Map<String, Object> params = getParams(pjp);
		
		LOGGER.info("----------Advice Call: {}({}) = {}", pjp.getSignature().getDeclaringTypeName(),pjp.getSignature().getName(),
				params); //"데이터":매개변수에 들어있느 모든 값->getParams() / getDeclaringTypeName():클래스명
		
		Object result = pjp.proceed(); //메서드 호출 / 해당 메서드가 실행되는 전 후 
		
		long endAt = System.currentTimeMillis();
		
		LOGGER.info("----------Advice End: {}({}) = {} ({}ms)", pjp.getSignature().getDeclaringTypeName(),
				pjp.getSignature().getName(),result,endAt - startAt); //result :해당 메서드 실행 결과(responseEntity) / endAt - startAt : 실행 시간
		
		return result;
	}
	
	private Map<String, Object> getParams(ProceedingJoinPoint pjp){
		Map<String, Object> params = new HashMap<String, Object>();
		
		Object[] args = pjp.getArgs();
		String[] argNames = ((CodeSignature)pjp.getSignature()).getParameterNames(); //매개변수 이름 -> CodeSignature으로 다운캐스팅하면 getParameterNames()사용가능
		
		for(int i =0; i<args.length; i++) {
			params.put(argNames[i], args[i]);
		}
		return params;
	}
}
