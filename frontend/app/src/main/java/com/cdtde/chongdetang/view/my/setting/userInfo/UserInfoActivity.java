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

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityUserInfoBinding;
import com.cdtde.chongdetang.util.CameraUtil;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.PermissionUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.viewModel.my.UserInfoViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.yalantis.ucrop.UCrop;

import java.io.File;

public class UserInfoActivity extends AppCompatActivity {
    private ActivityUserInfoBinding binding;
    private UserInfoViewModel vm;

    // 需要缓存一个引用，在RESULT_OK时赋值
    private Uri photoUri;

    // startActivityForResult的推荐写法
    private ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    File file = UriUtils.uri2File(photoUri);
                    Uri uri = Uri.fromFile(file);
                    vm.getUser().setPhoto(uri);
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

        WindowUtil.initActivityWindow(binding.toolbar, this, true);
        binding.setUser(vm.getUser());

        LiveEventBus.get("user_gender", String.class)
                    .observe(this, s -> vm.getUser().setGender(s));

        LiveEventBus.get("username", String.class)
                    .observe(this, s -> vm.getUser().setName(s));

        LiveEventBus.get("user_icon_action", Integer.class)
                    .observe(this, action -> {
                        if (action == 1) { // take photo
                            takePhoto();
                        } else if (action == 2) { // use album
                            openAlbum();
                        }
                    });


        binding.iconEntry.setOnClickListener(v -> DialogUtil.create(this, IconDialog.class).show());

        binding.nameEntry.setOnClickListener(v -> UsernameActivity.actionStart(this));

        binding.birthEntry.setOnClickListener(v -> {
            TimePickerView picker = new TimePickerBuilder(this, (date, v1) -> vm.getUser().setBirthday(date))
                    .setTitleText("选择日期")
                    .setCancelColor(Color.BLACK)
                    .setSubmitColor(Color.parseColor("#bd1b21"))
                    .build();
            picker.show();
        });

        binding.genderEntry.setOnClickListener(v -> DialogUtil.create(this, GenderDialog.class).show());

    }


    private void takePhoto() {
        PermissionUtil.requestPermission(Manifest.permission.CAMERA);

        // 启动相机
        File file = CameraUtil.createNewFile();
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
                vm.getUser().setPhoto(output);
            }
        }
    }

    private void doCrop(Uri data) {
        // UCrop不能读取FileProvider封装的路径，必须获取真实的路径
        Uri destUri = Uri.fromFile(CameraUtil.createNewFile());

        UCrop.Options options = new UCrop.Options();
        options.setActiveControlsWidgetColor(Color.parseColor("#bd1b21"));
        options.setCircleDimmedLayer(true);
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(60);
        options.setFreeStyleCropEnabled(true);
        UCrop.of(data, destUri)
                .withOptions(options)
                .start(this);
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
