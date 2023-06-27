package com.cdtde.chongdetang.view.index;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.IndexCollectionAdapter;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory.OnItemClickListener;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentIndexBinding;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.Info;
import com.cdtde.chongdetang.entity.Moment;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.view.MainActivity;
import com.cdtde.chongdetang.view.exhibit.CollectionActivity;
import com.cdtde.chongdetang.view.index.appoint.AppointActivity;
import com.cdtde.chongdetang.view.index.couplet.CoupletActivity;
import com.cdtde.chongdetang.view.index.culture.CultureActivity;
import com.cdtde.chongdetang.view.index.info.InfoActivity;
import com.cdtde.chongdetang.view.index.info.InfoDetailActivity;
import com.cdtde.chongdetang.view.index.moment.MomentActivity;
import com.cdtde.chongdetang.view.index.moment.MomentDetailActivity;
import com.cdtde.chongdetang.view.index.origin.OriginActivity;
import com.cdtde.chongdetang.view.index.scenes.ScenesActivity;
import com.cdtde.chongdetang.viewModel.index.IndexRequester;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/21 20:52
 * @Version 1
 */
public class IndexFragment extends BaseFragment<FragmentIndexBinding> {

    @InjectScope(Scopes.APPLICATION)
    private IndexRequester requester;
    @InjectScope(Scopes.APPLICATION)
    private CollectionActivity.Messenger collectionMessenger;
    @InjectScope(Scopes.APPLICATION)
    private MainActivity.Messenger mainMessenger;
    @InjectScope(Scopes.FRAGMENT)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private MomentDetailActivity.Messenger momentDetailMessenger;
    @InjectScope(Scopes.APPLICATION)
    private InfoDetailActivity.Messenger infoDetailMessenger;

    public static class States extends StateHolder {
        public final State<List<Collection>> hotCollections = new State<>(new ArrayList<>());
        public final State<List<Moment>> moments = new State<>(new ArrayList<>());
        public final State<List<Info>> infos = new State<>(new ArrayList<>());
        public final State<Moment> showMoment = new State<>(new Moment());
        public final State<Info> showInfo = new State<>(new Info());
        public final List<Integer> banner = Arrays.asList(
                R.drawable.index_banner1,
                R.drawable.index_banner2,
                R.drawable.index_banner3
        );

    }

    public class Handler {

        public final OnMenuItemClickListener onMenuItemClick = item -> {
            int id = item.getItemId();
            if (id == R.id.index_search) {
                ToastUtils.showShort("点击");
            }
            return true;
        };

        public final OnClickListener entry = v -> {
            if (v.getId() == R.id.entry1) {
                Starter.actionStart(activity, ScenesActivity.class);
            } else if (v.getId() == R.id.entry2) {
                Starter.actionStart(activity, AppointActivity.class);
            } else if (v.getId() == R.id.entry3) {
                Starter.actionStart(activity, CoupletActivity.class);
            } else if (v.getId() == R.id.entry4) {
                Starter.actionStart(activity, CultureActivity.class);
            } else if (v.getId() == R.id.entry5) {
                Starter.actionStart(activity, OriginActivity.class);
            } else if (v.getId() == R.id.entry6) {
                Starter.actionStart(activity, ContactActivity.class);
            }
        };

        public final OnClickListener momentEntry = v ->
                Starter.actionStart(activity, MomentActivity.class);

        public final OnClickListener infoEntry = v ->
                Starter.actionStart(activity, InfoActivity.class);

        public final OnClickListener collectionEntry = v ->
                mainMessenger.requestPage.send(1);

        public final OnItemClickListener<Moment> showMoment = (data, view) -> {
            momentDetailMessenger.showEvent.send(data);
            Starter.actionStart(activity, MomentDetailActivity.class);
        };

        public final OnItemClickListener<Info> showInfo = (data, view) -> {
            infoDetailMessenger.showEvent.send(data);
            Starter.actionStart(activity, InfoDetailActivity.class);
        };
    }

    public class ListHandler extends ListHandlerFactory {
        public final OnItemClickListener<Collection> onItemClick = (data, view) -> {
            collectionMessenger.showEvent.send(data);
            Starter.actionStart(activity, CollectionActivity.class);
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig()
                    .add(BR.itemClick, onItemClick);
        }
    }

    @Override
    protected ViewConfig configBinding() {
        IndexCollectionAdapter adapter = new IndexCollectionAdapter();
        adapter.setFactory(new ListHandler());
        return new ViewConfig(R.layout.fragment_index)
                .add(BR.state, states)
                .add(BR.collectionAdapter, adapter)
                .add(BR.handler, new Handler());
    }


    @Override
    protected void initUIComponent(@NonNull FragmentIndexBinding binding) {
        binding.toolbar.inflateMenu(R.menu.index_toolbar);
        WindowUtil.initWindowPadding(binding.toolbarLayout);
        binding.banner.setIndicator(new CircleIndicator(activity));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        requester.updateHotCollection(states.hotCollections::setValue, ToastUtils::showShort);

        requester.updateAllInfo(infos -> {
            states.infos.setValue(infos);
            states.showInfo.setValue(infos.get(0));
        }, ToastUtils::showShort);

        requester.updateAllMoment(moments -> {
            states.moments.setValue(moments);
            states.showMoment.setValue(moments.get(0));
        }, ToastUtils::showShort);
    }

}
