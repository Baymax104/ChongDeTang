# 后端说明

### 注意事项
    1.有什么需要的接口随时联系
    2.backend/test_api.py是使用python向后端发送请求的代码，可供调试
    3.backend/create_tables.sql是生成数据库的sql语句，请先创建名为chongdetang的database

### 接口：
##### 1.注册
    对于所填信息的判断（两次密码是否一致、用户名是否为空等已经判断并返回）
    url:https://app2799.acapp.acwing.com.cn/api/user/account/register/
    type:post
    data:{
        username        // 用户名
        password        // 密码
        confirmPassword // 确认密码
        mail            // 邮箱
        phone           // 电话号码
    }
    return:{
        /*
        Map格式
        有很多种，如success、用户名已存在、用户名不能为空等
        在前端设计时，直接把return的值取出提示即可
        */
        error_message   // 错误信息
    }

##### 2.登录
    采用jwt验证，返回值中有token，记得存储
    url:https://app2799.acapp.acwing.com.cn/api/user/account/token/
    type:post
    data:{
        username    // 用户名
        password    // 密码
    }
    return:{
        token           // 验证码
        error_message   // 错误信息，只有一个success
    }

##### 3.获取信息
    用于验证token，在登录实现后应立即获取信息
    url:https://app2799.acapp.acwing.com.cn/api/user/account/info/
    type:get
    headers:{   // 一定注意格式，Bearer后面有个空格，token就是登录返回的token
        Authorization: "Bearer " + token,
    }
    return:{
        error_message   // 只有success
        id              // 用户id
        username        // 用户昵称
        photo           // 用户头像（有默认的头像,后续出换头像接口）
        mail            // 用户邮箱
        phone           // 用户手机号
    }    