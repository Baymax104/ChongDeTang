package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.entity.Result;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 收货地址服务API
 * @author John
 */
public interface AddressService {

    /**
     * 获取所有地址信息
     * @param token 用户token
     * @return {@link Address} 列表
     */
    @GET("/api/user/address")
    Single<Result<List<Address>>> getAllAddress(@Header("Authorization") String token);

    @POST("/api/user/address")
    Single<Result<Object>> updateAddress(@Header("Authorization") String token,
                                         @Body Address address);

    @HTTP(path = "/api/user/address", method = "DELETE", hasBody = true)
    Single<Result<Object>> deleteAddress(@Header("Authorization") String token,
                                         @Body Address address);
}
