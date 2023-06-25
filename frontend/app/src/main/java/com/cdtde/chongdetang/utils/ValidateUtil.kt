package com.cdtde.chongdetang.utils

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.StringUtils
import com.cdtde.chongdetang.repository.AppKey
import java.nio.charset.StandardCharsets
import java.util.regex.Pattern

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/2 0:37
 * @Version 1
 */
object ValidateUtil {

    @JvmStatic
    fun validatePhone(phone: String?): Boolean {
        return !StringUtils.isEmpty(phone) && RegexUtils.isMobileExact(phone)
    }

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

    @JvmStatic
    fun validateUsername(username: String?): Boolean {
        if (username == null) {
            return false
        }
        // 2-12个中英文和数字的组合
        val pattern = "^[a-zA-Z\\d\\u4e00-\\u9fa5]+$"
        val isMatch = Pattern.matches(pattern, username)
        val length = username.length
        val lengthValidity = length in (2..12)
        return isMatch && lengthValidity
    }

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

    @JvmStatic
    fun validateCode(code: String?): Boolean {
        if (code == null) return false
        // 四位数字
        val pattern = "\\d{4}"
        return Pattern.matches(pattern, code)
    }

    @JvmStatic
    fun validateUri(uri: String?): Boolean {
        if (uri == null) return false
        val pattern = "^(?i)(?:file:///|android.resource://).+"
        return Pattern.matches(pattern, uri)
    }

}