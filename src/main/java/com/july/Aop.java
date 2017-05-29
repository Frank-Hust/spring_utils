package com.july;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by haoyifen on 2017/5/29 15:13.
 */
@Slf4j
@Aspect
@Component
public class Aop {
	@Before("execution(* com.july.DemoService.save(..))")
	public void before(JoinPoint joinPoint) {
		log.info("-------------");
		log.info("before()");
		log.info("signature: " + joinPoint.getSignature());
	}
}
