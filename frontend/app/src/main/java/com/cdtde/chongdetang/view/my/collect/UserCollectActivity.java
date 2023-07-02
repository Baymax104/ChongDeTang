package com.cdtde.chongdetang.view.my.collect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;

import com.angcyo.tablayout.delegate2.ViewPager2Delegate;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.FragmentAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityUserCollectBinding;
import com.cdtde.chongdetang.utils.WindowUtil;

import java.util.Arrays;
import java.util.List;

public class UserCollectActivity extends BaseActivity<ActivityUserCollectBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    public static class States extends StateHolder {
        public final List<Fragment> fragments = Arrays.asList(
                new UserCollectionFragment(),
                new UserProductFragment()
        );
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_user_collect)
                .add(BR.adapter, new FragmentAdapter(this))
                .add(BR.state, states);
    }

    @Override
    protected void initUIComponent(@NonNull ActivityUserCollectBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
        ViewPager2Delegate.Companion.install(binding.viewPager, binding.tabs, true);
    }

}