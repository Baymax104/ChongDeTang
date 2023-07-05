# 后端说明

## 注意事项
    1. backend/test_api.py是使用python向后端发送请求的代码，可供调试
    2. backend/create_tables.sql是生成数据库的sql语句，请先创建名为chongdetang的database

## UserController

### register

    对于所填信息的判断（两次密码是否一致、手机号是否是11位数字等已经判断并返回）
    url:/api/user/register
    type:post
    data:{
        phone           // 手机号
        password        // 加密后密码
    }
    return:{
        ResponseResult<Object>  // 包含status(success、error)、message(为什么error)
    }

### login

    采用jwt验证，返回值中有token，记得存储
    url:/api/user/login
    type:post
    data:{
        phone       // 手机号
        password    // 加密后密码
    }
    return:{
        ResponseResult<User>  // data为user信息，包含token
    }

### updateInfo

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

### updatePassword

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

### updatePhone

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
### setAdmin
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

### checkToken
``` 
判断token是否有效，若有效则判断是否为管理员
url: /api/user/checkToken
method: post
Authorization: Bearer {token}
data: {
	None
}
return: {
    无效："status":"error"
	管理员："status":"success","message":"is admin"
	非管理员："status":"error","message":"not admin"
}
```

### getAllUser
``` 
获取用户列表
url: /api/user/admin
method: get
data: None
return: {
    ResponseResult<List<User>> 
}
```


## AddressController

### getAllAddress

``` 
获取用户的所有地址
url: /api/user/address
method: get
data: null
return: {
	List<Address>  // address列表
}
```

### updateAddress

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

### deleteAddress

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

## AppointmentController

### getAllAppointment

``` 
获取用户的预约
url: /api/user/appointment
method: get
data: null
return: {
	List<Appointment>  // 预约列表
}
```

### addAppointment

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
### changeStatus
``` 
更改审核状态
url: /api/user/appointment/status
method: post
data: {
	String id          // 预约的id号
	String status      // 要更改到什么状态，取值['PROCESSING','SUCCESS','FAIL'] 
}
return: {
	success
}
```

### getAppointmentCheckList

``` 
获取需要审核列表（按类型）
url: /api/user/appointment/checklist
method: get
data: {
	filter: type (type=['', 'SUCCESS', 'PROCESSING', 'FAIL'])	''为全部
}
return: {
	List<Appointment>
}
```

## CollectionController

### download
``` 
获取所有藏品
url: /api/collection
method: get
data: null
return: {
	List<Collection>  // 藏品列表
}
```

### getHot

``` 
获取热搜藏品
url: /api/collection/hot
method: get
data: null
return: {
	List<Collection>  // 热搜藏品列表
}
```

### getNotHot

``` 
获取热搜藏品
url: /api/collection/not-hot
method: get
data: null
return: {
	List<Collection>  // 不热搜藏品列表
}
```

### setSelected

``` 
藏品设置精选
url: /api/collection/select
method: post
data: {
	以	
	{
		String id  // 藏品id,
    	String selected  // 1为设置精选，0为取消精选
    }
	为元素的数组，批量更新
}

return: {
	success
}
```

## CultureController

### download
```
    // 在前端区分四个type，取值["beyl","shzk","zzys","tpdk"]
    url:/api/culture
    method:get
    data: null
    return:{
        List<Culture>    //  Culture列表
    }
```
### upload
```
//代表匾额楹联，书画篆刻，造纸印刷，拓片雕刻
url:/api/culture/{type} // 表示是哪个子栏，取值["beyl","shzk","zzys","tpdk"]
method:post
data:{
    String date    // YYYY-MM-DD
    String title
    String photo
    String url
    String description

return:{
    success
}
```

## NewsController

### download
```
代表行业资讯、展馆动态、每日一联
url:/api/news/{type}    // 表示是哪个子栏，取值["hyzx","zgdt","mryl"]
method:get
data: None
return:{
    ResponseResult<List<News>>    //  列表形式的所有信息
}
```
### upload
```
url:/api/news/{type}    // 表示是哪个子栏，取值["hyzx","zgdt","mryl"]
method:post
data:{
    String date    // YYYY-MM-DD
    String title
    String photo
    String url
    String description

return:{
    success
}
```


## ProductController

### getAllProduct

``` 
如果处于未登陆状态则获取所有商品,如果处于登陆状态则获取收藏商品
url: /api/product
method: get
data: null
return: {
	List<Product>  // 商品列表
}
```

### getShoppingByUser

``` 
获取用户购物车
url: /api/product/shopping
method: get
data: null
return: {
	List<Shopping>  // 购物车列表
}
```

### updateShoppingNumber

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

### addShopping

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

### deleteShopping

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

### getAllProductByAdmin

``` 
管理员获取全部商品信息
url: /api/product/admin
method: get
data: null
return: {
	List<Product>  // 商品列表
}
```
### addProductByAdmin

```
管理员添加商品
url: /api/product/admin
method: post
body: {
	name,
	price,
	launch_time,	// yyyy-mm-dd
	photo,	// url
	introduction,
	storage
}
return: {
	status:success/error
	
}
```
### updateProductNumberByAdmin

```
管理员修改商品信息
在前端要带上未修改的字段
url: /api/product/admin/{productId}
method: post
parameters: {
	同addProductByAdmin
}
return: {
	status:success/error
}
```

### deleteProductByAdmin

```
管理员删除商品
url: /api/product/admin
method: delete
body: {
	id	// product id
}
return: {
	status:success/error
	
}
```

## Order
### 类定义
```java
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date orderDate;
    @TableField(exist = false)
    private Address address;
    private Integer addressId;
    @TableField(exist = false)
    private List<Shopping> shoppings;
    private String status;
}
```

### getAllOrders

``` 
获取当前登录用户全部订单信息
url: /api/order
method: get
data: null
return: {
	List<Order>  // 订单列表
}
```

### addOrder

``` 
为当前用户添加一个订单
url: /api/order
method: post
data: {
	Date orderDate
	Address address
	List<Shopping> shoppings
}
return: {
	success
}
```

### removeOrder

``` 
为当前用户删除一个订单
url: /api/order
method: post
data: {
	Integer id	// order的id
}
return: {
	success
}
```
### changeStatus

``` 
更改订单状态，包含PROCESSING,SUCCESS,FAIL
url: /api/order/change
method: post
data: {
	String id	// order的id
	String stauts	// 上述三种状态
}
return: {
	success
}
```
