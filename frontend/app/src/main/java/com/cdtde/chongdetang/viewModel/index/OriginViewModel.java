package com.cdtde.chongdetang.viewModel.index;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class OriginViewModel extends ViewModel {

    private int index;
    private List<String> urls;

    private MutableLiveData<String> webUrl;

    public OriginViewModel(){
        urls = new ArrayList<>();
        urls.add("http://www.cdtde.com/m/list.php?tid=12");     //走进崇德堂
        urls.add("http://www.cdtde.com/m/list.php?tid=13"); //崇德缘起
        urls.add("http://www.cdtde.com/m/list.php?tid=14"); //馆长介绍

        webUrl = new MutableLiveData<>(urls.get(index));
    }

    public void setIndex(int index) {
        this.index = index;
        webUrl.setValue(urls.get(index));
    }
    public int getIndex() {
        return index;
    }

    public MutableLiveData<String> getWebUrl() {
        return webUrl;
    }
}