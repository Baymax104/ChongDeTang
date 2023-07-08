package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.Collection;
import com.cdtde.chongdetang.pojo.Result;
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

    @GetMapping("/{type}")
    public Result<List<Collection>> getCollection(@PathVariable String type) {
        return collectionService.getCollection(type);
    }

    @GetMapping("/hot")
    public Result<List<Collection>> getHot() {
        return collectionService.getHot();
    }

    @GetMapping("/not-hot")
    public Result<List<Collection>> getNotHot() {
        return collectionService.getNotHot();
    }

    @PostMapping("/select")
    public Result<Object> setSelected(@RequestBody List<Collection> collectionList) {
        try {
            // 批量更新
            collectionService.updateBatchById(collectionList);
        } catch (Exception e) {
            return new Result<>().setStatus("error").setMessage(e.getMessage());
        }
        return new Result<>().setStatus("success");
    }

    @GetMapping("/admin")
    public Result<List<Collection>> getAllCollection(){
        return collectionService.getAllCollectionByAdmin();
    }

    @PostMapping("/admin")
    public Result<Integer> addCollection(@RequestBody Collection collection){
        return collectionService.addCollectionByAdmin(collection);
    }

    @PostMapping("/admin/{collectionId}")
    public Result<Object> updateCollection(@PathVariable("collectionId") Integer collectionId, @RequestBody Collection collection){
        return collectionService.updateCollectionByAdmin(collectionId,collection);
    }

    @DeleteMapping("/admin")
    public Result<Object> removeCollection(@RequestBody Collection collection){
        return collectionService.removeCollectionByAdmin(collection);
    }
}