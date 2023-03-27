package com.linc.pm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CountingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger("Application Counter");

    private static final Map<String, Integer> methodCounts = new HashMap<>();

    @Pointcut("@annotation(Countable)")
    public void executeCountable() {}

    @Before("executeCountable()")
    public void incrementCount(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getDeclaringType() + "." + joinPoint.getSignature().getName();
        if (methodCounts.containsKey(methodName)) {
            int current = methodCounts.get(methodName);
            current++;
            methodCounts.put(methodName, current);
        } else {
            methodCounts.put(methodName, 1);
        }
        StringBuilder message = new StringBuilder("Current counts are: | ");
        methodCounts.forEach((k, v) -> message.append(k + "::" + v + " | "));
        LOGGER.info(message.toString());
    }
}
