package com.cdtde.chongdetang.useCase;

import android.annotation.SuppressLint;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.repository.AppKey;
import com.cdtde.chongdetang.utils.ValidateUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 17:43
 * @Version 1
 */
public class ValidateUseCase extends MessageHolder {

    private String code;
    private String validCode;

    private Config config = new Config()
            .setAccessKeyId(AppKey.ACCESS_KEY_ID)
            .setAccessKeySecret(AppKey.ACCESS_KEY_SECRET)
            .setEndpoint("dysmsapi.aliyuncs.com")
            .setRegionId("cn-beijing")
            .setProtocol("HTTPS");

    public final Event<Unit, Boolean> validateEvent = new Event<>();


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public boolean validateCode() {

        // 测试专用，0000为测试码
        // ******************
        if (code.equals("0000")) {
            return true;
        }
        //********************

        if (!ValidateUtil.validateCode(code) || !ValidateUtil.validateCode(validCode)) {
            return false;
        }
        return code.equals(validCode);
    }

    @SuppressLint("CheckResult")
    public void sendSms(String phone) {

        // generate validate code
        validCode = String.valueOf((int) (Math.random() * 9000) + 1000);

        // setting
        SendSmsRequest request = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName("崇德堂")
                .setTemplateCode("SMS_268495668")
                .setTemplateParam("{\"code\":\"" + validCode + "\"}");
        RuntimeOptions options = new RuntimeOptions();
        options.ignoreSSL = true;

        // send message
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
            if (!"OK".equals(s)) {
                if ("isv.BUSINESS_LIMIT_CONTROL".equals(s)) {
                    ToastUtils.showShort("验证码发送到达上限！");
                } else {
                    ToastUtils.showShort("验证码发送错误！请重试");
                }
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
