package com.cdtde.chongdetang.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/25 22:36
 * @Version 1
 */
public class SearchViewModel extends ViewModel {
    private MutableLiveData<List<String>> historyTags;
    private MutableLiveData<List<String>> collectionTags;
    private MutableLiveData<List<String>> productTags;

    public SearchViewModel() {
        historyTags = new MutableLiveData<>(new ArrayList<>());
        collectionTags = new MutableLiveData<>(new ArrayList<>());
        productTags = new MutableLiveData<>(new ArrayList<>());
        generateTest();
    }

    public MutableLiveData<List<String>> getHistoryTags() {
        return historyTags;
    }

    public MutableLiveData<List<String>> getCollectionTags() {
        return collectionTags;
    }

    public MutableLiveData<List<String>> getProductTags() {
        return productTags;
    }

    public void search(String content) {
        List<String> value = historyTags.getValue();
        if (value != null && !content.equals("") && !value.contains(content)) {
            value.add(content);
            historyTags.setValue(value);
        }
        // 搜索
    }

    public void clearTag() {
        List<String> value = historyTags.getValue();
        if (value != null) {
            value.clear();
        }
        historyTags.setValue(value);
    }


    private void generateTest() {
        List<String> data1 = new ArrayList<>();
        data1.add("ZJpkDd");
        data1.add("hWDDTi");
        data1.add("XHqfzcU");
        data1.add("QhXiN");
        data1.add("LZXWQK");
        collectionTags.setValue(data1);

        List<String> data2 = new ArrayList<>();
        data2.add("ZJpkDd");
        data2.add("hWDDTi");
        data2.add("XHqfzcU");
        data2.add("QhXiN");
        data2.add("LZXWQK");
        productTags.setValue(data2);
    }
}
