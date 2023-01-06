package com.cdtde.chongdetang.viewModel;

import androidx.lifecycle.ViewModel;

public class PersonInfoViewModel extends ViewModel {
    private String new_userIcon;
    private String new_userName;

    public PersonInfoViewModel() {
        new_userIcon="none";
        new_userName="none";
    }

    public String getNew_userIcon() {
        return new_userIcon;
    }

    public void setNew_userIcon(String new_userIcon) {
        this.new_userIcon = new_userIcon;
    }

    public String getNew_userName() {
        return new_userName;
    }

    public void setNew_userName(String new_userName) {
        this.new_userName = new_userName;
    }
}
