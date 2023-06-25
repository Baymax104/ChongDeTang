package com.cdtde.chongdetang.repository;

import android.annotation.SuppressLint;

import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.CollectionService;
import com.cdtde.chongdetang.dataSource.web.api.CultureService;
import com.cdtde.chongdetang.dataSource.web.api.NewsService;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.Culture;
import com.cdtde.chongdetang.entity.News;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/13 1:33
 * @Version 1
 */
@SuppressLint("CheckResult")
public class IndexRepository {

    private UserStore userRepo;
    private static IndexRepository repository;

    private List<News> couplet;
    private List<News> moments;
    private List<News> infos;
    private List<Culture> cultures;
    private List<Collection> hotCollection;

    private CultureService cultureService;
    private NewsService newsService;
    private CollectionService collectionService;


    private IndexRepository() {
//        userRepo = UserStore.getInstance();
        couplet = new ArrayList<>();
        cultures = new ArrayList<>();
        moments = new ArrayList<>();
        infos = new ArrayList<>();
        hotCollection = new ArrayList<>();

        cultureService = WebService.getInstance().create(CultureService.class);
        newsService = WebService.getInstance().create(NewsService.class);
        collectionService = WebService.getInstance().create(CollectionService.class);
    }

    public static IndexRepository getInstance() {
        if (repository == null) {
            repository = new IndexRepository();
        }
        return repository;
    }


    public List<Culture> getCultures() {
        return cultures;
    }

    public List<News> getCouplet() {
        return couplet;
    }

    public List<News> getMoments() {
        return moments;
    }

    public List<News> getInfos() {
        return infos;
    }

    public List<Collection> getHotCollection() {
        return hotCollection;
    }

//    public void requestAllCulture() {
//        Consumer<Result<List<Culture>>> onNext = result -> {
//            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
//            if (isSuccess) {
//                cultures = result.getData();
//            }
//            LiveEventBus.get("IndexRepository-requestAllCulture", WebException.class)
//                    .post(new WebException(isSuccess, result.getMessage()));
//        };
//
//        Consumer<Throwable> onError = throwable ->
//                LiveEventBus.get("IndexRepository-requestAllCulture", WebException.class)
//                        .post(new WebException(false, throwable.getMessage()));
//
//        cultureService.getAllCulture()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(onNext, onError);
//    }

//    public void requestNews(String type) {
//        Consumer<Result<List<News>>> onNext = result -> {
//            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
//            if (isSuccess) {
//                if ("mryl".equals(type)) {
//                    couplet = result.getData();
//                } else if ("zgdt".equals(type)) {
//                    moments = result.getData();
//                } else if ("hyzx".equals(type)) {
//                    infos = result.getData();
//                }
//            }
//            LiveEventBus.get("IndexRepository-requestNews-" + type, WebException.class)
//                    .post(new WebException(isSuccess, result.getMessage()));
//        };
//
//        Consumer<Throwable> onError = throwable ->
//                LiveEventBus.get("IndexRepository-requestNews-" + type, WebException.class)
//                        .post(new WebException(false, throwable.getMessage()));
//
//        newsService.getNews(type)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(onNext, onError);
//    }

//    public void requestHotCollection() {
//        Consumer<Result<List<Collection>>> onNext = result -> {
//            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
//            if (isSuccess) {
//                hotCollection = result.getData();
//            }
//            LiveEventBus.get("IndexRepository-requestHotCollection", WebException.class)
//                    .post(new WebException(isSuccess, result.getMessage()));
//        };
//
//        Consumer<Throwable> onError = throwable ->
//                LiveEventBus.get("IndexRepository-requestHotCollection", WebException.class)
//                        .post(new WebException(false, throwable.getMessage()));
//
//        collectionService.getHotCollection()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(onNext, onError);
//    }
}
