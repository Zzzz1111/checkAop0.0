package com.huzijun.check;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Aspect
public class ParamsCheckAspect {

	@Autowired
	private ParameterCheckOption parameterCheckOption;

	@Pointcut("within(com.huzijun.check.controller.*)")
	public void check(){
	}

	@Around(value = "check()", argNames = "joinPoint")
	public Object checkIsValid(ProceedingJoinPoint joinPoint) throws Throwable {
		Object object ;
		String str = parameterCheckOption.checkValid(joinPoint);
		if (StringUtils.hasText(str)){
			return str;
		}
		object = joinPoint.proceed(joinPoint.getArgs());
		return object;
	}
}
