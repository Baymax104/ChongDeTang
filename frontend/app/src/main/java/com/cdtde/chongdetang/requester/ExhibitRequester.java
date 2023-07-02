package com.cdtde.chongdetang.requester;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.repository.CollectionRepository;
import com.cdtde.chongdetang.repository.UserCollectRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 22:32
 * @Version 1
 */
public class ExhibitRequester extends Requester {

    private CollectionRepository repo = CollectionRepository.getInstance();

    private UserCollectRepository userCollectRepo = UserCollectRepository.getInstance();


    public void updateAllCollectionByType(String type,
                                          Consumer<List<Collection>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Collection>> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestCollectionByType(type, callback);
    }

    public void updateUserCollection(Collection collection,
                                     Consumer<Collection> onSuccess, Consumer<String> onFail) {
        boolean isCollect = collection.isUserCollect();
        ReqCallback<Collection> callback = new ReqCallback<>(onSuccess, onFail, this);
        if (isCollect) { // 取消收藏
            userCollectRepo.requestRemoveUserCollectionWithoutList(collection, callback);
        } else { // 添加收藏
            userCollectRepo.requestAddUserCollection(collection, callback);
        }
    }
}
