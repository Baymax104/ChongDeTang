package com.cdtde.chongdetang.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/14 0:06
 * @Version 1
 */
public class Moment implements Serializable {
    private String title;
    private Date publishDate;
    private String preview;
    private String image;
    private String url;

    public Moment() {
    }

    public Moment(String title, Date publishDate, String preview, String image, String url) {
        this.title = title;
        this.publishDate = publishDate;
        this.preview = preview;
        this.image = image;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
