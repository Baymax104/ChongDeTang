package com.cdtde.chongdetang.view.my.setting.userInfo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View.OnClickListener;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityUserInfoBinding;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.requester.UserInfoRequester;
import com.cdtde.chongdetang.utils.CameraUtil;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Permission;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import kotlin.Unit;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 用户信息页
 */
public class UserInfoActivity extends BaseActivity<ActivityUserInfoBinding> {

    @InjectScope(Scopes.APPLICATION)
    private UserInfoRequester requester;

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;

    public static class States extends StateHolder {
        /**
         * 用户信息实体
         */
        public final State<User> user = new State<>(new User(UserStore.getUser()));
        public Uri photoUri;
    }

    public static class Messenger extends MessageHolder {
        /**
         * 用户信息修改事件
         */
        public final Event<User, User> userEvent = new Event<>();
        /**
         * 头像获取事件
         */
        public final Event<Integer, Unit> photoAction = new Event<>();
        /**
         * 用户信息更新通知事件
         */
        public final Event<Unit, Unit> userInfoUpdate = new Event<>();
    }


    // startActivityForResult的推荐写法
    private ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    File file = UriUtils.uri2File(states.photoUri);
                    doCompression(file);
                }
            });

    private ActivityResultLauncher<Intent> pickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent intent = result.getData();
                if (result.getResultCode() == RESULT_OK && intent != null) {
                    Uri uri = intent.getData();
                    doCrop(uri);
                }
            });

    @Override
    protected ViewConfig configBinding() {
        requester.registerObserver(DialogUtil.createNetLoading(this), this);
        return new ViewConfig(R.layout.activity_user_info)
                .add(BR.state, states)
                .add(BR.handler, new Handler());
    }

    @Override
    protected void initUIComponent(@NonNull ActivityUserInfoBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }

    public class Handler {
        public OnClickListener iconEntry = v -> {
            messenger.userEvent.send(states.user.getValue());
            DialogUtil.create(activity, PhotoDialog.class, null).show();
        };
        public OnClickListener nameEntry = v -> {
            messenger.userEvent.send(states.user.getValue());
            Starter.actionStart(activity, UsernameActivity.class);
        };
        public OnClickListener birthEntry = v ->
                DialogUtil.createTimePicker(activity, states.user.getValue()::setBirthday).show();
        public OnClickListener genderEntry = v -> {
            messenger.userEvent.send(states.user.getValue());
            DialogUtil.create(activity, GenderDialog.class, null).show();
        };

        public OnClickListener confirm = v ->
                requester.update(states.user.getValue(),
                        o -> {
                            // 通知其他页面更新用户信息
                            messenger.userInfoUpdate.send(Unit.INSTANCE);
                            finish();
                        }, ToastUtils::showShort);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messenger.userEvent.observeReply(this, states.user::setValue);

        messenger.photoAction.observeSend(this, value -> {
            if (value == 1) { // take photo
                new Permission(Manifest.permission.CAMERA)
                        .granted(this::takePhoto)
                        .denied(() -> ToastUtils.showShort("开启权限失败，请进入权限中心开启权限"))
                        .request();
            } else if (value == 2) { // use album
                new Permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .granted(this::openAlbum)
                        .denied(() -> ToastUtils.showShort("开启权限失败，请进入权限中心开启权限"))
                        .request();
            }
        });

    }


    private void takePhoto() {
        // 启动相机
        File file = CameraUtil.createNewFile(false);
        states.photoUri = UriUtils.file2Uri(file);
        Intent intent = CameraUtil.startCamera(file);
        if (intent != null) {
            cameraLauncher.launch(intent);
        }
    }

    private void openAlbum() {
        Intent intent = CameraUtil.startPhotoPicker();
        pickerLauncher.launch(intent);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            Uri output = UCrop.getOutput(data);
            if (output != null) {
                states.user.getValue().setPhoto(output.toString());
            }
        }
    }

    private void doCrop(Uri data) {
        // UCrop不能读取FileProvider封装的路径，必须获取真实的路径
        Uri destUri = Uri.fromFile(CameraUtil.createNewFile(true));

        UCrop.Options options = new UCrop.Options();
        options.setActiveControlsWidgetColor(Color.parseColor("#bd1b21"));
        options.setCircleDimmedLayer(true);
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(80);
        options.setFreeStyleCropEnabled(true);
        UCrop.of(data, destUri)
                .withOptions(options)
                .start(this);
    }

    private void doCompression(File data) {
        String dir = PathUtils.getExternalAppFilesPath();

        Luban.with(this)
                .load(data)
                .ignoreBy(80)
                .setTargetDir(dir)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        Uri uri = Uri.fromFile(file);
                        states.user.getValue().setPhoto(uri.toString());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.showShort("图片压缩失败：" + throwable.getMessage());
                        Uri uri = Uri.fromFile(data);
                        states.user.getValue().setPhoto(uri.toString());
                    }
                })
                .launch();
    }


}
