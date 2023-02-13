package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.ResponseResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/13 1:45
 * @Version 1
 */
public interface CollectionService {
    @GET("/api/collection")
    Observable<ResponseResult<List<Collection>>> getAllCollection();
}
