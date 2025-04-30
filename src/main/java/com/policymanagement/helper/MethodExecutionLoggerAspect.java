package com.policymanagement.helper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
@Slf4j
public class MethodExecutionLoggerAspect {

    @Pointcut("execution(* com.policymanagement.controller..*(..)) || execution(* com.policymanagement.serviceimpl..*(..))")
    public void loggableMethods() {}

    @Around("loggableMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startMillis = System.currentTimeMillis();
        ZonedDateTime startTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));

        Object result = joinPoint.proceed();

        long endMillis = System.currentTimeMillis();
        ZonedDateTime endTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));

        long duration = endMillis - startMillis;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");

        log.info("Method: {}.{} | Start: {} | End: {} | Duration: {} ms",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                startTime.format(formatter),
                endTime.format(formatter),
                duration);

        return result;
    }
}
