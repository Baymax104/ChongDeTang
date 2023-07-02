package com.cdtde.chongdetang.view.my;

import android.os.Bundle;
import android.text.Editable;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityFeedbackBinding;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.requester.UserRequester;

public class FeedbackActivity extends BaseActivity<ActivityFeedbackBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private UserRequester requester;

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    public static class States extends StateHolder {
        public State<String> content = new State<>("");
    }

    @Override
    protected ViewConfig configBinding() {
        requester.registerObserver(DialogUtil.createNetLoading(this), this);

        return new ViewConfig(R.layout.activity_feedback)
                .add(BR.state, states)
                .add(BR.handler, new Handler());
    }

    public class Handler {
        public OnClickListener commit = v ->
                requester.updateFeedback(states.content.getValue(),
                        o -> {
                            ToastUtils.showShort("反馈成功");
                            finish();
                        },
                        ToastUtils::showShort);

        public void setContent(Editable s) {
            states.content.setValue(s.toString());
        }
    }

    @Override
    protected void initUIComponent(@NonNull ActivityFeedbackBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
