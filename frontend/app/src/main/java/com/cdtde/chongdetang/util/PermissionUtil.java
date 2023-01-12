package com.cdtde.chongdetang.util;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * @Description 基于RxPermissions，封装其调用
 * @Author John
 * @email
 * @Date 2023/1/11 7:02
 * @Version 1
 */
public class PermissionUtil {

    // RxPermissions申请权限失败后会自动return，不需要手动处理
    @SuppressLint("CheckResult")
    public static void requestPermission(String permission) {
        Activity activity = ActivityUtils.getTopActivity();
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(permission).subscribe(aBoolean -> {
            if (!aBoolean) {
                ToastUtils.showShort("权限已关闭！请到权限中心打开权限");
            }
        });
    }
}
