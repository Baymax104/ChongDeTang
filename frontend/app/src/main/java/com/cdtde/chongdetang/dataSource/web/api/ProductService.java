package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.ResponseResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

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
}
