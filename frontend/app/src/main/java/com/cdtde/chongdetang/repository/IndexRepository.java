package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.entity.Moment;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/13 1:33
 * @Version 1
 */
public class IndexRepository {
    private static IndexRepository repository;

    private List<Moment> moments;
    private IndexRepository() {
    }

    public static IndexRepository getInstance() {
        if (repository == null) {
            repository = new IndexRepository();
        }
        return repository;
    }
}
