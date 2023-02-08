package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.CulturalKnowledge;
import com.cdtde.chongdetang.pojo.ResponseResult;

import java.util.List;

public interface CulturalKnowledgeService {
    ResponseResult<Object> upload(String type, String date, String title, String photo, String url, String aabstract);
    ResponseResult<List<CulturalKnowledge>> download(String type);
}