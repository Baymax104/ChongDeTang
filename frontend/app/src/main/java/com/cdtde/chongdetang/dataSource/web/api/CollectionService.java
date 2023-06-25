package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.Result;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/13 1:45
 * @Version 1
 */
public interface CollectionService {
    @GET("/api/collection/{type}")
    Single<Result<List<Collection>>> getAllCollection(@Header("Authorization") String token,
                                                      @Path("type") String type);

    @GET("/api/collection/hot")
    Single<Result<List<Collection>>> getHotCollection();
}
