package com.cdtde.chongdetang.view.my.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.databinding.ActivityUserOrderBinding;

public class UserOrderActivity extends BaseActivity<ActivityUserOrderBinding> {

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_user_order);
    }

    @Override
    protected void initUIComponent(@NonNull ActivityUserOrderBinding binding) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);
    }
}