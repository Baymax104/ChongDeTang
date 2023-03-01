package com.cdtde.chongdetang.aop;

import com.blankj.utilcode.util.LogUtils;
import com.cdtde.chongdetang.entity.ResponseResult;

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

    private WebServiceAspect() {
    }

    @Before("execution(* com.cdtde.chongdetang.repository.*.request*(..))")
    public void beforeRequest(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String functionName = signature.getName();
        LogUtils.iTag("cdt-web-" + functionName, functionName + "发送请求");
    }

    @SuppressWarnings("rawtypes")
    @Before("execution(void com.cdtde..*.lambda$request*(com.cdtde..*.ResponseResult))")
    public void beforeNext(JoinPoint joinPoint) {
        Object arg = joinPoint.getArgs()[0];
        ResponseResult result = (ResponseResult) arg;
        String name = joinPoint.getSignature().getName();
        if ("success".equals(result.getStatus())) {
            LogUtils.iTag("cdt-web-" + name, name + "请求结果成功");
        } else {
            LogUtils.eTag("cdt-web-" + name, "请求结果失败");
        }
    }

    @Before("execution(void com.cdtde..*.lambda$request*(java.lang.Throwable))")
    public void beforeError(JoinPoint joinPoint) {
        Throwable e = (Throwable) joinPoint.getArgs()[0];
        String name = joinPoint.getSignature().getName();
        LogUtils.eTag("cdt-web-" + name, e.getMessage());
    }
}
