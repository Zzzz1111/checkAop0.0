package com.huzijun.check;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Component
public class ParameterCheckOption {

	String checkValid(ProceedingJoinPoint joinPoint){
		Object[] args;
		Method method;
		Object target;
		String methodName;
		String str = "";
		methodName = joinPoint.getSignature().getName();
		target = joinPoint.getTarget();
		method = getMethodByName(target.getClass(),methodName);
		Annotation[][] annotations = method != null ? method.getParameterAnnotations() : new Annotation[0][];
		args = joinPoint.getArgs();
		if ( annotations != null) {
			for (int i = 0;i < annotations.length; i++) {
				Annotation[] annotations1 = annotations[i];
				for (int j = 0;j <annotations1.length; j++) {
					if (annotations[i][j].annotationType().equals(Valid.class)) {
						try {
							str = checkParam(args[i]);
							return str;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return str;
	}

	private String checkParam(Object args) throws Exception {
		String reStr = "";
		Field[] fields = args.getClass().getDeclaredFields();
		for (Field field : fields) {
			Check check = field.getAnnotation(Check.class);
			if (check != null) {
				reStr = validateFiled(check, field, args);
				if (StringUtils.hasText(reStr)) {
					return reStr;
				}
			}
		}
		return reStr;
	}

	private String validateFiled(Check check, Field field, Object args) throws Exception {
		field.setAccessible(true);
		int length ;
		if (field.get(args) != null) {
			length = (String.valueOf(field.get(args))).length();
			System.out.println(length);
		}
		if(check.notNull()){
			if (field.get(args) == null || field.get(args).equals("")) {
				return (field.getName()+"不能为空");
			}
		}
		return "";
	}

	@SuppressWarnings("rawtypes")
	private Method getMethodByName(Class clazz,String methodName){
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}
}

