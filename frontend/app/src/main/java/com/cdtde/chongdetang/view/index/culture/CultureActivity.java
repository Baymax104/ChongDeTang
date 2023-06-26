package com.cdtde.chongdetang.view.index.culture;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.angcyo.tablayout.delegate2.ViewPager2Delegate;
import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.FragmentAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityCultureBinding;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.viewModel.index.CultureRequester;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.Arrays;
import java.util.List;


public class CultureActivity extends BaseActivity<ActivityCultureBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    public static class States extends StateHolder {
        public final List<Fragment> fragments = Arrays.asList(
                CultureListFragment.newInstance(0),
                CultureListFragment.newInstance(1),
                CultureListFragment.newInstance(2),
                CultureListFragment.newInstance(3)
        );
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_culture)
                .add(BR.state, states)
                .add(BR.fragmentAdapter, new FragmentAdapter(this));
    }

    @Override
    protected void initUIComponent(@NonNull ActivityCultureBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
        binding.viewPager.setOffscreenPageLimit(2);
        ViewPager2Delegate.Companion.install(binding.viewPager, binding.tabs, true);
    }

}