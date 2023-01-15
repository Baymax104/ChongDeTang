package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.entity.Collection;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/12 23:22
 * @Version 1
 */
public class ExhibitRepository {

    private UserRepository userRepository;
    private static ExhibitRepository repository;

    private List<Collection> collectionPage1;

    private List<Collection> collectionPage2;

    private List<Collection> collectionPage3;

    public ExhibitRepository() {
        userRepository = UserRepository.getInstance();

        collectionPage1 = new ArrayList<>();
        collectionPage2 = new ArrayList<>();
        collectionPage3 = new ArrayList<>();
        generateTest();
    }

    public static ExhibitRepository getInstance() {
        if (repository == null) {
            repository = new ExhibitRepository();
        }
        return repository;
    }

    public List<Collection> getCollectionPage1() {
        return collectionPage1;
    }

    public List<Collection> getCollectionPage2() {
        return collectionPage2;
    }

    public List<Collection> getCollectionPage3() {
        return collectionPage3;
    }

    private void generateTest() {

        for (int i = 0; i < 20; i++) {
            collectionPage1.add(new Collection());
        }

        for (int i = 0; i < 40; i++) {
            collectionPage2.add(new Collection());
        }

        for (int i = 0; i < 10; i++) {
            collectionPage3.add(new Collection());
        }
    }
}
