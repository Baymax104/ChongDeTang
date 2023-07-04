package com.cdtde.chongdetang.view.index.search;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.angcyo.tablayout.delegate2.ViewPager2Delegate;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.FragmentAdapter;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentSearchListBinding;

import java.util.Arrays;
import java.util.List;


public class SearchListFragment extends BaseFragment<FragmentSearchListBinding> {

    @InjectScope(Scopes.FRAGMENT)
    private States states;


    public static class States extends StateHolder {
        public final List<Fragment> fragments = Arrays.asList(
                new SearchCollectionFragment(),
                new SearchProductFragment()
        );
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.fragment_search_list)
                .add(BR.state, states)
                .add(BR.adapter, new FragmentAdapter(activity));
    }

    @Override
    protected void initUIComponent(@NonNull FragmentSearchListBinding binding) {
        ViewPager2Delegate.Companion.install(binding.viewPager, binding.tabs, true);
        binding.viewPager.setOffscreenPageLimit(2);
    }
}
