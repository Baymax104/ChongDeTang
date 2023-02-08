package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.entity.Moment;
import com.cdtde.chongdetang.entity.News;
import com.cdtde.chongdetang.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/13 1:33
 * @Version 1
 */
public class IndexRepository {

    private UserRepository userRepo;
    private static IndexRepository repository;

    private List<Moment> moments;
    private List<News> news;
    private IndexRepository() {
        userRepo = UserRepository.getInstance();
        moments = new ArrayList<>();
        news = new ArrayList<>();
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
}
