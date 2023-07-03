package com.cdtde.chongdetang.view.exhibit;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;

import com.angcyo.tablayout.delegate2.ViewPager2Delegate;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.FragmentAdapter;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentExhibitBinding;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.view.index.search.SearchActivity;

import java.util.Arrays;
import java.util.List;

public class ExhibitFragment extends BaseFragment<FragmentExhibitBinding> {

    @InjectScope(Scopes.FRAGMENT)
    private States states;

    public static class States extends StateHolder {

        public final State<Integer> page = new State<>(0);

        public final List<Fragment> fragments = Arrays.asList(
                ExhibitListFragment.newInstance(0),
                ExhibitListFragment.newInstance(1),
                ExhibitListFragment.newInstance(2)
        );

    }

    public class Handler {
        public void setPage(int page) {
            states.page.setValue(page);
        }

        public final OnMenuItemClickListener onMenuItemClick = item -> {
            int id = item.getItemId();
            if (id == R.id.index_search) {
                Starter.actionStart(activity, SearchActivity.class);
            }
            return true;
        };

    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.fragment_exhibit)
                .add(BR.state, states)
                .add(BR.handler, new Handler())
                .add(BR.tabAdapter, new FragmentAdapter(activity));
    }

    @Override
    protected void initUIComponent(@NonNull FragmentExhibitBinding binding) {
        binding.toolbar.inflateMenu(R.menu.index_toolbar);
        binding.viewPager.setOffscreenPageLimit(3);
        ViewPager2Delegate.Companion.install(binding.viewPager, binding.tabs, true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

}
