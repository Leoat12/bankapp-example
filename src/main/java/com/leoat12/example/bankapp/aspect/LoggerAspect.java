package com.leoat12.example.bankapp.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LoggerAspect {

    private Logger logger = LogManager.getLogger(LoggerAspect.class);

    @Before("execution(* com.leoat12.example.bankapp.controller.*.*(..))")
    public void before(JoinPoint joinPoint){

        logger.info("Aspect running...");

        Object body = null;
        for(Object arg : joinPoint.getArgs()){
            if(arg.getClass().getPackage().getName().startsWith("com.leoat12.example.bankapp.model"))
                body = arg;
        }

        if(body != null){
            ThreadContext.put("body", body.toString());
        }

        logger.info("Aspect finished...");
    }


}
