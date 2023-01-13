package com.cdtde.chongdetang.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.repository.SearchRepository;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/25 22:36
 * @Version 1
 */
public class SearchViewModel extends ViewModel {

    private SearchRepository repository;
    private MutableLiveData<List<String>> historyTags;
    private MutableLiveData<List<String>> collectionTags;
    private MutableLiveData<List<String>> productTags;

    public SearchViewModel() {
        repository = SearchRepository.getInstance();
        historyTags = new MutableLiveData<>(repository.getHistories());
        collectionTags = new MutableLiveData<>(repository.getCollections());
        productTags = new MutableLiveData<>(repository.getProducts());
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
        ToastUtils.showShort("搜索");
    }

    public void clearTag() {
        List<String> value = historyTags.getValue();
        if (value != null) {
            value.clear();
        }
        historyTags.setValue(value);
        // 清除历史缓存
    }


}
