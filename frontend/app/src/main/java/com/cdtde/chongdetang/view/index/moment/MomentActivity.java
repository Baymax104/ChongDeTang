package com.cdtde.chongdetang.view.index.moment;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.MomentsAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityMomentBinding;
import com.cdtde.chongdetang.entity.Moment;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.viewModel.index.IndexRequester;

import java.util.ArrayList;
import java.util.List;

public class MomentActivity extends BaseActivity<ActivityMomentBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    @InjectScope(Scopes.APPLICATION)
    private IndexRequester requester;

    @InjectScope(Scopes.APPLICATION)
    private MomentDetailActivity.Messenger messenger;

    public static class States extends StateHolder {
        public final State<List<Moment>> moments = new State<>(new ArrayList<>());
    }

    public class ListHandler extends ListHandlerFactory {
        public final OnItemClickListener<Moment> onItemClick = (data, view) -> {
            messenger.showEvent.send(data);
            Starter.actionStart(MomentActivity.this, MomentDetailActivity.class);
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig()
                    .add(BR.itemClick, onItemClick);
        }
    }

    @Override
    protected ViewConfig configBinding() {
        MomentsAdapter adapter = new MomentsAdapter();
        adapter.setFactory(new ListHandler());
        return new ViewConfig(R.layout.activity_moment)
                .add(BR.state, states)
                .add(BR.adapter, adapter);
    }

    @Override
    protected void initUIComponent(@NonNull ActivityMomentBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requester.updateAllMoment(states.moments::setValue, ToastUtils::showShort);
    }


}