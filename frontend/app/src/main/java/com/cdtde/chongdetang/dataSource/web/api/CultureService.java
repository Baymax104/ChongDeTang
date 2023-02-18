package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.Culture;
import com.cdtde.chongdetang.entity.ResponseResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/18 11:30
 * @Version 1
 */
public interface CultureService {

    @GET("/api/culture")
    Observable<ResponseResult<List<Culture>>> getAllCulture();
}
