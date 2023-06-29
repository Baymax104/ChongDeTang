package com.cdtde.chongdetang.view.my.collect;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.UserCollectionAdapter;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentUserCollectionBinding;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.UserCollect;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.view.exhibit.CollectionActivity;
import com.cdtde.chongdetang.view.shop.ItemCollectDialog;
import com.cdtde.chongdetang.requester.my.UserCollectRequester;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/24 20:35
 * @Version 1
 */
public class UserCollectionFragment extends BaseFragment<FragmentUserCollectionBinding> {


    @InjectScope(Scopes.ACTIVITY)
    private UserCollectRequester requester;

    private ItemCollectDialog dialog;

    @InjectScope(Scopes.FRAGMENT)
    private States states;

    @InjectScope(Scopes.APPLICATION)
    private ItemCollectDialog.Messenger messenger;

    @InjectScope(Scopes.APPLICATION)
    private CollectionActivity.Messenger collectionMessenger;

    public static class States extends StateHolder {
        public final State<List<Collection>> collections = new State<>(new ArrayList<>());
    }

    public class ListHandler extends ListHandlerFactory {

        public OnItemClickListener<Collection> itemClick = (data, view) -> {
            collectionMessenger.showEvent.send(data);
            Starter.actionStart(activity, CollectionActivity.class);
        };

        public OnItemClickListener<Collection> moreClick = (data, view) -> {
            dialog = DialogUtil.createAttachDialog(activity, ItemCollectDialog.class, view);
            dialog.show();
            messenger.clickEvent.send(new UserCollect(data), "UserCollectionFragment");
//            LiveEventBus.get("ItemCollectDialog-show", UserCollect.class)
//                    .post(new UserCollect(data));
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig()
                    .add(BR.allClick, itemClick)
                    .add(BR.more, moreClick);
        }
    }

    @Override
    protected ViewConfig configBinding() {
        UserCollectionAdapter adapter = new UserCollectionAdapter();
        adapter.setFactory(new ListHandler());
        return new ViewConfig(R.layout.fragment_user_collection)
                .add(BR.state, states)
                .add(BR.adapter, adapter);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        LiveEventBus.get("UserRepository-requestUserCollection", WebException.class)
//                .observe(this, exception -> {
//                    if (exception.isSuccess()) {
//                        states.collections.setValue(requester.refreshUserCollection());
//                    } else {
//                        ToastUtils.showShort(exception.getMessage());
//                    }
//                });

        messenger.cancelEvent.observeSend(getViewLifecycleOwner(), "UserCollectionFragment",
                (value, key) -> requester.removeUserCollection(
                        value.getCollection(),
                        states.collections::setValue,
                        ToastUtils::showShort
                )
        );


//        LiveEventBus.get("UserRepository-requestRemoveUserCollect", WebException.class)
//                .observe(this, e -> {
//                    if (requester.getCurrentPage() == 1) {
//                        dialog.smartDismiss();
//                        if (e.isSuccess()) {
//                            requester.updateUserCollection();
//                        } else {
//                            ToastUtils.showShort(e.getMessage());
//                        }
//                    }
//                });

        requester.updateUserCollection(states.collections::setValue, ToastUtils::showShort);
    }
}
