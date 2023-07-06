package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Culture;
import com.cdtde.chongdetang.pojo.Result;

import java.util.List;

public interface CultureService {
    Result<List<Culture>> getCultures(String type);

    Result<Object> addCulture(Culture culture);
}