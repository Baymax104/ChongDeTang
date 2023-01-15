package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/13 1:24
 * @Version 1
 */
public class ShopRepository {

    private UserRepository userRepository;
    private static ShopRepository repository;

    private List<Integer> bannerResource;

    private List<Product> products;

    private ShopRepository() {
        userRepository = UserRepository.getInstance();

        bannerResource = new ArrayList<>();
        bannerResource.add(R.drawable.shop_banner1);
        bannerResource.add(R.drawable.shop_banner2);
        bannerResource.add(R.drawable.shop_banner3);

        products = new ArrayList<>();
        generateTest();
    }

    public static ShopRepository getInstance() {
        if (repository == null) {
            repository = new ShopRepository();
        }
        return repository;
    }

    public List<Integer> getBannerResource() {
        return bannerResource;
    }

    public List<Product> getProducts() {
        return products;
    }

    private void generateTest() {
        for (int i = 0; i < 20; i++) {
            products.add(new Product());
        }
    }

}
