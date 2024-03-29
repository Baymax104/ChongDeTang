package com.cdtde.chongdetang.view.index.couplet;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.BR;
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
import com.cdtde.chongdetang.utils.WebViewUtil;
import com.cdtde.chongdetang.utils.WindowUtil;

import kotlin.Unit;

/**
 * “每日一联”内容详情页
 */
public class CoupletDetailActivity extends BaseActivity<ActivityCoupletDetailBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;

    public static class States extends StateHolder{
        /**
         * 当前展示内容对象
         */
        public final State<Couplet> data = new State<>(new Couplet());
    }
    public static class Messenger extends MessageHolder{
        /**
         * 展示事件
         */
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
        messenger.showEvent.observeSend(this, true, states.data::setValue);
    }

}