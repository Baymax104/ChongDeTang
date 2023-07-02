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
import com.cdtde.chongdetang.requester.UserCollectRequester;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.view.exhibit.CollectionActivity;

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

    @InjectScope(Scopes.FRAGMENT)
    private States states;

    @InjectScope(Scopes.APPLICATION)
    private CollectionActivity.Messenger collectionMessenger;

    public static class States extends StateHolder {
        public final State<List<Collection>> collections = new State<>(new ArrayList<>());
    }

    public class ListHandler extends ListHandlerFactory {

        public OnItemClickListener<Collection> itemClick = (data, view) -> {
            Starter.actionStart(activity, CollectionActivity.class);
            collectionMessenger.showEvent.send(data);
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig()
                    .add(BR.allClick, itemClick);
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
        requester.updateUserCollection(states.collections::setValue, ToastUtils::showShort);

        // TODO 批量编辑
    }
}
