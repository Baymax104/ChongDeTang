package com.cdtde.chongdetang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdtde.chongdetang.pojo.Collection;
import com.cdtde.chongdetang.pojo.Result;

import java.util.List;

public interface CollectionService extends IService<Collection> {
    /**
     * 根据藏品类型获取所有藏品
     * @param type 藏品类型
     * @return Collection列表
     */
    Result<List<Collection>> getCollection(String type);

    Result<List<Collection>> getHot();

    Result<List<Collection>> getNotHot();

    /**
     * 获取所有藏品（管理员）
     * @return Collection列表
     */
    Result<List<Collection>> getAllCollectionByAdmin();

    /**
     * 添加藏品(管理员)
     * @param collection 藏品
     * @return success
     */
    Result<Integer> addCollectionByAdmin(Collection collection);

    /**
     * 修改藏品（管理员）
     * @param collectionId 藏品id
     * @param collection    藏品
     * @return  success
     */
    Result<Object> updateCollectionByAdmin(Integer collectionId, Collection collection);

    /**
     * 删除某一藏品(管理员)
     * @param collection 藏品id
     * @return  success
     */

    Result<Object> removeCollectionByAdmin(Collection collection);
}