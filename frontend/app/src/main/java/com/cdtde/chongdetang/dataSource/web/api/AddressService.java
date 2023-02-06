package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.entity.ResponseResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/5 23:38
 * @Version 1
 */
public interface AddressService {

    @GET("/api/user/address")
    Observable<ResponseResult<List<Address>>> getAllAddress(@Header("Authorization") String token);

    @POST("/api/user/address")
    Observable<ResponseResult<Object>> updateAddress(@Header("Authorization") String token,
                                                      @Body Address address);
}
