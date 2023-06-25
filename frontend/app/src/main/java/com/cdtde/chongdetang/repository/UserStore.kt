package com.cdtde.chongdetang.repository

import com.blankj.utilcode.util.Utils
import com.cdtde.chongdetang.base.vm.MessageHolder
import com.cdtde.chongdetang.entity.User
import java.util.*

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 22:59
 * @Version 1
 */
object UserStore : MessageHolder() {

    @JvmStatic
    var id: Int?
        get() = getUser().id
        set(value) {
            getUser().id = value
        }

    @JvmStatic
    var username: String
        get() = getUser().username ?: "未登录"
        set(value) {
            getUser().username = value
        }

    @JvmStatic
    var photo: String
        get() = getUser().photo ?: User.DEFAULT_PHOTO
        set(value) {
            getUser().photo = value
        }

    @JvmStatic
    var gender: String?
        get() = getUser().gender
        set(value) {
            getUser().gender = value
        }

    @JvmStatic
    var birthday: Date?
        get() = getUser().birthday
        set(value) {
            getUser().birthday = value
        }

    @JvmStatic
    var phone: String?
        get() = getUser().phone
        set(value) {
            getUser().phone = value
        }

    @JvmStatic
    var password: String?
        get() = getUser().password
        set(value) {
            getUser().password = value
        }

    @JvmStatic
    var mail: String?
        get() = getUser().mail
        set(value) {
            getUser().mail = value
        }

    @JvmStatic
    var token: String?
        get() = getUser().token
        set(value) {
            getUser().token = value
        }

    @JvmStatic
    val userLoginEvent: Event<Boolean, Unit> = Event()

    @JvmStatic
    fun getUser(): User {
        val app = Utils.getApp() as AppApplication
        return app.user
    }

    @JvmStatic
    fun initUser() {
        val app = Utils.getApp() as AppApplication
        app.initUser()
        userLoginEvent.send(false)
    }

    @JvmStatic
    fun setUser(user: User) {
        val app = Utils.getApp() as AppApplication
        app.user = user
        userLoginEvent.send(true)
    }

    @JvmStatic
    fun setUserInfo(user: User) {
        val app = Utils.getApp() as AppApplication
        val origin = app.user
        origin.apply {
            username = user.username
            photo = user.photo
            gender = user.gender
            birthday = user.birthday
            mail = user.mail
        }
        app.user = origin
    }

    @JvmStatic
    fun isLogin() = token != null
}
