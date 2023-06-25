package com.cdtde.chongdetang.view.my.setting;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentValidateBinding;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.viewModel.my.ValidateUseCase;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 0:05
 * @Version 1
 */
public class ValidateFragment extends BaseFragment<FragmentValidateBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private ValidateUseCase useCase;

    @InjectScope(Scopes.FRAGMENT)
    private States states;

    public static class States extends StateHolder {
        public final State<Boolean> enabled = new State<>(true);
        public final State<String> tip = new State<>("获取验证码");
        public final String phone = UserStore.getPhone();
        public final State<String> code = new State<>("");
    }

    public class Handler {
        public OnClickListener sendSms = v -> {
            Timer timer = new Timer();
            timer.start();
            useCase.sendSms(states.phone);
        };

        public void setCode(@NonNull Editable s) {
            states.code.setValue(s.toString());
        }
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.fragment_validate)
                .add(BR.state, states)
                .add(BR.handler, new Handler());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        useCase.validateEvent.observeSend(this, value -> {
            useCase.setCode(states.code.getValue());
            useCase.validateEvent.reply(useCase.validateCode());
        });
    }

    private class Timer extends CountDownTimer {
        public Timer() {
            super(60000, 1000);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            states.enabled.setValue(false);
            String content = millisUntilFinished / 1000 + "秒后可重新发送";
            states.tip.setValue(content);
        }

        @Override
        public void onFinish() {
            states.enabled.setValue(true);
            states.tip.setValue("重新获取验证码");
        }

    }


}
