package com.cdtde.chongdetang.view.index.origin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.angcyo.tablayout.delegate2.ViewPager2Delegate;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.FragmentAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityOriginBinding;
import com.cdtde.chongdetang.utils.WindowUtil;

import java.util.Arrays;
import java.util.List;


public class OriginActivity extends BaseActivity<ActivityOriginBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    public static class States extends StateHolder {
        public final List<Fragment> fragments = Arrays.asList(
                OriginFragment.newInstance(0),
                OriginFragment.newInstance(1),
                OriginFragment.newInstance(2)
        );
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_origin)
                .add(BR.state, states)
                .add(BR.adapter, new FragmentAdapter(this));
    }

    @Override
    protected void initUIComponent(@NonNull ActivityOriginBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
        binding.viewPager.setOffscreenPageLimit(2);
        ViewPager2Delegate.Companion.install(binding.viewPager, binding.tabs, true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}