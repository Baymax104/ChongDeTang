package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.ResponseResult;
import com.cdtde.chongdetang.entity.Shopping;

import java.util.List;

import io.reactivex.Observable;
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
    Observable<ResponseResult<List<Product>>> getAllProduct();

    @GET("/api/product/shopping")
    Observable<ResponseResult<List<Shopping>>> getShoppingByUser(@Header("Authorization") String token);

    @POST("/api/product/shopping/{shoppingId}/{productId}")
    Observable<ResponseResult<Integer>> updateShoppingNumber(@Header("Authorization") String token,
                                                            @Path("shoppingId") Integer shoppingId,
                                                            @Path("productId") Integer productId,
                                                            @Query("number") Integer number);
    @POST("/api/product/shopping")
    Observable<ResponseResult<Object>> addShopping(@Header("Authorization") String token,
                                                   @Body Shopping shopping);

    @HTTP(path = "/api/product/shopping", method = "DELETE", hasBody = true)
    Observable<ResponseResult<Object>> deleteShopping(@Header("Authorization") String token,
                                                      @Body Shopping shopping);
}
