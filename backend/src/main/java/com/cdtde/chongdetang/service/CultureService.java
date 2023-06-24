package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Culture;
import com.cdtde.chongdetang.pojo.Result;

import java.util.List;

public interface CultureService {
    Result<List<Culture>> download();

    Result<Object> upload(String type, String date, String title, String url, String description, String photo);
}