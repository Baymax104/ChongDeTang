package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdtde.chongdetang.mapper.CulturalMapper;
import com.cdtde.chongdetang.pojo.Cultural;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.CulturalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CulturalServiceImpl implements CulturalService {
    @Autowired
    private CulturalMapper culturalMapper;

    @Override
    public ResponseResult<List<Cultural>> download(String type){

        QueryWrapper<Cultural> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",type);
        List<Cultural> culturalList = culturalMapper.selectList(queryWrapper);

        ResponseResult<List<Cultural>> result = new ResponseResult<>();
        result.setStatus("success").setData(culturalList);
        return result;
    }
}