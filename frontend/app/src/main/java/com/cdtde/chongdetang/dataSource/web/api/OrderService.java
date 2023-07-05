package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.Order;
import com.cdtde.chongdetang.entity.Result;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * @Author John
 */
public interface OrderService {

    /**
     * 获取用户所有订单
     * @param token 用户token
     * @return Order列表
     */
    @GET("/api/order")
    Single<Result<List<Order>>> getAllOrders(@Header("Authorization") String token);

    /**
     * 添加订单
     * @param token 用户token
     * @param order order对象，json格式传输
     * @return 结果状态
     */
    @POST("/api/order")
    Single<Result<Object>> addOrder(@Header("Authorization") String token,
                                    @Body Order order);

    /**
     * 删除订单
     * @param token 用户token
     * @param order order对象，json格式传输
     * @return 结果状态
     */
    @HTTP(path = "/api/order", method = "DELETE", hasBody = true)
    Single<Result<Object>> removeOrder(@Header("Authorization") String token,
                                       @Body Order order);

    /**
     * 确认订单
     * @param token 用户token
     * @param orderId 订单id
     * @param status 确认状态
     * @return 结果状态
     */
    @FormUrlEncoded
    @POST("/api/order/change")
    Single<Result<Object>> confirmOrder(@Header("Authorization") String token,
                                        @Field("id") Integer orderId,
                                        @Field("status") String status);
}
