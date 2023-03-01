package com.cdtde.chongdetang.repository;

import android.annotation.SuppressLint;

import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.CollectionService;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.ResponseResult;
import com.cdtde.chongdetang.exception.WebException;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/12 23:22
 * @Version 1
 */
@SuppressLint("CheckResult")
public class ExhibitRepository {

    private UserRepository userRepo;
    private static ExhibitRepository repository;

    private List<Collection> collections;

    private CollectionService collectionService;

    public ExhibitRepository() {
        userRepo = UserRepository.getInstance();
        collections = new ArrayList<>();

        collectionService = WebService.getInstance().create(CollectionService.class);
    }

    public static ExhibitRepository getInstance() {
        if (repository == null) {
            repository = new ExhibitRepository();
        }
        return repository;
    }

    public List<Collection> getCollections() {
        return collections;
    }
    public void requestAllCollection() {
        Consumer<ResponseResult<List<Collection>>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                collections = result.getData();
            }
            LiveEventBus.get("ExhibitRepository-requestAllCollection", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("ExhibitRepository-requestAllCollection", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        collectionService.getAllCollection()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }
}
