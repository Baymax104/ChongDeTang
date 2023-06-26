package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdtde.chongdetang.mapper.CulturalMapper;
import com.cdtde.chongdetang.pojo.Culture;
import com.cdtde.chongdetang.pojo.Result;
import com.cdtde.chongdetang.service.CultureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CultureServiceImpl implements CultureService {
    @Autowired
    private CulturalMapper culturalMapper;

    @Override
    public Result<List<Culture>> download(String type) {
        QueryWrapper<Culture> query = new QueryWrapper<>();
        query.eq("type", type);
        List<Culture> cultureList = culturalMapper.selectList(query);
        return new Result<>("success", null, cultureList);
    }

    @Override
    public Result<Object> upload(String type, String date, String title, String url, String description, String photo) {
        Culture culture = new Culture(date, title, photo, url, type, description);
        culturalMapper.insert(culture);
        return new Result<>("success", null, null);
    }
}