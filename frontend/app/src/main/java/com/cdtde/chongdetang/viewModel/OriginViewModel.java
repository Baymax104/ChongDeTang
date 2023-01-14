package com.cdtde.chongdetang.viewModel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Collection;

import java.util.ArrayList;
import java.util.List;

public class OriginViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private MutableLiveData<List<String>> webUrlList;

    public OriginViewModel(){
        super();
        List<String> urls = new ArrayList<>();
        urls.add("http://www.cdtde.com/m/list.php?tid=12");     //走进崇德堂
        urls.add("http://www.cdtde.com/m/list.php?tid=13"); //崇德缘起
        urls.add("http://www.cdtde.com/m/list.php?tid=14"); //馆长介绍
        webUrlList = new MutableLiveData<>(urls);

    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }
    public int getIndex() { return mIndex.getValue(); }

    public String getUrl(){
        int page = mIndex.getValue();
        if (page>=1 && page<=3) {
            return webUrlList.getValue().get(page - 1);
        }
        else
            return null;
    }

}