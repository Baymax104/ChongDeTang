package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.News;
import com.cdtde.chongdetang.entity.ResponseResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/18 19:49
 * @Version 1
 */
public interface NewsService {

    @GET("/api/news/{type}")
    Observable<ResponseResult<List<News>>> getNews(@Path("type") String type);
}
