package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdtde.chongdetang.mapper.CulturalKnowledgeMapper;
import com.cdtde.chongdetang.pojo.CulturalKnowledge;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.CulturalKnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CulturalKnowledgeImpl implements CulturalKnowledgeService {
    @Autowired
    private CulturalKnowledgeMapper culturalKnowledgeMapper;

    @Override
    public ResponseResult<Object> upload(String type, String date, String title, String photo, String url, String aabstract){
        CulturalKnowledge culturalKnowledge = new CulturalKnowledge(type,date,title,photo,url,aabstract);
        culturalKnowledgeMapper.insert(culturalKnowledge);

        ResponseResult<Object> result = new ResponseResult<>();
        result.setStatus("success");
        return result;
    }

    @Override
    public ResponseResult<List<CulturalKnowledge>> download(String type){

        QueryWrapper<CulturalKnowledge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",type);
        List<CulturalKnowledge> culturalKnowledgeList = culturalKnowledgeMapper.selectList(queryWrapper);

        ResponseResult<List<CulturalKnowledge>> result = new ResponseResult<>();
        result.setStatus("success").setData(culturalKnowledgeList);
        return result;
    }
}