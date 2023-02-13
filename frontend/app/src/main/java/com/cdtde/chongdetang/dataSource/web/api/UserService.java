package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.ResponseResult;
import com.cdtde.chongdetang.entity.User;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/2 2:43
 * @Version 1
 */
public interface UserService {

    @POST("/api/user/register")
    Observable<ResponseResult<Object>> register(@Body Map<String, String> map);

    @POST("/api/user/login")
    Observable<ResponseResult<User>> login(@Body Map<String, String> map);

    @POST("/api/user/update/info")
    Observable<ResponseResult<User>> updateInfo(@Header("Authorization") String token,
                                                @Body Map<String, Object> map);

    @POST("/api/user/update/password")
    Observable<ResponseResult<String>> updatePassword(@Header("Authorization") String token,
                                                      @Body Map<String, String> map);

    @POST("/api/user/update/phone")
    Observable<ResponseResult<Object>> updatePhone(@Header("Authorization") String token,
                                                   @Body String phone);
}
