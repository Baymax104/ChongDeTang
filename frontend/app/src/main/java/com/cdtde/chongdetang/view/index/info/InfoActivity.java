package com.cdtde.chongdetang.view.index.info;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.InfosAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityInfoBinding;
import com.cdtde.chongdetang.entity.Info;
import com.cdtde.chongdetang.requester.IndexRequester;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.WindowUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * “行业资讯”列表页
 */
public class InfoActivity extends BaseActivity<ActivityInfoBinding> {

    @InjectScope(Scopes.APPLICATION)
    private IndexRequester requester;

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    @InjectScope(Scopes.APPLICATION)
    private InfoDetailActivity.Messenger messenger;


    public static class States extends StateHolder {
        /**
         * “行业资讯”内容对象列表
         */
        public final State<List<Info>> infos = new State<>(new ArrayList<>());
    }

    public class ListHandler extends ListHandlerFactory {

        public final OnItemClickListener<Info> onItemClickListener = (data, view) -> {
            messenger.showEvent.send(data);
            Starter.actionStart(activity, InfoDetailActivity.class);
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig()
                    .add(BR.itemClick, onItemClickListener);
        }
    }

    @Override
    protected ViewConfig configBinding() {
        InfosAdapter adapter = new InfosAdapter();
        adapter.setFactory(new ListHandler());
        return new ViewConfig(R.layout.activity_info)
                .add(BR.state, states)
                .add(BR.adapter, adapter);
    }

    @Override
    protected void initUIComponent(@NonNull ActivityInfoBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requester.updateAllInfo(states.infos::setValue, ToastUtils::showShort);
    }

}