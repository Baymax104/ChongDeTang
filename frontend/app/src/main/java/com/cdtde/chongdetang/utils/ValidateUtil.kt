package com.cdtde.chongdetang.utils

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.StringUtils
import com.cdtde.chongdetang.repository.AppKey
import java.nio.charset.StandardCharsets
import java.util.regex.Pattern

/**
 * 验证工具类
 */
object ValidateUtil {

    /**
     * 验证手机号格式
     * @param phone 手机号字符串
     * @return true/false
     */
    @JvmStatic
    fun validatePhone(phone: String?): Boolean {
        return !StringUtils.isEmpty(phone) && RegexUtils.isMobileExact(phone)
    }

    /**
     * 验证密码格式, 6-15位英文字母或数字的组合
     * @param password 密码字符串
     * @return true/false
     */
    @JvmStatic
    fun validatePassword(password: String?): Boolean {
        if (password == null) {
            return false
        }
        // 6-15位英文字母与数字的组合
        val pattern = "^[a-zA-Z\\d]+$"
        val match = Pattern.matches(pattern, password)
        val length = password.length in (6..15)
        return match && length
    }

    /**
     * 验证密码输入与重复输入是否相同
     * @param password 密码字符串
     * @param repeat 重复输入字符串
     * @return true/false
     */
    @JvmStatic
    fun validateRepeat(password: String?, repeat: String?): Boolean {
        if (password == null || repeat == null) {
            return false
        }
//        // 判断是否为密码格式
//        val isPwd = validatePassword(password)
        // 判断重复密码是否与原密码相等
        return repeat == password
    }

    /**
     * 验证用户名格式，2-12个中英文或数字的组合
     * @param username 用户名字符串
     * @return true/false
     */
    @JvmStatic
    fun validateUsername(username: String?): Boolean {
        if (username == null) {
            return false
        }
        // 2-12个中英文或数字的组合
        val pattern = "^[a-zA-Z\\d\\u4e00-\\u9fa5]+$"
        val isMatch = Pattern.matches(pattern, username)
        val length = username.length
        val lengthValidity = length in (2..12)
        return isMatch && lengthValidity
    }

    /**
     * 密码AES加密
     * @param data 原始密码
     * @return 加密密码
     */
    @JvmStatic
    fun encrypt(data: String): String {
        val bytes = data.toByteArray(StandardCharsets.UTF_8)
        return EncryptUtils.encryptAES2HexString(
            bytes,
            AppKey.PASSWORD_KEY,
            "AES/CBC/PKCS5Padding",
            AppKey.PASSWORD_IV
        )
    }

    /**
     * 验证验证码格式
     * @param code 验证码字符串
     * @return true/false
     */
    @JvmStatic
    fun validateCode(code: String?): Boolean {
        if (code == null) return false
        // 四位数字
        val pattern = "\\d{4}"
        return Pattern.matches(pattern, code)
    }

    /**
     * 验证URI路径格式，支持file协议和resource协议验证
     * @param uri URI路径字符串
     * @return true/false
     */
    @JvmStatic
    fun validateUri(uri: String?): Boolean {
        if (uri == null) return false
        val pattern = "^(?i)(?:file:///|android.resource://).+"
        return Pattern.matches(pattern, uri)
    }

}