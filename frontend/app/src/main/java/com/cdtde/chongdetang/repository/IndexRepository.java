package com.cdtde.chongdetang.repository;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.LogUtils;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.CollectionService;
import com.cdtde.chongdetang.dataSource.web.api.CultureService;
import com.cdtde.chongdetang.dataSource.web.api.NewsService;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.Culture;
import com.cdtde.chongdetang.entity.News;
import com.cdtde.chongdetang.entity.ResponseResult;
import com.cdtde.chongdetang.entity.User;
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
 * @Date 2023/1/13 1:33
 * @Version 1
 */
@SuppressLint("CheckResult")
public class IndexRepository {

    private UserRepository userRepo;
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
        userRepo = UserRepository.getInstance();
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

    public User getUser() {
        return userRepo.getUser();
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

    public void requestAllCulture() {
        Consumer<ResponseResult<List<Culture>>> onNext = result -> {
            if (result.getStatus().equals("success")) {
                if (result.getData() != null) {
                    cultures = result.getData();
                    LiveEventBus.get("IndexRepository-requestAllCulture", Boolean.class).post(true);
                    LogUtils.iTag("cdt-web-requestAllCulture", "获取“崇德讲堂”成功");
                }
            } else {
                LogUtils.eTag("cdt-web-requestAllCulture", result.getMessage());
            }
        };

        cultureService.getAllCulture()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-web-requestAllCulture", throwable),
                        () -> LogUtils.iTag("cdt-web-requestAllCulture", "获取“崇德讲堂”请求结束")
                );
    }

    public void requestNews(String type) {
        Consumer<ResponseResult<List<News>>> onNext = result -> {
            if (result.getStatus().equals("success")) {
                if (result.getData() != null) {
                    if ("mryl".equals(type)) {
                        couplet = result.getData();
                    } else if ("zgdt".equals(type)) {
                        moments = result.getData();
                    } else if ("hyzx".equals(type)) {
                        infos = result.getData();
                    }
                    LiveEventBus.get("IndexRepository-requestNews-" + type, Boolean.class).post(true);
                    LogUtils.iTag("cdt-web-requestNews", "获取news成功");
                }
            } else {
                LogUtils.eTag("cdt-web-requestNews", result.getMessage());
            }
        };

        newsService.getNews(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-web-requestNews", throwable),
                        () -> LogUtils.iTag("cdt-web-requestNews", "获取news请求结束")
                );
    }

    public void requestHotCollection() {
        Consumer<ResponseResult<List<Collection>>> onNext = result -> {
            if (result.getStatus().equals("success")) {
                if (result.getData() != null) {
                    hotCollection = result.getData();
                    LiveEventBus.get("IndexRepository-requestHotCollection", Boolean.class).post(true);
                    LogUtils.iTag("cdt-web-requestHotCollection", "获取藏品精选成功");
                }
            } else {
                LogUtils.eTag("cdt-web-requestHotCollection", result.getMessage());
            }
        };

        collectionService.getHotCollection()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-web-requestHotCollection", throwable),
                        () -> LogUtils.iTag("cdt-web-requestHotCollection", "获取藏品精选请求结束")
                );
    }
}
