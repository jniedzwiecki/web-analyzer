package com.jani.webanalyzer.aspects

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

/**
 * Created by jacekniedzwiecki on 11.03.2017.
 */
@Aspect
class LoggingAspect {

    @Before("execution(public * com.jani.webanalyzer.services.*Service.*(..))")
    static void logBefore(JoinPoint joinPoint) {
        System.out.println("Before: " + joinPoint.getSignature().toString())
    }

    @After("execution(public * com.jani.webanalyzer.services.*Service.*(..))")
    static void logAfter(JoinPoint joinPoint) {
        System.out.println("After: " + joinPoint.getSignature().toString())
    }
}
