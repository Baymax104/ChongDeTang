package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.Result;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * @Author John
 */
public interface SearchService {

    /**
     * 搜索商品请求接口
     * @param token 用户token
     * @param content 输入内容
     * @return 商品结果列表
     */
    @GET("/api/search/product")
    Single<Result<List<Product>>> searchProduct(@Header("Authorization") String token,
                                                @Query("content") String content);

    /**
     * 搜索藏品请求接口
     * @param token 用户token
     * @param content 输入内容
     * @return 藏品结果列表
     */
    @GET("/api/search/collection")
    Single<Result<List<Collection>>> searchCollection(@Header("Authorization") String token,
                                                      @Query("content") String content);
}
