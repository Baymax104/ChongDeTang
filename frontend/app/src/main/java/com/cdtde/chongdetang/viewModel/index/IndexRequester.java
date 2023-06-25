package com.cdtde.chongdetang.viewModel.index;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.Info;
import com.cdtde.chongdetang.entity.Moment;
import com.cdtde.chongdetang.repository.CollectionRepository;
import com.cdtde.chongdetang.repository.InfoRepository;
import com.cdtde.chongdetang.repository.MomentRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/25 23:12
 * @Version 1
 */
public class IndexRequester extends Requester {

    private MomentRepository momentRepo = MomentRepository.getInstance();

    private InfoRepository infoRepo = InfoRepository.getInstance();

    private CollectionRepository collectionRepo = CollectionRepository.getInstance();


    public void updateAllMoment(Consumer<List<Moment>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Moment>> callback = new ReqCallback<>(onSuccess, onFail, this);
        momentRepo.requestAllMoment(callback);
    }

    public void updateAllInfo(Consumer<List<Info>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Info>> callback = new ReqCallback<>(onSuccess, onFail, this);
        infoRepo.requestAllInfo(callback);
    }

    public void updateHotCollection(Consumer<List<Collection>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Collection>> callback = new ReqCallback<>(onSuccess, onFail, this);
        collectionRepo.requestHotCollection(callback);
    }

}
