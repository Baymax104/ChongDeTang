package com.cdtde.chongdetang.view.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActicityNewnameBinding;

public class NewNameActivity extends AppCompatActivity {
    private ActicityNewnameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActicityNewnameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initViews();
        setListener();//设置监听事件
    }

    private void setListener() {
        binding.newNameSave.setOnClickListener(v -> {
            String content=binding.newNameContent.getText().toString();//用户输入内容
            Bundle bundle=new Bundle();
            bundle.putString("new_name",content);
            Intent intent=getIntent();
            intent.putExtras(bundle);
            intent.setClass(NewNameActivity.this, PersonInfoActivity.class);
//                startActivity(intent);
            setResult(1,intent);
            finish();


//            Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
//            finish();
        });
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, NewNameActivity.class);
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
