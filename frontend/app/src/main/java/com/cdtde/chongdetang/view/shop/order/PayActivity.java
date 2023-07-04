package com.cdtde.chongdetang.view.shop.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.databinding.ActivityPayBinding;
import com.cdtde.chongdetang.utils.WindowUtil;

import kotlin.Unit;

public class PayActivity extends BaseActivity<ActivityPayBinding> {

    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;

    public static class Messenger extends MessageHolder {
        public final Event<Unit, Unit> payEvent = new Event<>();
    }

    public class Handler {
        public final OnClickListener pay = v -> {
            messenger.payEvent.send(Unit.INSTANCE);
            finish();
        };
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_pay)
                .add(BR.handler, new Handler());
    }

    @Override
    protected void initUIComponent(@NonNull ActivityPayBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }

}