package com.cdtde.chongdetang.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/25 22:36
 * @Version 1
 */
public class SearchViewModel extends ViewModel {
    private MutableLiveData<List<String>> historyTags;
    private List<String> collectionTags;
    private List<String> productTags;

    public SearchViewModel() {
        historyTags = new MutableLiveData<>(new ArrayList<>());
        collectionTags = new ArrayList<>();
        productTags = new ArrayList<>();

        collectionTags.add("ZJpkDd");
        collectionTags.add("hWDDTi");
        collectionTags.add("XHqfzcU");
        collectionTags.add("QhXiN");
        collectionTags.add("LZXWQK");


        productTags.add("ZJpkDd");
        productTags.add("hWDDTi");
        productTags.add("XHqfzcU");
        productTags.add("QhXiN");
        productTags.add("LZXWQK");
    }

    public MutableLiveData<List<String>> getHistoryTags() {
        return historyTags;
    }

    public List<String> getCollectionTags() {
        return collectionTags;
    }

    public List<String> getProductTags() {
        return productTags;
    }

    public void addTag(String tag) {
        List<String> value = historyTags.getValue();
        if (value != null) {
            value.add(tag);
        }
        historyTags.setValue(value);
    }

    public void clearTag() {
        List<String> value = historyTags.getValue();
        if (value != null) {
            value.clear();
        }
    }

    public boolean isHistoryEmpty() {
        return Objects.requireNonNull(historyTags.getValue()).isEmpty();
    }

    public boolean isHistoryExist(String s) {
        return Objects.requireNonNull(historyTags.getValue()).contains(s);
    }
}
