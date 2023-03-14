package com.cdtde.chongdetang.aop;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/3/1 23:26
 * @Version 1
 */
@Aspect
public class ViewModelAspect {

    private Logger logger = XLog.tag("cdt-vm-log").build();

    private ViewModelAspect() {
    }

    @After("execution(void com.cdtde.chongdetang.viewModel..*.*Init(boolean))")
    public void beforeInit(JoinPoint joinPoint) {
        Boolean arg = (Boolean) joinPoint.getArgs()[0];
        String targetName = joinPoint.getTarget().getClass().getSimpleName();
        String functionName = joinPoint.getSignature().getName();
        String objTag = targetName + "-" + functionName;
        String msg = arg ? objTag + "：初始化完成" : objTag + "：init = false";
        logger.i(msg);
    }

    @After("execution(* com.cdtde.chongdetang.viewModel..*.refresh*())")
    public void beforeRefresh(JoinPoint joinPoint) {
        String targetName = joinPoint.getTarget().getClass().getSimpleName();
        String functionName = joinPoint.getSignature().getName();
        String objTag = targetName + "-" + functionName;
        logger.i(objTag + "：刷新数据完成");
    }
}
