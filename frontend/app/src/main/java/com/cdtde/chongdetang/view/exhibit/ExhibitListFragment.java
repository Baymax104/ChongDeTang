package com.cdtde.chongdetang.view.exhibit;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.ExhibitCollectionAdapter;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentExhibitListBinding;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.requester.ExhibitRequester;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Starter;

import java.util.ArrayList;
import java.util.List;

/**
 * 藏品列表页
 */
public class ExhibitListFragment extends BaseFragment<FragmentExhibitListBinding> {

    @InjectScope(Scopes.APPLICATION)
    private ExhibitRequester requester;

    @InjectScope(Scopes.APPLICATION)
    private UserCollectionDialog.Messenger messenger;

    @InjectScope(Scopes.FRAGMENT)
    private States states;

    @InjectScope(Scopes.APPLICATION)
    private CollectionActivity.Messenger collectionMessenger;

    public static class States extends StateHolder {
        /**
         * 当前类别藏品列表状态
         */
        public final State<List<Collection>> collections = new State<>(new ArrayList<>());
        /**
         * 当前列表页所在页数
         */
        public int page = 0;
        /**
         * 当前列表页展示的藏品类别
         */
        public String type = "";
    }


    public class ListHandler extends ListHandlerFactory {

        public final OnItemClickListener<Collection> allClick = (data, view) -> {
            collectionMessenger.showEvent.send(data);
            Starter.actionStart(activity, CollectionActivity.class);
        };

        public final OnItemClickListener<Collection> more = (data, view) -> {
            DialogUtil.createAttachDialog(activity, UserCollectionDialog.class, view).show();
            messenger.clickEvent.send(data);
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig()
                    .add(BR.allClick, allClick)
                    .add(BR.more, more);
        }
    }

    @Override
    protected ViewConfig configBinding() {
        ExhibitCollectionAdapter adapter = new ExhibitCollectionAdapter();
        adapter.setFactory(new ListHandler());

        return new ViewConfig(R.layout.fragment_exhibit_list)
                .add(BR.state, states)
                .add(BR.adapter, adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            states.page = getArguments().getInt("page");
            switch (states.page) {
                case 0:
                    states.type = "sf";
                    break;
                case 1:
                    states.type = "zk";
                    break;
                case 2:
                    states.type = "pb";
                    break;
                default:
                    break;
            }
        }

        UserStore.getUserLoginEvent().observeSend(getViewLifecycleOwner(), true, value ->
                requester.updateAllCollectionByType(states.type,
                states.collections::setValue, ToastUtils::showShort));

        requester.updateAllCollectionByType(states.type, states.collections::setValue, ToastUtils::showShort);

    }

    public static ExhibitListFragment newInstance(int page) {
        ExhibitListFragment fragment = new ExhibitListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page", page);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initUIComponent(@NonNull FragmentExhibitListBinding binding) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        binding.tabList.setLayoutManager(layoutManager);
        binding.tabList.setItemAnimator(null);
        binding.tabList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments();
            }
        });
    }
}
