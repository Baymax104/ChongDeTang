package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.Culture;
import com.cdtde.chongdetang.entity.Result;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/18 11:30
 * @Version 1
 */
public interface CultureService {

    @GET("/api/culture/{type}")
    Single<Result<List<Culture>>> getAllCulture(@Path("type") String type);
}
