package com.cdtde.chongdetang.view.my.setting.userInfo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.DialogBinder;
import com.cdtde.chongdetang.base.vm.DialogScope;
import com.lxj.xpopup.core.BottomPopupView;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/9 18:17
 * @Version 1
 */
public class PhotoDialog extends BottomPopupView {

    private UserInfoActivity.Messenger messenger =
            DialogScope.getFromApplication(UserInfoActivity.Messenger.class);

    public PhotoDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_user_icon;
    }

    public class Handler {
        public OnClickListener takePhoto = v -> {
            messenger.photoAction.send(1);
            dismiss();
        };

        public OnClickListener openAlbum = v -> {
            messenger.photoAction.send(2);
            dismiss();
        };

        public OnClickListener cancel = v -> dismiss();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        DialogBinder.bind(this, new BindingConfig().add(BR.handler, new Handler()));
    }
}
