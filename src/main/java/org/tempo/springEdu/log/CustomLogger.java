package org.tempo.springEdu.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tempo.springEdu.SpringEduApplication;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
@Aspect
public class CustomLogger {
    private final Logger logger = LogManager.getLogger(SpringEduApplication.class);

    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)"
            + " || @annotation(org.springframework.web.bind.annotation.PostMapping)"
            + " || @annotation(org.springframework.web.bind.annotation.PutMapping)"
            + " || @annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String httpMethod = request.getMethod();
        String requestUri = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + request.getContextPath() + request.getRequestURI();
        String queryString = request.getQueryString();
        if (queryString != null) {
            requestUri += "?" + queryString;
        }
        String clientIp = request.getRemoteAddr();
        String userName = request.getRemoteUser();

        logger.info(String.format(
                "\nMethod: %s\n"
                        + "Request URI: %s\n"
                        + "Client address: %s\n"
                        + "Username: %s\n"
                        + "Processing time: %s ms"
                ,httpMethod
                ,requestUri
                ,clientIp
                ,userName
                ,stopWatch.getTotalTimeMillis()
        ));

        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                logger.info("Header: " + request.getHeader(headerNames.nextElement()));
            }
        }
        return result;
    }
}
