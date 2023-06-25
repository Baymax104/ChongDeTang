package com.cdtde.chongdetang.utils

import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.PermissionUtils.SimpleCallback
import com.cdtde.chongdetang.utils.Permission.PermissionCallback

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/4/24 23:25
 *@Version 1
 */
class Permission(private val permission: String) : SimpleCallback {

    fun interface PermissionCallback {
        fun invoke()
    }

    private var granted = PermissionCallback { }
    private var denied = PermissionCallback { }

    fun granted(action: PermissionCallback) = apply { granted = action }
    fun denied(action: PermissionCallback) = apply { denied = action }

    override fun onGranted() = granted.invoke()
    override fun onDenied() = denied.invoke()

    fun request() {
        if (PermissionUtils.isGranted(permission)) {
            granted.invoke()
        } else {
            PermissionUtils.permission(permission).callback(this).request()
        }
    }
}