package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.entity.Culture;
import com.cdtde.chongdetang.entity.Moment;
import com.cdtde.chongdetang.entity.News;
import com.cdtde.chongdetang.entity.User;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    private List<Culture> culturePage1;
    private List<Culture> culturePage2;
    private List<Culture> culturePage3;
    private List<Culture> culturePage4;

    private String cultureDetailUrl;



    private IndexRepository() {
        userRepo = UserRepository.getInstance();
        moments = new ArrayList<>();
        news = new ArrayList<>();

        culturePage1 = new ArrayList<>();
        culturePage2 = new ArrayList<>();
        culturePage3 = new ArrayList<>();
        culturePage4 = new ArrayList<>();
        generateTest();
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


    public List<Culture> getCulturePage1() {
        return culturePage1;
    }

    public List<Culture> getCulturePage2() {
        return culturePage2;
    }

    public List<Culture> getCulturePage3() {
        return culturePage3;
    }

    public List<Culture> getCulturePage4() {
        return culturePage4;
    }

    public void setCulturePage1(List<Culture> culturePage1) {
        this.culturePage1 = culturePage1;
    }

    private void generateTest() {


        for (int i = 0; i < 30; i++) {
            culturePage1.add(new Culture());
        }

        for (int i = 0; i < 30; i++) {
            culturePage2.add(new Culture());
        }

        for (int i = 0; i < 30; i++) {
            culturePage3.add(new Culture());
        }

        for (int i = 0; i < 30; i++) {
            culturePage4.add(new Culture());
        }
    }

    public String getCultureDetailUrl() {
        return cultureDetailUrl;
    }

    public void setCultureDetailUrl(String cultureDetailUrl) {
        this.cultureDetailUrl = cultureDetailUrl;
    }
}
