package com.cdtde.chongdetang.model;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 16:03
 * @Version 1
 */
public class Collection {
    private String name;
    private Uri imgUri;
    private String description;

    public Collection() {
    }

    public Collection(String name, Uri imgUri, String description) {
        this.name = name;
        this.imgUri = imgUri;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
