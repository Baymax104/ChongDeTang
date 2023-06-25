package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.Result;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/19 22:46
 * @Version 1
 */
public interface UserCollectService {
    @GET("/api/user/collect/collection")
    Single<Result<List<Collection>>> getUserCollection(@Header("Authorization") String token);

    @GET("/api/user/collect/product")
    Single<Result<List<Product>>> getUserProduct(@Header("Authorization") String token);

    @POST("/api/user/collect/collection")
    Single<Result<Object>> addUserCollection(@Header("Authorization") String token,
                                             @Body Collection collection);

    @HTTP(path = "/api/user/collect/collection", method = "DELETE", hasBody = true)
    Single<Result<Object>> removeUserCollection(@Header("Authorization") String token,
                                                @Body Collection collection);

    @POST("/api/user/collect/product")
    Single<Result<Object>> addUserProduct(@Header("Authorization") String token,
                                          @Body Product product);

    @HTTP(path = "/api/user/collect/product", method = "DELETE", hasBody = true)
    Single<Result<Object>> removeUserProduct(@Header("Authorization") String token,
                                             @Body Product product);

}
