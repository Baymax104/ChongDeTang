package com.cdtde.chongdetang.viewModel.index;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.LogUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.News;
import com.cdtde.chongdetang.repository.IndexRepository;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/25 23:12
 * @Version 1
 */
public class IndexViewModel extends ViewModel {

    private IndexRepository repo;

    private MutableLiveData<List<Integer>> bannerImg;

    private MutableLiveData<List<Collection>> hotCollections;

    private MutableLiveData<List<News>> moments;

    private MutableLiveData<List<News>> infos;

    private boolean isMomentInit;
    private boolean isInfoInit;
    private boolean isHotCollectionInit;


    public IndexViewModel() {
        repo = IndexRepository.getInstance();
        bannerImg = new MutableLiveData<>();
        hotCollections = new MutableLiveData<>();
        moments = new MutableLiveData<>();
        infos = new MutableLiveData<>();

        List<Integer> imgs = new ArrayList<>();
        imgs.add(R.drawable.index_banner1);
        imgs.add(R.drawable.index_banner2);
        imgs.add(R.drawable.index_banner3);
        bannerImg.setValue(imgs);

        isMomentInit = false;
        isInfoInit = false;
        isHotCollectionInit = false;
    }

    public MutableLiveData<List<Integer>> getBannerImg() {
        return bannerImg;
    }

    public MutableLiveData<List<Collection>> getHotCollections() {
        return hotCollections;
    }

    public MutableLiveData<List<News>> getMoments() {
        return moments;
    }

    public MutableLiveData<List<News>> getInfos() {
        return infos;
    }

    public void updateAllMoment() {
        repo.requestNews("zgdt");
    }

    public void updateAllInfo() {
        repo.requestNews("hyzx");
    }

    public void updateHotCollection() {
        repo.requestHotCollection();
    }

    public boolean isMomentInit() {
        return isMomentInit;
    }

    public void setMomentInit(boolean momentInit) {
        isMomentInit = momentInit;
        if (momentInit) {
            LogUtils.iTag("cdt-index", "index-moment初始化完成");
        }
    }

    public boolean isInfoInit() {
        return isInfoInit;
    }

    public void setInfoInit(boolean infoInit) {
        isInfoInit = infoInit;
        if (infoInit) {
            LogUtils.iTag("cdt-index", "index-info初始化完成");
        }
    }

    public boolean isHotCollectionInit() {
        return isHotCollectionInit;
    }

    public void setHotCollectionInit(boolean hotCollectionInit) {
        isHotCollectionInit = hotCollectionInit;
        if (hotCollectionInit) {
            LogUtils.iTag("cdt-index", "index-hotCollection初始化完成");
        }
    }

    public void refreshAllMoment() {
        List<News> moments1 = repo.getMoments();
        moments.setValue(moments1);
        LiveEventBus.get("IndexFragment-allMoment", News[].class)
                .post(moments1.toArray(new News[0]));
    }

    public void refreshAllInfo() {
        List<News> infos1 = repo.getInfos();
        infos.setValue(infos1);
        LiveEventBus.get("IndexFragment-allInfo", News[].class)
                .post(infos1.toArray(new News[0]));
    }

    public void refreshHotCollection() {
        hotCollections.setValue(repo.getHotCollection());
    }
}
