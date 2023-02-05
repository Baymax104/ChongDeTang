package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdtde.chongdetang.mapper.NewsCenterMapper;
import com.cdtde.chongdetang.pojo.NewsCenter;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.NewsCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NewsCenterServiceImpl implements NewsCenterService {
    @Autowired
    private NewsCenterMapper newsCenterMapper;

    @Override
    public ResponseResult<Object> upload(String type, String date,String title,String photo,String url,String aabstract){
        NewsCenter newsCenter = new NewsCenter(type,date,title,photo,url,aabstract);
        newsCenterMapper.insert(newsCenter);

        ResponseResult<Object> result = new ResponseResult<>();
        result.setStatus("success");
        return result;
    }

    @Override
    public ResponseResult<List<NewsCenter>> download(String type){

        QueryWrapper<NewsCenter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",type);
        List<NewsCenter> newsCenterList = newsCenterMapper.selectList(queryWrapper);

        ResponseResult<List<NewsCenter>> result = new ResponseResult<>();
        result.setStatus("success").setData(newsCenterList);
        return result;
    }
}
