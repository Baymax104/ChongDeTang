package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.News;
import com.cdtde.chongdetang.pojo.Result;

import java.util.List;

public interface NewsService {
    Result<List<News>> download(String type);

    Result<Object> upload(String date, String title, String photo, String url, String type, String description);
}
