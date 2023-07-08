package com.cdtde.chongdetang.view.my.setting.userPhone;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentUserPhoneBinding;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.useCase.PhoneValidateUseCase;
import com.cdtde.chongdetang.useCase.ValidateUseCase;
import com.cdtde.chongdetang.utils.ValidateUtil;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 19:49
 * @Version 1
 */
public class UserPhoneFragment extends BaseFragment<FragmentUserPhoneBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private PhoneValidateUseCase phoneValidateUseCase;

    @InjectScope(Scopes.FRAGMENT)
    private States states;

    @InjectScope(Scopes.FRAGMENT)
    private ValidateUseCase validateUseCase;


    public static class States extends StateHolder {
        public final State<Boolean> enabled = new State<>(true);
        public final State<String> tip = new State<>("获取验证码");
        public final String originPhone = UserStore.getPhone();
        public final State<String> inputPhone = new State<>("");
        public String finalPhone;
        public final State<String> code = new State<>("");
    }

    public class Handler {
        public final OnClickListener sendSms = v -> {
            if (ValidateUtil.validatePhone(states.inputPhone.getValue())) {
                Timer timer = new Timer();
                timer.start();
                validateUseCase.sendSms(states.inputPhone.getValue());
                states.finalPhone = states.inputPhone.getValue();
            } else {
                ToastUtils.showShort("手机号格式错误");
            }
        };

        public void setCode(Editable s) {
            states.code.setValue(s.toString());
        }

        public void setInputPhone(Editable s) {
            states.inputPhone.setValue(s.toString());
        }
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.fragment_user_phone)
                .add(BR.state, states)
                .add(BR.handler, new Handler());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        phoneValidateUseCase.validateEvent.observeSend(this, value -> {
            phoneValidateUseCase.setCode(states.code.getValue());
            phoneValidateUseCase.setValidCode(validateUseCase.getValidCode());
            if ("0000".equals(states.code.getValue())) {
                states.finalPhone = states.inputPhone.getValue();
            }
            phoneValidateUseCase.setFinalPhone(states.finalPhone);
            phoneValidateUseCase.validateEvent.reply(phoneValidateUseCase.validateCode());
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
