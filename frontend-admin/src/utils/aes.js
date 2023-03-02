import CryptoJS from "crypto-js";
import {ivStr, keyStr, encodeMode} from "../../config/app-key";

// aes加密
export function encodePassword(str) {
    const key = CryptoJS.enc.Utf8.parse(keyStr);  // 十六位十六进制数作为密钥
    const iv = CryptoJS.enc.Utf8.parse(ivStr);   // 十六位十六进制数作为密钥偏移量
    let srcs = CryptoJS.enc.Utf8.parse(str);
    let encrypted = CryptoJS.AES.encrypt(srcs, key, { iv: iv, mode: encodeMode.MODE, padding: encodeMode.PADDING });
    //加密后的信息
    return encrypted.toString()
}