package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.Result;
import com.cdtde.chongdetang.entity.Shopping;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/9 23:00
 * @Version 1
 */
public interface ProductService {

    @GET("/api/product")
    Single<Result<List<Product>>> getAllProduct(@Header("Authorization") String token);

    @GET("/api/product/hot")
    Single<Result<List<Product>>> getHotProduct();

    @GET("/api/product/shopping")
    Single<Result<List<Shopping>>> getShoppingByUser(@Header("Authorization") String token);

    @POST("/api/product/shopping/{shoppingId}/{productId}")
    Single<Result<Integer>> updateShoppingNumber(@Header("Authorization") String token,
                                                 @Path("shoppingId") Integer shoppingId,
                                                 @Path("productId") Integer productId,
                                                 @Query("number") Integer number);

    @POST("/api/product/shopping")
    Single<Result<Object>> addShopping(@Header("Authorization") String token,
                                       @Body Shopping shopping);

    @HTTP(path = "/api/product/shopping", method = "DELETE", hasBody = true)
    Single<Result<Object>> deleteShopping(@Header("Authorization") String token,
                                          @Body Shopping shopping);
}
