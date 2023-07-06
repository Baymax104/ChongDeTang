package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.News;
import com.cdtde.chongdetang.pojo.Result;

import java.util.List;

public interface NewsService {
    Result<List<News>> getNews(String type);

    Result<Object> addNews(News news);
}
