package com.cdtde.chongdetang.view.index.culture;

import android.os.Bundle;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityCultureDetailBinding;
import com.cdtde.chongdetang.entity.Culture;
import com.cdtde.chongdetang.utils.WebViewUtil;
import com.cdtde.chongdetang.utils.WindowUtil;

import kotlin.Unit;

public class CultureDetailActivity extends BaseActivity<ActivityCultureDetailBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;


    public static class Messenger extends MessageHolder {
        public final Event<Culture, Unit> showEvent = new Event<>();
    }


    public static class States extends StateHolder {
        public final State<Culture> culture = new State<>(new Culture());
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_culture_detail)
                .add(BR.state, states);
    }

    @Override
    protected void initUIComponent(@androidx.annotation.NonNull ActivityCultureDetailBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
        WebViewUtil.configure(binding.webPage, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messenger.showEvent.observeSend(this, true, states.culture::setValue);
    }

}