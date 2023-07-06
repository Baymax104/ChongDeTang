package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdtde.chongdetang.mapper.NewsMapper;
import com.cdtde.chongdetang.pojo.News;
import com.cdtde.chongdetang.pojo.Result;
import com.cdtde.chongdetang.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;

    @Override
    public Result<List<News>> getNews(String type) {

        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        List<News> newsList = newsMapper.selectList(queryWrapper);

        Result<List<News>> result = new Result<>();
        result.setStatus("success").setData(newsList);
        return result;
    }

    @Override
    public Result<Object> addNews(News news) {
        newsMapper.insert(news);
        return new Result<>("success", null, null);
    }
}
