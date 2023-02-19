package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.Collection;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.CollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/collection")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @GetMapping
    public ResponseResult<List<Collection>> download(){
        return collectionService.download();
    }

    @GetMapping("/hot")
    public ResponseResult<List<Collection>> getHot() {
        return collectionService.getHot();
    }
}