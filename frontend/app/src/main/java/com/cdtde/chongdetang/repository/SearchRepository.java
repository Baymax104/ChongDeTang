package com.cdtde.chongdetang.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/13 1:33
 * @Version 1
 */
public class SearchRepository {
    private static SearchRepository repository;

    private List<String> histories;

    private List<String> collections;

    private List<String> products;

    private SearchRepository() {
        histories = new ArrayList<>();
        collections = new ArrayList<>();
        products = new ArrayList<>();

        collections.add("王琦书法作品 明德惟馨");
        collections.add("贾若愚书法作品 俭以养德");
        collections.add("焦亮篆刻作品 德尊望重");
        collections.add("功德匾 祖德流芳");
        collections.add("牌坊匾 旌德");
        collections.add("功德匾 德重桑梓");

        products.add("书签护身符");
        products.add("德文化书简装签套装");
        products.add("德福-厚德载福条屏");
        products.add("堂主作品（舍念清净）");
        products.add("丝网印帆布包");
        products.add("德福门神");
    }

    public static SearchRepository getInstance() {
        if (repository == null) {
            repository = new SearchRepository();
        }
        return repository;
    }

    public List<String> getHistories() {
        return histories;
    }

    public List<String> getCollections() {
        return collections;
    }

    public List<String> getProducts() {
        return products;
    }

}
