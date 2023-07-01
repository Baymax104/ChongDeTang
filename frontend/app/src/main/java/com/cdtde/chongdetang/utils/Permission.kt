package com.cdtde.chongdetang.utils

import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.PermissionUtils.SimpleCallback
import com.cdtde.chongdetang.utils.Permission.PermissionCallback

/**
 * 权限请求与回调类
 * @property permission 权限字符串
 */
class Permission(private val permission: String) : SimpleCallback {

    /**
     * 权限请求回调接口
     */
    fun interface PermissionCallback {
        fun invoke()
    }

    private var granted = PermissionCallback { }
    private var denied = PermissionCallback { }

    /**
     * 权限请求成功回调
     * @param action 事件回调
     */
    fun granted(action: PermissionCallback) = apply { granted = action }

    /**
     * 权限请求失败回调
     * @param action 事件回调
     */
    fun denied(action: PermissionCallback) = apply { denied = action }

    /**
     * 重写[SimpleCallback.onGranted]方法，调用[granted]
     */
    override fun onGranted() = granted.invoke()

    /**
     * 重写[SimpleCallback.onDenied]方法，调用[denied]
     */
    override fun onDenied() = denied.invoke()

    /**
     * 权限请求执行方法
     */
    fun request() {
        if (PermissionUtils.isGranted(permission)) {
            granted.invoke()
        } else {
            PermissionUtils.permission(permission).callback(this).request()
        }
    }
}