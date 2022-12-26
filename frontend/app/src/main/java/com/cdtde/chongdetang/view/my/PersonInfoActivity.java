package com.cdtde.chongdetang.view.my;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.util.BitmapUtils;
import com.cdtde.chongdetang.util.CameraUtils;
import com.cdtde.chongdetang.util.SPUtils;
import com.cdtde.chongdetang.databinding.ActivityPersonBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonInfoActivity extends AppCompatActivity {
    private ActivityPersonBinding binding;

    //权限请求
    private RxPermissions rxPermissions;

    //是否拥有权限
    private boolean hasPermissions = false;

    //底部弹窗
    private BottomSheetDialog bottomSheetDialog;
    //弹窗视图
    private View bottomView;

    //存储拍完照后的图片
    private File outputImagePath;
    private Uri photoUri;
    //启动相机标识
    public static final int TAKE_PHOTO = 1;
    //启动相册标识
    public static final int SELECT_PHOTO = 2;
    //设置新昵称的标识
    public static final int NEW_NAME = 3;

    //图片控件
    private ShapeableImageView person_icon;
    //Base64
    private String base64Pic;
    //拍照和相册获取图片的Bitmap
    private Bitmap orc_bitmap;

    //Glide请求图片选项配置
    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存

    //activity 结束后返回的信息
    private String new_userIcon="none";
    private String new_userName="none";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initViews();
        setListener();//设置监听事件
    }

    private void initViews() {
        setSupportActionBar(binding.settingsToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    private void setListener() {
        checkVersion();
        binding.personIconLayout.setOnClickListener(v -> {
            changeAvatar();
        });
        binding.personNameLayout.setOnClickListener(v -> {
            Intent intent=new Intent();
            intent.setClass(PersonInfoActivity.this, NewNameActivity.class);
            startActivityForResult(intent, NEW_NAME);
        });
        binding.personBirthLayout.setOnClickListener(v -> {
            chooseDate();
        });
        binding.personGenderLayout.setOnClickListener(v -> {
            changeGender();
        });
    }

    private void chooseDate() {
        new DatePickerDialog(PersonInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String theDate = String.format("%d-%d-%d",year,month+1,dayOfMonth);
                binding.personBirthTv.setText(theDate);
            }
        },2000,12,24).show();

    }

    private void changeGender() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        bottomSheetDialog.setContentView(bottomView);

        TextView tvMale = bottomView.findViewById(R.id.tv_take_pictures);
        TextView tvFemale = bottomView.findViewById(R.id.tv_open_album);
        TextView tvCancel = bottomView.findViewById(R.id.tv_cancel);
        tvMale.setText("男");
        tvFemale.setText("女");

        //拍照
        tvMale.setOnClickListener(v -> {
            binding.personGenderTv.setText("男");
            bottomSheetDialog.cancel();
        });
        //打开相册
        tvFemale.setOnClickListener(v -> {
            binding.personGenderTv.setText("女");
            bottomSheetDialog.cancel();
        });
        //取消
        tvCancel.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
        });
        //底部弹窗显示
        bottomSheetDialog.show();

    }

    private void changeAvatar() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        bottomSheetDialog.setContentView(bottomView);

        TextView tvTakePictures = bottomView.findViewById(R.id.tv_take_pictures);
        TextView tvOpenAlbum = bottomView.findViewById(R.id.tv_open_album);
        TextView tvCancel = bottomView.findViewById(R.id.tv_cancel);
        person_icon = findViewById(R.id.person_icon);

        //拍照
        tvTakePictures.setOnClickListener(v -> {
            takePhoto();
            showMsg("拍照");
            bottomSheetDialog.cancel();
        });
        //打开相册
        tvOpenAlbum.setOnClickListener(v -> {
            openAlbum();
            showMsg("打开相册");
            bottomSheetDialog.cancel();
        });
        //取消
        tvCancel.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
        });
        //底部弹窗显示
        bottomSheetDialog.show();

    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        startActivityForResult(CameraUtils.getSelectPhotoIntent(), SELECT_PHOTO);
    }
    private void takePhoto() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        outputImagePath = new File(getExternalCacheDir(),
                filename + ".jpg");

        Intent takePhotoIntent = CameraUtils.getTakePhotoIntent(this, outputImagePath);
        // 开启一个带有返回值的Activity，请求码为TAKE_PHOTO
        startActivityForResult(takePhotoIntent, TAKE_PHOTO);
    }

    /**
     * 检查版本
     */
    private void checkVersion() {
        //Android6.0及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果你是在Fragment中，则把this换成getActivity()
            rxPermissions = new RxPermissions(this);
            //权限请求
            rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {//申请成功
                            hasPermissions = true;
                        } else {//申请失败
                            showMsg("权限未开启");
                            hasPermissions = false;
                        }
                    });
        } else {
            //Android6.0以下
            showMsg("无需请求动态权限");
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //头像
        switch (requestCode) {
            //拍照后返回
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //显示图片
                    new_userIcon=outputImagePath.getAbsolutePath();
                    displayImage(new_userIcon);
                }

                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    doCrop(data.getData());
                }
                break;
            case UCrop.REQUEST_CROP:
                // 加载圆形图片
                new_userIcon=UCrop.getOutput(data).toString();
                Glide.with(this).load(new_userIcon).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(person_icon);
                break;
            case NEW_NAME:
                if (resultCode==1){
                    Bundle newbundle=data.getExtras();
                    new_userName=newbundle.getString("new_name");
                    binding.personNameTv.setText(new_userName);
                }
            default:
                break;
        }
    }
    // 裁剪方法
    private void doCrop(Uri data) {
        UCrop.of(data, getDestinationUri())//当前资源，保存目标位置
                .withAspectRatio(1f, 1f)//宽高比
                .withMaxResultSize(500, 500)//宽高
                .start(this);
    }
    private Uri getDestinationUri() {
        String fileName = String.format("fr_crop_%s.jpg", System.currentTimeMillis());
        File cropFile = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);
        return Uri.fromFile(cropFile);
    }
    /**
     * 通过图片路径显示图片
     */
    private void displayImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {

            //放入缓存
            SPUtils.putString("imageUrl",imagePath,this);

            //显示图片
            Glide.with(this).load(imagePath).apply(requestOptions).into(person_icon);
            //压缩图片
            orc_bitmap = CameraUtils.compression(BitmapFactory.decodeFile(imagePath));
            //转Base64
            base64Pic = BitmapUtils.bitmapToBase64(orc_bitmap);

        } else {
            showMsg("图片获取失败");
        }
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, PersonInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Bundle bundle=new Bundle();
            bundle.putString("new_userIcon",new_userIcon);
            bundle.putString("new_userName",new_userName);
            Intent intent=getIntent();
            intent.putExtras(bundle);
            intent.setClass(PersonInfoActivity.this, MyFragment.class);
            setResult(2,intent);
            finish();
        }
        return true;
    }
    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
