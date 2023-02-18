package com.cdtde.chongdetang.view.my.setting.userInfo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityUserInfoBinding;
import com.cdtde.chongdetang.util.CameraUtil;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.PermissionUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.viewModel.my.UserInfoViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopupext.popup.TimePickerPopup;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Calendar;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class UserInfoActivity extends AppCompatActivity {
    private ActivityUserInfoBinding binding;
    private UserInfoViewModel vm;

    private LoadingPopupView loadingPopupView;

    private TimePickerPopup timePicker;

    // 需要缓存一个引用，在RESULT_OK时赋值
    private Uri photoUri;

    // startActivityForResult的推荐写法
    private ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    File file = UriUtils.uri2File(photoUri);
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);
        vm = new ViewModelProvider(this).get(UserInfoViewModel.class);
        binding.setLifecycleOwner(this);

        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);
        binding.setViewModel(vm);

        XPopup.Builder builder = new XPopup.Builder(this).dismissOnTouchOutside(false);
        loadingPopupView = (LoadingPopupView) DialogUtil.create(this, LoadingPopupView.class, builder);

        Calendar start = Calendar.getInstance();
        start.set(1900, 0, 1);
        Calendar end = Calendar.getInstance();
        timePicker = new TimePickerPopup(this)
                .setDefaultDate(end)
                .setDateRange(start, end)
                .setTimePickerListener((DialogUtil.TimePickerListenerAdapter) (date, view) ->
                        vm.getUser().setBirthday(date)
                );

        LiveEventBus.get("GenderDialog-gender", String.class)
                    .observe(this, s -> vm.getUser().setGender(s));

        LiveEventBus.get("UsernameActivity-username", String.class)
                    .observe(this, s -> vm.getUser().setUsername(s));

        LiveEventBus.get("PhotoDialog-action", Integer.class)
                    .observe(this, action -> {
                        if (action == 1) { // take photo
                            takePhoto();
                        } else if (action == 2) { // use album
                            openAlbum();
                        }
                    });

        LiveEventBus.get("MyRepository-updateInfo", Boolean.class)
                        .observe(this, aBoolean -> {
                            loadingPopupView.smartDismiss();
                            if (aBoolean) {
                                finish();
                            }
                        });


        binding.iconEntry.setOnClickListener(v ->
                DialogUtil.create(this, PhotoDialog.class, null).show()
        );

        binding.nameEntry.setOnClickListener(v -> UsernameActivity.actionStart(this));

        binding.birthEntry.setOnClickListener(v ->
                DialogUtil.create(this, timePicker, null).show()
        );

        binding.genderEntry.setOnClickListener(v ->
                DialogUtil.create(this, GenderDialog.class, null).show()
        );

        binding.confirm.setOnClickListener(v -> {
            loadingPopupView.show();
            vm.update();
        });
    }


    private void takePhoto() {
        PermissionUtil.requestPermission(Manifest.permission.CAMERA);

        // 启动相机
        File file = CameraUtil.createNewFile(false);
        photoUri = UriUtils.file2Uri(file);
        Intent intent = CameraUtil.startCamera(file);
        if (intent != null) {
            cameraLauncher.launch(intent);
        }
    }

    private void openAlbum() {
        PermissionUtil.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        Intent intent = CameraUtil.startPhotoPicker();
        pickerLauncher.launch(intent);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            Uri output = UCrop.getOutput(data);
            if (output != null) {
                vm.getUser().setPhoto(output.toString());
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
                        vm.getUser().setPhoto(uri.toString());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.showShort("图片压缩失败：" + throwable.getMessage());
                    }
                })
                .launch();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
