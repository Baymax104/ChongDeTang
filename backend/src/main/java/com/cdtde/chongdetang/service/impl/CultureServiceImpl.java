package com.cdtde.chongdetang.service.impl;

import com.cdtde.chongdetang.mapper.CulturalMapper;
import com.cdtde.chongdetang.pojo.Culture;
import com.cdtde.chongdetang.pojo.ResponseResult;
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
    public ResponseResult<List<Culture>> download() {
        List<Culture> cultureList = culturalMapper.selectList(null);
        ResponseResult<List<Culture>> result = new ResponseResult<>();
        result.setStatus("success").setData(cultureList);
        return result;
    }

    @Override
    public ResponseResult<Object> upload(String type, String date, String title, String url, String description, String photo) {
        Culture culture = new Culture(date, title, photo, url, type, description);
        culturalMapper.insert(culture);
        return new ResponseResult<>("success", null, null);
    }
}