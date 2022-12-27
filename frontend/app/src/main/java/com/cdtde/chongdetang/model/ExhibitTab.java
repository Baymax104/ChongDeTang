package com.cdtde.chongdetang.model;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 22:34
 * @Version 1
 */
public class ExhibitTab implements CustomTabEntity {

    private String title;

    public ExhibitTab(String title) {
        this.title = title;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return 0;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }
}
