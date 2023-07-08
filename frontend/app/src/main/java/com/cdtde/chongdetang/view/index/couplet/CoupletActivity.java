package com.cdtde.chongdetang.view.index.couplet;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.CoupletAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityCoupletBinding;
import com.cdtde.chongdetang.entity.Couplet;
import com.cdtde.chongdetang.requester.CoupletRequester;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.WindowUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ”每日一联“列表页
 */
public class CoupletActivity extends BaseActivity<ActivityCoupletBinding> {

    @InjectScope(Scopes.APPLICATION)
    private CoupletRequester requester;
    @InjectScope(Scopes.ACTIVITY)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private CoupletDetailActivity.Messenger messenger;

    public static class States extends StateHolder{
        /**
         * “每日一联”内容对象列表
         */
        public final State<List<Couplet>> couplets = new State<>(new ArrayList<>());

    }
    public class ListHandler extends BaseAdapter.ListHandlerFactory{
        public final OnItemClickListener<Couplet> onItemClickListener = (data, view) -> {
            messenger.showEvent.send(data);
            Starter.actionStart(activity, CoupletDetailActivity.class);
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig().add(BR.itemClick, onItemClickListener);
        }
    }
    @Override
    protected ViewConfig configBinding() {
        CoupletAdapter adapter = new CoupletAdapter();
        adapter.setFactory(new ListHandler());
        return new ViewConfig(R.layout.activity_couplet)
                .add(BR.adapter, adapter)
                .add(BR.state,states);
    }

    @Override
    protected void initUIComponent(@NonNull ActivityCoupletBinding binding) {
        WindowUtil.initActivityWindow(this,binding.toolbar,binding.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 网络请求-方法回调
        requester.updateAllCouplet(states.couplets::setValue, ToastUtils::showShort);
        // 请求结果监听
        //        LiveEventBus.get("IndexRepository-requestNews-mryl", WebException.class)
//                .observe(this, e -> {
//                    loading.smartDismiss();
//                    if (e.isSuccess()) {
//                        vm.refreshAllCouplet();
//                    } else {
//                        ToastUtils.showShort(e.getMessage());
//                    }
//                });

//        Glide.with(this)
//                .asBitmap()
//                .load(R.drawable.couplet_banner)
//                .placeholder(R.drawable.loading)
//                .into(binding.banner);


//        binding.setViewModel(vm);


//        adapter.setOnItemClickListener(data ->
//                CoupletDetailActivity.actionStart(this, data)
//        );
//        binding.setAdapter(adapter);




    }

}