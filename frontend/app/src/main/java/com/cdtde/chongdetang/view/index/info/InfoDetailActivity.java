package com.cdtde.chongdetang.view.index.info;

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
import com.cdtde.chongdetang.databinding.ActivityInfoDetailBinding;
import com.cdtde.chongdetang.entity.Info;
import com.cdtde.chongdetang.utils.WebViewUtil;
import com.cdtde.chongdetang.utils.WindowUtil;

import kotlin.Unit;

public class InfoDetailActivity extends BaseActivity<ActivityInfoDetailBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;

    public static class States extends StateHolder {
        public final State<Info> info = new State<>(new Info());
    }

    public static class Messenger extends MessageHolder {
        public final Event<Info, Unit> showEvent = new Event<>();
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_info_detail)
                .add(BR.state, states);
    }

    @Override
    protected void initUIComponent(@NonNull ActivityInfoDetailBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
        WebViewUtil.configure(binding.webPage, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messenger.showEvent.observeSend(this, true, states.info::setValue);
    }

}