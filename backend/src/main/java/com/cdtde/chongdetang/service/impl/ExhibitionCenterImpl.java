package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdtde.chongdetang.mapper.ExhibitionCenterMapper;
import com.cdtde.chongdetang.pojo.ExhibitionCenter;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.ExhibitionCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ExhibitionCenterImpl implements ExhibitionCenterService {
    @Autowired
    private ExhibitionCenterMapper exhibitionCenterMapper;

    @Override
    public ResponseResult<Object> upload(String type, String title, String photo, String url){
        ExhibitionCenter exhibitionCenter = new ExhibitionCenter(type, title, photo,url);
        exhibitionCenterMapper.insert(exhibitionCenter);

        ResponseResult<Object> result = new ResponseResult<>();
        result.setStatus("success");
        return result;
    }

    @Override
    public ResponseResult<List<ExhibitionCenter>> download(String type){

        QueryWrapper<ExhibitionCenter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",type);
        List<ExhibitionCenter> exhibitionCenterList = exhibitionCenterMapper.selectList(queryWrapper);

        ResponseResult<List<ExhibitionCenter>> result = new ResponseResult<>();
        result.setStatus("success").setData(exhibitionCenterList);
        return result;
    }
}