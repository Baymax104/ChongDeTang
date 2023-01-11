# 后端说明

### 注意事项
    1.有什么需要的接口随时联系
    2.backend/test_api.py是使用python向后端发送请求的代码，可供调试
    3.backend/create_tables.sql是生成数据库的sql语句，请先创建名为chongdetang的database

### 表描述
##### 1.User
    用户信息
    id          int                 唯一ID
    username    varchar(40)         用户名
    password    varchar(100)        密码
    photo       varchar(100)        头像（后端已删去默认头像）
    mail        varchar(40)         邮箱
    phone       varchar(20)         手机号码
    sex         varchar(10)         性别
    age         int                 年龄
    from        varchar(100)        来自哪个平台

### 接口：
##### 1.注册
    对于所填信息的判断（两次密码是否一致、手机号是否是11位数字等已经判断并返回）
    url:https://app2799.acapp.acwing.com.cn/api/user/account/register/
    type:post
    data:{
        phone           // 手机号
        password        // 密码
        confirmPassword // 确认密码
    }
    return:{
        /*
        Map格式
        有很多种，如success、手机号已注册、两次密码不一致等
        在前端设计时，直接把return的值取出提示即可
        */
        error_message   // 错误信息
    }

##### 2.登录
    采用jwt验证，返回值中有token，记得存储
    url:https://app2799.acapp.acwing.com.cn/api/user/account/token/
    type:post
    data:{
        phone       // 手机号
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
        username        // 昵称
        photo           // 头像
        mail            // 邮箱
        phone           // 手机号
        sex             // 性别
        age             // 年龄
    }    