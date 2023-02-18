package com.cdtde.chongdetang.viewModel.index;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    private MutableLiveData<List<Collection>> collections;

    private MutableLiveData<List<News>> moments;

    private MutableLiveData<List<News>> infos;


    public IndexViewModel() {
        repo = IndexRepository.getInstance();
        bannerImg = new MutableLiveData<>();
        collections = new MutableLiveData<>();
        moments = new MutableLiveData<>();
        infos = new MutableLiveData<>();

        List<Integer> imgs = new ArrayList<>();
        imgs.add(R.drawable.index_banner1);
        imgs.add(R.drawable.index_banner2);
        imgs.add(R.drawable.index_banner3);
        bannerImg.setValue(imgs);

        updateAllMoment();
        updateAllInfo();
        generateTest();
    }

    public MutableLiveData<List<Integer>> getBannerImg() {
        return bannerImg;
    }

    public MutableLiveData<List<Collection>> getCollections() {
        return collections;
    }

    public MutableLiveData<List<News>> getMoments() {
        return moments;
    }

    public MutableLiveData<List<News>> getInfos() {
        return infos;
    }

    public void updateAllMoment() {
        repo.getNews("zgdt");
    }

    public void updateAllInfo() {
        repo.getNews("hyzx");
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

    private void generateTest() {
        List<Collection> value2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            value2.add(new Collection());
        }
        collections.setValue(value2);
    }
}
