package com.cdtde.chongdetang.aop;

import com.cdtde.chongdetang.entity.Result;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/3/1 12:32
 * @Version 1
 */
@Aspect
public class WebServiceAspect {

    private Logger logger = XLog.tag("cdt-web-log").build();

    private WebServiceAspect() {
    }

    @Before("execution(* com.cdtde.chongdetang.repository.*.request*(..))")
    public void beforeRequest(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String functionName = signature.getName();
        logger.i(functionName + "调用");
    }

    @SuppressWarnings("rawtypes")
    @Before("execution(void com.cdtde..*.lambda$request*(com.cdtde..*.Result))")
    public void beforeNext(JoinPoint joinPoint) {
        Object arg = joinPoint.getArgs()[0];
        Result result = (Result) arg;
        String name = joinPoint.getSignature().getName();
        String msg = "success".equals(result.getStatus()) ? name + "：请求结果成功" : name + "：请求结果失败";
        logger.i(msg);
    }

    @Before("execution(void com.cdtde..*.lambda$request*(java.lang.Throwable))")
    public void beforeError(JoinPoint joinPoint) {
        Throwable e = (Throwable) joinPoint.getArgs()[0];
        String name = joinPoint.getSignature().getName();
        logger.e(name + "异常", e);
    }
}
