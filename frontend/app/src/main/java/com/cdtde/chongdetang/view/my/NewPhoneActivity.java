package com.cdtde.chongdetang.view.my;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;

        import com.cdtde.chongdetang.R;
        import com.cdtde.chongdetang.databinding.ActivityNewPhoneBinding;

public class NewPhoneActivity extends AppCompatActivity {
    private ActivityNewPhoneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPhoneBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initViews();
        setListener();//设置监听事件
    }

    private void setListener() {
        //按钮
        binding.newPhoneCommit.setOnClickListener(v -> {
            String phoneCode=binding.newPhoneEnterCodeEv.getText().toString();//用户输入内容
            if (phoneCode.isEmpty()){
                Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            }
            else{
                //检验验证码
                Toast.makeText(this, "验证码："+phoneCode, Toast.LENGTH_SHORT).show();
                finish();
            }

        });
        //获取验证码
        binding.newPhoneGetCodeTv.setOnClickListener(v -> {
            String phoneNumber=binding.newPhoneEnterPhoneEv.getText().toString();//新手机号
            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            }
            else if (phoneNumber.length()!=11  || !phoneNumber.matches("[0-9]+")){
                Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            }
            else
                //发送验证码
                Toast.makeText(this, "向"+phoneNumber+"发送验证码", Toast.LENGTH_SHORT).show();

        });
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, NewPhoneActivity.class);
        context.startActivity(intent);
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

//        input=findViewById(R.id.feedback_content);
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
