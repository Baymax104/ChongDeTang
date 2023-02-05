package com.cdtde.chongdetang.viewModel.my;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.repository.AppKey;
import com.cdtde.chongdetang.util.ValidateUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 17:43
 * @Version 1
 */
public abstract class ValidateViewModel extends ViewModel {
    private String code;
    private String validCode;

    protected String phone;
    protected String finalPhone;  // 防止用户在验证过程中修改手机号

    private Config config = new Config()
            .setAccessKeyId(AppKey.ACCESS_KEY_ID)
            .setAccessKeySecret(AppKey.ACCESS_KEY_SECRET)
            .setEndpoint("dysmsapi.aliyuncs.com")
            .setRegionId("cn-beijing")
            .setProtocol("HTTPS");

    private MutableLiveData<Boolean> enabled;

    private MutableLiveData<String> tip;

    private class Timer extends CountDownTimer {
        public Timer() {
            super(60000, 1000);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            enabled.postValue(false);
            String content = millisUntilFinished / 1000 + "秒后可重新发送";
            tip.postValue(content);
        }

        @Override
        public void onFinish() {
            enabled.postValue(true);
            tip.postValue("重新获取验证码");
        }

    }

    public ValidateViewModel() {
        enabled = new MutableLiveData<>(true);
        tip = new MutableLiveData<>("获取验证码");
    }

    public abstract User getUser();

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MutableLiveData<Boolean> getEnabled() {
        return enabled;
    }

    public MutableLiveData<String> getTip() {
        return tip;
    }

    public boolean validate() {
        if (StringUtils.isEmpty(code)) {
            return false;
        }

        // 测试专用，0000为测试码
        // ******************
        if (code.equals("0000")) {
            validCode = "0000";
            finalPhone = phone;
        }
        //********************

        return code.equals(validCode);
    }

    @SuppressLint("CheckResult")
    public void sendSms() {
        // validate phone
        validCode = String.valueOf((int) (Math.random() * 9000) + 1000);
        if (!ValidateUtil.validatePhone(phone)) {
            return;
        }

        // setting
        String tmpPhone = phone;
        SendSmsRequest request = new SendSmsRequest()
                .setPhoneNumbers(tmpPhone)
                .setSignName("崇德堂")
                .setTemplateCode("SMS_268495668")
                .setTemplateParam("{\"code\":\"" + validCode + "\"}");
        RuntimeOptions options = new RuntimeOptions();
        options.ignoreSSL = true;

        // send message
        Timer timer = new Timer();
        timer.start();
        Observable<String> sender = Observable.create(emitter -> {
            Client client = new Client(config);
            SendSmsResponse response;
            response = client.sendSmsWithOptions(request, options);
            String status = response.body.code;
            LogUtils.iTag("cdt-validate-sendSms", status);
            emitter.onNext(status);
            emitter.onComplete();
        });

        Consumer<String> onNext = s -> {
            if ("OK".equals(s)) {
                finalPhone = tmpPhone;
            } else if ("isv.BUSINESS_LIMIT_CONTROL".equals(s)) {
                ToastUtils.showShort("验证码发送到达上限！");
            } else {
                ToastUtils.showShort("验证码发送错误！请重试");
            }
        };

        sender.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-validate-sendSms", throwable),
                        () -> LogUtils.eTag("cdt-validate-sendSms", "验证码发送完成")
                );
    }

}
