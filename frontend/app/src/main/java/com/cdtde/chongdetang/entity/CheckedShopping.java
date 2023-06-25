package com.cdtde.chongdetang.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.cdtde.chongdetang.BR;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/21 21:38
 * @Version 1
 */
public class CheckedShopping extends BaseObservable {
    private boolean isChecked;
    private boolean editEnabled;
    private Shopping shopping;

    public CheckedShopping(Shopping shopping) {
        this(false, false, shopping);
    }

    public CheckedShopping(boolean isChecked, boolean editEnabled, Shopping shopping) {
        this.isChecked = isChecked;
        this.editEnabled = editEnabled;
        this.shopping = shopping;
    }

    @Bindable
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        notifyPropertyChanged(BR.checked);
    }

    @Bindable
    public Shopping getShopping() {
        return shopping;
    }

    public void setShopping(Shopping shopping) {
        this.shopping = shopping;
        notifyPropertyChanged(BR.shopping);
    }

    @Bindable
    public boolean isEditEnabled() {
        return editEnabled;
    }

    public void setEditEnabled(boolean editEnabled) {
        this.editEnabled = editEnabled;
        notifyPropertyChanged(BR.editEnabled);
    }
}
