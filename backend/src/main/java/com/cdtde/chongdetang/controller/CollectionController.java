package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.Collection;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.CollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/not-hot")
    public ResponseResult<List<Collection>> getNotHot() {
        return collectionService.getNotHot();
    }

    @PostMapping("/select")
    public ResponseResult<Object> setSelected(@RequestBody List<Collection> collectionList){
        try {
            // 批量更新
            collectionService.updateBatchById(collectionList);
        } catch (Exception e){
            return new ResponseResult<>().setStatus("error").setMessage(e.getMessage());
        }
        return new ResponseResult<>().setStatus("success");
    }
}