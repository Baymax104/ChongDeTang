package com.cdtde.chongdetang.view.index.moment;

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
import com.cdtde.chongdetang.databinding.ActivityMomentDetailBinding;
import com.cdtde.chongdetang.entity.Moment;
import com.cdtde.chongdetang.utils.WebViewUtil;
import com.cdtde.chongdetang.utils.WindowUtil;

import kotlin.Unit;

/**
 * “展馆动态”内容详情页
 */
public class MomentDetailActivity extends BaseActivity<ActivityMomentDetailBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;


    public static class States extends StateHolder {
        /**
         * 当前展示内容对象
         */
        public final State<Moment> moment = new State<>(new Moment());
    }

    public static class Messenger extends MessageHolder {
        /**
         * 展示事件
         */
        public final Event<Moment, Unit> showEvent = new Event<>();
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_moment_detail)
                .add(BR.state, states);
    }

    @Override
    protected void initUIComponent(@NonNull ActivityMomentDetailBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
        WebViewUtil.configure(binding.webPage, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messenger.showEvent.observeSend(this, true, states.moment::setValue);
    }

}