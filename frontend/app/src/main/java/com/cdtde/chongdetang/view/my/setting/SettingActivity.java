package com.cdtde.chongdetang.view.my.setting;

import android.os.Bundle;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.databinding.ActivitySettingBinding;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.view.my.login.LoginActivity;
import com.cdtde.chongdetang.view.my.login.LogoutDialog;
import com.cdtde.chongdetang.view.my.setting.userInfo.UserInfoActivity;
import com.cdtde.chongdetang.view.my.setting.userPassword.UserPasswordActivity;
import com.cdtde.chongdetang.view.my.setting.userPhone.UserPhoneActivity;

public class SettingActivity extends BaseActivity<ActivitySettingBinding> {

    @InjectScope(Scopes.APPLICATION)
    private LogoutDialog.Messenger messenger;

    public class Handler {
        public OnClickListener entry = v -> {
            if (UserStore.isLogin()) {
                if (v.getId() == R.id.data_entry) {
                    Starter.actionStart(activity, UserInfoActivity.class);
                } else if (v.getId() == R.id.password_entry) {
                    Starter.actionStart(activity, UserPasswordActivity.class);
                } else if (v.getId() == R.id.phone_entry) {
                    Starter.actionStart(activity, UserPhoneActivity.class);
                } else if (v.getId() == R.id.logout) {
                    DialogUtil.create(activity, LogoutDialog.class).show();
                }
            } else {
                Starter.actionStart(activity, LoginActivity.class);
            }
        };

        public final OnClickListener versionClick = v ->
                ToastUtils.showShort("当前已经是最高版本");

        public final OnClickListener copyrightClick = v ->
                ToastUtils.showShort("版权声明");
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_setting)
                .add(BR.handler, new Handler());
    }

    @Override
    protected void initUIComponent(@NonNull ActivitySettingBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messenger.logout.observeSend(this, value -> finish());
    }

}