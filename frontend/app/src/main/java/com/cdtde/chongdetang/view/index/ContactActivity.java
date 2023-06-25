package com.cdtde.chongdetang.view.index;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.databinding.ActivityContactBinding;
import com.cdtde.chongdetang.utils.WindowUtil;

public class ContactActivity extends BaseActivity<ActivityContactBinding> {

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_contact);
    }

    @Override
    protected void initUIComponent(@NonNull ActivityContactBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }
}