package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.Result;
import com.cdtde.chongdetang.entity.User;

import java.util.Map;

import io.reactivex.Single;
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
    Single<Result<Object>> register(@Body Map<String, String> map);

    @POST("/api/user/login")
    Single<Result<User>> login(@Body Map<String, String> map);

    @POST("/api/user/update/info")
    Single<Result<User>> updateInfo(@Header("Authorization") String token,
                                    @Body User newUser);

    @POST("/api/user/update/password")
    Single<Result<String>> updatePassword(@Header("Authorization") String token,
                                          @Body Map<String, String> map);

    @POST("/api/user/update/phone")
    Single<Result<Object>> updatePhone(@Header("Authorization") String token,
                                       @Body String phone);

    @POST("/api/user/feedback")
    Single<Result<Object>> addFeedback(@Header("Authorization") String token,
                                       @Body String feedbackContent);
}
