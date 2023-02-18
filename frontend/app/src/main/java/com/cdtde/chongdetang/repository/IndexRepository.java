package com.cdtde.chongdetang.repository;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.LogUtils;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.CultureService;
import com.cdtde.chongdetang.dataSource.web.api.NewsService;
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

    private List<News> news;

    private List<Culture> cultures;

    private CultureService cultureService;

    private NewsService newsService;


    private IndexRepository() {
        userRepo = UserRepository.getInstance();
        news = new ArrayList<>();
        cultures = new ArrayList<>();

        cultureService = WebService.getInstance().create(CultureService.class);
        newsService = WebService.getInstance().create(NewsService.class);
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

    public List<News> getNews() {
        return news;
    }

    public void getAllCulture() {
        Consumer<ResponseResult<List<Culture>>> onNext = result -> {
            if (result.getStatus().equals("success")) {
                if (result.getData() != null) {
                    cultures = result.getData();
                    LiveEventBus.get("IndexRepository-getAllCulture", Boolean.class).post(true);
                    LogUtils.iTag("cdt-web-getAllCulture", "获取“崇德讲堂”成功");
                }
            } else {
                LogUtils.eTag("cdt-web-getAllCulture", result.getMessage());
            }
        };

        cultureService.getAllCulture()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-web-getAllCulture", throwable),
                        () -> LogUtils.iTag("cdt-web-getAllCulture", "获取“崇德讲堂”请求结束")
                );
    }

    public void getNews(String type) {
        Consumer<ResponseResult<List<News>>> onNext = result -> {
            if (result.getStatus().equals("success")) {
                if (result.getData() != null) {
                    news = result.getData();
                    LiveEventBus.get("IndexRepository-getNews-" + type, Boolean.class).post(true);
                    LogUtils.iTag("cdt-web-getNews", "获取news成功");
                }
            } else {
                LogUtils.eTag("cdt-web-getNews", result.getMessage());
            }
        };

        newsService.getNews(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-web-getNews", throwable),
                        () -> LogUtils.iTag("cdt-web-getNews", "获取news请求结束")
                );
    }
}
