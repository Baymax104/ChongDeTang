package com.cdtde.chongdetang.entity;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/25 11:38
 * @Version 1
 */
public class UserCollect {
    private Integer id;
    private Integer userId;
    private Collection collection;
    private Product product;

    public UserCollect() {
    }

    public UserCollect(Collection collection) {
        this.collection = collection;
    }

    public UserCollect(Product product) {
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isCollected() {
        return (product != null && product.isUserCollect()) ||
                (collection != null && collection.isUserCollect());
    }
}
