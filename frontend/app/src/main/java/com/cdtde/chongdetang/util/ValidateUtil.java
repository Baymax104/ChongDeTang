package com.cdtde.chongdetang.util;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdtde.chongdetang.repository.AppKey;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/2 0:37
 * @Version 1
 */
public class ValidateUtil {

    public static boolean validatePhone(String phone) {
        return !StringUtils.isEmpty(phone) && RegexUtils.isMobileExact(phone);
    }

    public static boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        String pattern = "^[a-zA-Z\\d]+$";
        boolean match = password.matches(pattern);
        boolean length = password.length() >= 6 && password.length() <= 15;
        return match && length;
    }

    public static boolean validateUsername(String username) {
        if (username == null) {
            return false;
        }
        String pattern = "^[a-zA-Z\\d\\u4e00-\\u9fa5]+$";
        boolean isMatch = Pattern.matches(pattern, username);

        int length = username.length();
        boolean lengthValidity = length >= 2 && length <= 12;

        return isMatch && lengthValidity;
    }

    public static String encrypt(String data) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        return EncryptUtils.encryptAES2HexString(bytes, AppKey.PASSWORD_KEY, "AES/CBC/PKCS5Padding", AppKey.PASSWORD_IV);
    }
}
