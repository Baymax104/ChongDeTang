package com.cdtde.chongdetang.aop;

import com.blankj.utilcode.util.LogUtils;

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

    private ViewModelAspect() {
    }

    @After("execution(void com.cdtde.chongdetang.viewModel..*.*Init(boolean))")
    public void beforeInit(JoinPoint joinPoint) {
        Boolean arg = (Boolean) joinPoint.getArgs()[0];
        String targetName = joinPoint.getTarget().getClass().getSimpleName();
        String functionName = joinPoint.getSignature().getName();
        String objTag = targetName + "-" + functionName;
        String tag = "cdt-vm-" + objTag;
        if (arg) {
            LogUtils.iTag(tag, objTag + "：初始化完成");
        } else {
            LogUtils.iTag(tag, objTag + "：init = false");
        }
    }

    @After("execution(* com.cdtde.chongdetang.viewModel..*.refresh*())")
    public void beforeRefresh(JoinPoint joinPoint) {
        String targetName = joinPoint.getTarget().getClass().getSimpleName();
        String functionName = joinPoint.getSignature().getName();
        String objTag = targetName + "-" + functionName;
        LogUtils.iTag("cdt-vm-" + objTag, objTag + "：刷新数据完成");
    }
}
