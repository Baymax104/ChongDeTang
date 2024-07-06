package com.cdtde.chongdetang.repository;

import java.nio.charset.StandardCharsets;

/**
 * @Description 存放所有api接口的key，不可上传github，使用时放在repository文件夹下即可
 * @Author John
 * @email
 * @Date 2023/1/29 22:34
 * @Version 1
 */
public class AppKey {

    // 阿里云短信API
    public static final String ACCESS_KEY_ID = "";
    public static final String ACCESS_KEY_SECRET = "";

    // 远程服务器URL，部署后可直接在公网访问
    public static final String SERVER_BASE_URL = "";

    // 本地调试URL，后端项目启动后，手机与主机在同一局域网内访问
    // 调试时修改为自己主机的IP地址
    public static final String TEST_SERVER_BASE_URL = "";

    // 密码传输密钥，AES加密
    public static final byte[] PASSWORD_KEY = "Lt3ERDb1iiUAIDo2".getBytes(StandardCharsets.UTF_8);
    public static final byte[] PASSWORD_IV = "sAoymr1pXYM0BFZ6".getBytes(StandardCharsets.UTF_8);

    public static final String COS_URL = "";

}
