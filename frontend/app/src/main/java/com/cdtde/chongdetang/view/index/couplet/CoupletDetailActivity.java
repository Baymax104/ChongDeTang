package com.cdtde.chongdetang.view.index.couplet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityCoupletDetailBinding;
import com.cdtde.chongdetang.entity.Couplet;
import com.cdtde.chongdetang.entity.News;
import com.cdtde.chongdetang.utils.WebViewUtil;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;

import kotlin.Unit;

public class CoupletDetailActivity extends BaseActivity<ActivityCoupletDetailBinding> {



    @InjectScope(Scopes.ACTIVITY)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;

    public static class States extends StateHolder{
        public final State<Couplet> data = new State<>(new Couplet());
    }
    public static class Messenger extends MessageHolder{
        public final Event<Couplet, Unit> showEvent = new Event<>();
    }


    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_couplet_detail).add(BR.state,states);
    }

    @Override
    protected void initUIComponent(@NonNull ActivityCoupletDetailBinding binding) {
        WindowUtil.initActivityWindow(this,binding.toolbar,binding.toolbar);
        WebViewUtil.configure(binding.webPage, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_couplet_detail);
//        binding.setLifecycleOwner(this);
//        vm = new ViewModelProvider(this).get(CoupletDetailViewModel.class);
//        binding.setViewModel(vm);

//        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);
//        WebViewUtil.configure(binding.webPage, false);


        messenger.showEvent.observeSend(this, true, states.data::setValue);
//        LiveEventBus.get("CoupletDetailActivity-getData", News.class)
//                .observeSticky(this, vm::setCouplet);
    }



}