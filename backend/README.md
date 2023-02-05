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
    birthday    varchar(50)         生日
    photo       varchar(100)        头像
    mail        varchar(40)         邮箱
    phone       varchar(20)         手机号码
    gender      varchar(10)         性别
    age         int                 年龄

### 接口：
##### 1.注册
    对于所填信息的判断（两次密码是否一致、手机号是否是11位数字等已经判断并返回）
    url:https://app2799.acapp.acwing.com.cn/api/user/register/
    type:post
    data:{
        phone           // 手机号
        password        // 密码
    }
    return:{
        ResponseResult<Object>  // 包含status(success、error)、message(为什么error)
    }

##### 2.登录
    采用jwt验证，返回值中有token，记得存储
    url:https://app2799.acapp.acwing.com.cn/api/user/login
    type:post
    data:{
        phone       // 手机号
        password    // 密码
    }
    return:{
        ResponseResult<User>  // data为user信息，包含token
    }
##### 3.上传新闻中心内容
    包含行业资讯、展馆动态、每日一栏
    url:https://app2799.acapp.acwing.com.cn/api/newscenter/upload
    type:post
    data:{
        type    // 表示是哪个子栏，取值["hyzx","zgdt","mryl"]
        date    // 文章发表日期
        title   // 文章标题
        photo   // 文章配图
        url     // 手机端文章网址
        aabstract   // 文章摘要
    }
    return:{
        ResponseResult<Object>  // success
    }

##### 4.获取新闻中心内容
    url:https://app2799.acapp.acwing.com.cn/api/newscenter/download
    type:post
    data:{
        type    // 表示是哪个子栏，取值["hyzx","zgdt","mryl"]
    }
    return:{
        ResponseResult<List<NewsCenter>>    //  列表形式的所有信息
    }