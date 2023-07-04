package com.cdtde.chongdetang.view.index.appoint;

import android.view.View.OnClickListener;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.databinding.ActivityAppointBinding;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.view.my.login.LoginActivity;

public class AppointActivity extends BaseActivity<ActivityAppointBinding> {

    public class Handler {
        public final OnClickListener appointClick = v -> {
            if (UserStore.isLogin()) {
                DialogUtil.create(activity, AppointDialog.class).show();
            } else {
                Starter.actionStart(activity, LoginActivity.class);
            }
        };
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_appoint)
                .add(BR.handler, new Handler());
    }

    @Override
    protected void initUIComponent(@NonNull ActivityAppointBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar);
        // 两张大图在布局初始化时加载会非常耗时，导致Activity无法立即打开
        // 在Activity初始化时加载不会影响Activity的打开
        Glide.with(this).load(R.drawable.contact_banner).into(binding.banner);
        Glide.with(this).load(R.drawable.appoint_traffic).into(binding.traffic);
    }

}