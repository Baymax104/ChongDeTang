package com.cdtde.chongdetang.entity;

import android.net.Uri;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/14 17:34
 * @Version 1
 */
public class News implements Serializable {
    private String title;
    private Date publishDate;
    private String preview;
    private String image;
    private String url;
}
