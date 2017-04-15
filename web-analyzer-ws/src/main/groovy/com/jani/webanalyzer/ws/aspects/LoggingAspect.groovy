package com.jani.webanalyzer.ws.aspects

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
/**
 * Created by jacekniedzwiecki on 11.03.2017.
 */
@Aspect
class LoggingAspect {

    @Before("execution(public * com.jani.webanalyzer.ws.services.*Service.*(..))")
    static void logBeforeWs(JoinPoint joinPoint) {
        System.out.println("Before: " + joinPoint.getSignature().toString())
    }

    @After("execution(public * com.jani.webanalyzer.ws.services.*Service.*(..))")
    static void logAfterWs(JoinPoint joinPoint) {
        System.out.println("After: " + joinPoint.getSignature().toString())
    }

    @Before("execution(public * com.jani.webanalyzer.pathprocessor.*Processor.*(..))")
    static void logBeforeProcessor(JoinPoint joinPoint) {
        System.out.println("Before: " + joinPoint.getSignature().toString())
    }

    @After("execution(public * com.jani.webanalyzer.pathprocessor.*Processor.*(..))")
    static void logAfterProcessor(JoinPoint joinPoint) {
        System.out.println("After: " + joinPoint.getSignature().toString())
    }
}
