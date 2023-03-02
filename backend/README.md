# 后端说明

### 注意事项
    1.有什么需要的接口随时联系
    2.backend/test_api.py是使用python向后端发送请求的代码，可供调试
    3.backend/create_tables.sql是生成数据库的sql语句，请先创建名为chongdetang的database

### Controller

#### UserController

##### register

    对于所填信息的判断（两次密码是否一致、手机号是否是11位数字等已经判断并返回）
    url:/api/user/register
    type:post
    data:{
        phone           // 手机号
        password        // 密码
    }
    return:{
        ResponseResult<Object>  // 包含status(success、error)、message(为什么error)
    }

##### login

    采用jwt验证，返回值中有token，记得存储
    url:/api/user/login
    type:post
    data:{
        phone       // 手机号
        password    // 密码
    }
    return:{
        ResponseResult<User>  // data为user信息，包含token
    }



##### updateInfo

``` 
更新用户信息
url:/api/user/update/info
method:post
data: {
	User,
	newPhoto  // 新头像base64编码，nullable
}
return: {
	ResponseResult<User>  // 更新后的User
}
```

##### updatePassword

``` 
修改密码
url:/api/user/update/password
method: post
data: {
	oldPassword,  // AES加密
	newPassword   // AES加密
}
return: {
	ResponseResult<String>  // BCrypt加密后的密码
}
```

##### updatePhone

``` 
修改手机号
url: /api/user/update/phone
method: post
data: {
	phone  // 新手机号
}
return: {
	success
}
```
##### setAdmin
``` 
修改管理员权限
url: /api/user/admin
method: post
data: {
	phone   // 手机号
	mode    // 0代表设置为非管理员，1代表设置为管理员
}
return: {
	success
}
```

##### getAllUser
``` 
获取用户列表
url: /api/user/admin
method: get
data: None
return: {
    ResponseResult<List<User>> 
}
```


#### AddressController

##### getAllAddress

``` 
获取用户的所有地址
url: /api/user/address
method: get
data: null
return: {
	List<Address>  // address列表
}
```

##### updateAddress

``` 
修改地址
url: /api/user/address
method: post
data: {
	address  // 新Address对象
}
return: {
	address  // 修改后的Address对象
}
```

##### deleteAddress

``` 
删除地址
url: /api/user/address
method: delete
data: {
	address  // 删除的address
}
return: {
	success
}
```

#### AppointmentController

##### getAllAppointment

``` 
获取用户的预约
url: /api/user/appointment
method: get
data: null
return: {
	List<Appointment>  // 预约列表
}
```

##### addAppointment

``` 
添加预约
url: /api/user/appointment
method: post
data: {
	appointment  // 添加的预约
}
return: {
	success
}
```

#### CollectionController

##### download

``` 
获取所有藏品
url: /api/collection
method: get
data: null
return: {
	List<Collection>  // 藏品列表
}
```

##### getHot

``` 
获取热搜藏品
url: /api/collection/hot
method: get
data: null
return: {
	List<Collection>  // 热搜藏品列表
}
```

#### CultureController

##### download

    url:/api/culture
    method:get
    data: null
    return:{
        List<Culture>    //  Culture列表
    }

#### NewsController

##### download

    url:/api/news/{type}
    method:get
    data:{
        type    // 表示是哪个子栏，取值["hyzx","zgdt","mryl"]
    }
    return:{
        ResponseResult<List<News>>    //  列表形式的所有信息
    }

#### ProductController

##### getAllProduct

``` 
获取所有商品
url: /api/product
method: get
data: null
return: {
	List<Product>  // 商品列表
}
```

##### getShoppingByUser

``` 
获取用户购物车
url: /api/product/shopping
method: get
data: null
return: {
	List<Shopping>  // 购物车列表
}
```

##### updateShoppingNumber

``` 
修改购物车商品数量
url: /api/product/shopping/{shoppingId}/{productId}
method: post
data: {
	shoppingId,
	productId,
	number  // 修改的数量
}
return: {
	number  // 修改的数量
}
```

##### addShopping

``` 
添加购物车商品
url: /api/product/shopping
method: post
data: {
	shopping
}
return: {
	success
}
```

##### deleteShopping

``` 
删除购物车商品
url: /api/product/shopping
method: delete
data: {
	shopping  // 删除的shopping
}
return: {
	success
}
```

