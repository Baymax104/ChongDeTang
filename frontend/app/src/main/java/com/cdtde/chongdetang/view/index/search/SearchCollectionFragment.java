package com.cdtde.chongdetang.view.index.search;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.SearchCollectionAdapter;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentSearchCollectionBinding;
import com.cdtde.chongdetang.entity.Collection;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;

/**
 * @ClassName SearchCollectionFragment
 * @Author John
 * @Date 2023/7/2 17:15
 * @Version 1.0
 */
public class SearchCollectionFragment extends BaseFragment<FragmentSearchCollectionBinding> {

    @InjectScope(Scopes.FRAGMENT)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;

    public static class States extends StateHolder {
        public final State<List<Collection>> collections = new State<>(new ArrayList<>());
    }

    public static class Messenger extends MessageHolder {
        public final Event<List<Collection>, Unit> showEvent = new Event<>();
    }

    @Override
    protected ViewConfig configBinding() {
        SearchCollectionAdapter adapter = new SearchCollectionAdapter();
        return new ViewConfig(R.layout.fragment_search_collection)
                .add(BR.state, states)
                .add(BR.adapter, adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messenger.showEvent.observeSend(getViewLifecycleOwner(), true,
                states.collections::setValue);
    }
}