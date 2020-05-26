package com.aop.handler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description :
 * @Author : wsz
 * @Date: 2020-05-25 19:42
 */
@Slf4j
@Aspect
@Component
public class AopHandler {

    /**
     * 切入点：注解路径
     */
    @Pointcut("@annotation(com.aop.handler.ReqLog)")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        System.out.println("before");
    }

    @After("pointCut()")
    public void after(JoinPoint point) {
        System.out.println("after");
    }

    @AfterReturning("pointCut()")
    public void afterRe(JoinPoint point){
        System.out.println("AfterReturning");
    }

//    @Around("execution(* com.aop.web.*.*(..))")
    @Around("pointCut()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();

        long start = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 请求路径
        log.info("方法：[{}]", request.getMethod() +"  " + request.getRequestURI() + "  "
                + signature.getDeclaringTypeName() + "#" + signature.getName());

        // 请求参数
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        List<String> params = new ArrayList<>();
        for(int i=0; i< parameterNames.length; i++){
            params.add(parameterNames[i] + "=" + args[i]);
        }
        log.info("请求参数：{}", params);

        // 返回结果
        Object proceed = joinPoint.proceed();
        log.info("返回结果：[{}]", proceed);

        // 请求耗时
        log.info("耗时：[{}ms]", System.currentTimeMillis() - start);

        return proceed;
    }
}
