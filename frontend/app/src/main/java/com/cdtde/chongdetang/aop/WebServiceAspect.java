package com.cdtde.chongdetang.aop;

import com.blankj.utilcode.util.LogUtils;

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

    @Before("execution(* com.cdtde.chongdetang.repository.ExhibitRepository.*(..))")
    public void doBefore() {
        LogUtils.iTag("cdt-", "进来了");
    }
}
