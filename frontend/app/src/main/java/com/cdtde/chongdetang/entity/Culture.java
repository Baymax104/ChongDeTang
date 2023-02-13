package com.cdtde.chongdetang.entity;

public class Culture {
    private String type;
    private String title;    //实际标题内容
    private String disTitle; //显示在列表item中的内容
    private String date;
    private String url;

    public Culture() {
        title="山东青岛一收藏家收藏千余块汉画像砖 无处存放";
        date="2022-12-12";
    }
    public Culture(String title,String url) {
        this.title=title;
        this.url=url;
        date="2022-12-12";
    }


    public String getDisTitle() {
        int endPos=15;
        if (title.length()>endPos){
            disTitle=title.substring(0,endPos)+"...";
        }
        else disTitle=title;
        return disTitle;
    }

    public void setDisTitle(String disTitle) {
        this.disTitle = disTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
