package com.cdtde.chongdetang.view.index.culture;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.CultureAdapter;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentCultureListBinding;
import com.cdtde.chongdetang.entity.Culture;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.requester.CultureRequester;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class CultureListFragment extends BaseFragment<FragmentCultureListBinding> {

    @InjectScope(Scopes.APPLICATION)
    private CultureRequester requester;
    @InjectScope(Scopes.FRAGMENT)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private CultureDetailActivity.Messenger messenger;

    public static class States extends StateHolder {
        public final State<List<Culture>> cultures = new State<>(new ArrayList<>());
        public int page = 0;
        public String type = "";
    }

    public class ListHandler extends ListHandlerFactory {

        public final OnItemClickListener<Culture> itemClick = (data, view) -> {
            messenger.showEvent.send(data);
            Starter.actionStart(activity, CultureDetailActivity.class);
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig()
                    .add(BR.itemClick, itemClick);
        }
    }

    @Override
    protected ViewConfig configBinding() {
        CultureAdapter adapter = new CultureAdapter();
        adapter.setFactory(new ListHandler());
        return new ViewConfig(R.layout.fragment_culture_list)
                .add(BR.state, states)
                .add(BR.adapter, adapter);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        if (getArguments() != null) {
            states.page = getArguments().getInt("page");
            switch (states.page) {
                case 0:
                    states.type = "beyl";
                    break;
                case 1:
                    states.type = "shzk";
                    break;
                case 2:
                    states.type = "zzys";
                    break;
                case 3:
                    states.type = "tpdk";
                default: break;
            }
        }
        requester.updateAllCulture(states.type,
                states.cultures::setValue, ToastUtils::showShort);
    }

    public static CultureListFragment newInstance(int index) {
        CultureListFragment fragment = new CultureListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page", index);
        fragment.setArguments(bundle);
        return fragment;
    }

}