package com.leoat12.example.bankapp;

import com.leoat12.example.bankapp.exception.ResourceNotFoundException;
import io.sentry.spring.SentryExceptionResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
public class BankappApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankappApplication.class, args);
    }

    @Bean
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new SentryExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 Object handler,
                                                 Exception ex) {

                if(!(ex instanceof ResourceNotFoundException))
                    super.resolveException(request, response, handler, ex);

                return null;

            }
        };
    }

    @Bean
    public ServletContextInitializer sentryServletContextInitializer() {
        return new io.sentry.spring.SentryServletContextInitializer();
    }
}
