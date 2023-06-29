package com.cdtde.chongdetang.view.exhibit;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import com.cdtde.chongdetang.entity.UserCollect;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.view.my.login.LoginActivity;
import com.cdtde.chongdetang.view.shop.ItemCollectDialog;
import com.cdtde.chongdetang.requester.exhibit.ExhibitRequester;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 22:57
 * @Version 1
 */
public class ExhibitListFragment extends BaseFragment<FragmentExhibitListBinding> {

    @InjectScope(Scopes.APPLICATION)
    private ExhibitRequester requester;

    @InjectScope(Scopes.APPLICATION)
    private ItemCollectDialog.Messenger messenger;

    @InjectScope(Scopes.FRAGMENT)
    private States states;

    private ItemCollectDialog collectDialog;

    @InjectScope(Scopes.APPLICATION)
    private CollectionActivity.Messenger collectionMessenger;

    public static class States extends StateHolder {
        public final State<List<Collection>> collections = new State<>(new ArrayList<>());
        public int page = 0;
        public String key = "";
        public String type = "";
    }


    public class ListHandler extends ListHandlerFactory {

        public final OnItemClickListener<Collection> allClick = (data, view) -> {
            collectionMessenger.showEvent.send(data);
            Starter.actionStart(activity, CollectionActivity.class);
        };

        public final OnItemClickListener<Collection> more = (data, view) -> {
            collectDialog = DialogUtil.createAttachDialog(activity, ItemCollectDialog.class, view);
            collectDialog.show();
            messenger.clickEvent.send(new UserCollect(data), states.key);
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
            states.key = "ExhibitList" + states.page;
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

        messenger.collectEvent.observeSend(getViewLifecycleOwner(), states.key,
                (value, key) -> {
                    if (UserStore.isLogin()) {
                        requester.addUserCollection(
                                value.getCollection(),
                                collection ->
                                        messenger.collectEvent.reply("collection", states.key),
                                ToastUtils::showShort
                        );
                    } else {
                        collectDialog.dismissWith(() ->
                                Starter.actionStart(activity, LoginActivity.class)
                        );
                    }
                });

        // 由于ItemCollectDialog发送消息是全局发送，因此每个页面需要判断当前操作的页面是否是自己
        // 当前页面的ViewModel记录页面内的子页面页数状态，MainViewModel记录四个主页面的页数状态
//        LiveEventBus.get("ItemCollectDialog-collect", UserCollect.class)
//                .observe(this, userCollect -> {
//                    Integer mainPage = mainViewModel.getPage().getValue();
//                    if (mainPage != null && mainPage == 1 && states.page == vm.getCurrentPage()) {
//                        if (vm.isLogin()) {
//                            vm.addUserCollection(userCollect);
//                        } else {
//                            collectDialog.dismissWith(() ->
//                                    Starter.actionStart(activity, LoginActivity.class)
//                            );
//                        }
//                    }
//                });

        messenger.cancelEvent.observeSend(getViewLifecycleOwner(), states.key,
                (value, key) ->
                        requester.removeUserCollection(
                                value.getCollection(),
                                collection -> messenger.cancelEvent.reply("collection", states.key),
                                ToastUtils::showShort));


//        LiveEventBus.get("ItemCollectDialog-cancelCollect", UserCollect.class)
//                .observe(this, userCollect -> {
//                    Integer mainPage = mainViewModel.getPage().getValue();
//                    if (mainPage != null && mainPage == 1 && states.page == vm.getCurrentPage()) {
//                        vm.removeUserProduct(userCollect);
//                    }
//                });

//        LiveEventBus.get("CollectionRepository-requestAddUserCollect", WebException.class)
//                .observe(this, e -> {
//                    if (e.isSuccess()) {
//                        messenger.collectEvent.reply("collection", states.key);
////                        LiveEventBus.get("ItemCollectDialog-refreshAddCollect", Boolean.class).post(true);
//                    } else {
//                        ToastUtils.showShort(e.getMessage());
//                    }
//                });
//        LiveEventBus.get("CollectionRepository-requestRemoveUserCollect", WebException.class)
//                .observe(this, e -> {
//                    if (e.isSuccess()) {
//                        messenger.cancelEvent.reply("collection", states.key);
////                        LiveEventBus.get("ItemCollectDialog-refreshRemoveCollect", Boolean.class).post(true);
//                    } else {
//                        ToastUtils.showShort(e.getMessage());
//                    }
//                });

        requester.updateAllCollectionByType(states.type, states.collections::setValue, ToastUtils::showShort);

    }

    public static ExhibitListFragment newInstance(int page) {
        ExhibitListFragment fragment = new ExhibitListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page", page);
        fragment.setArguments(bundle);
        return fragment;
    }

}
