package com.cdtde.chongdetang.controller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.cdtde.chongdetang.databinding.ActivitySearchBinding;
import com.cdtde.chongdetang.util.SearchTagAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;

    private List<String> tags = new ArrayList<>(); // 模拟tag

    private List<String> historyTags = new ArrayList<>(); // 最近搜索tag


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initView();

        // 搜索框内容监听
        binding.searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                if (content.equals("")) {
                    binding.searchCleanBtn.setVisibility(View.INVISIBLE);
                } else {
                    binding.searchCleanBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.searchHistoryFlow.setOnTagClickListener((view1, i, flowLayout) -> {
            binding.searchEdit.setText(historyTags.get(i));
            return true;
        });

        // 搜索框清除按钮
        binding.searchCleanBtn.setOnClickListener(v -> binding.searchEdit.setText(""));

        // 藏品推荐tags
        SearchTagAdapter collectionAdapter = new SearchTagAdapter(this, tags);
        binding.searchCollectionFlow.setAdapter(collectionAdapter);
        binding.searchCollectionFlow.setOnTagClickListener((view1, i, flowLayout) -> {
            binding.searchEdit.setText(tags.get(i));
            return true;
        });

        // 商品推荐tags
        SearchTagAdapter productAdapter = new SearchTagAdapter(this, tags);
        binding.searchProductFlow.setAdapter(productAdapter);
        binding.searchProductFlow.setOnTagClickListener((view1, i, flowLayout) -> {
            binding.searchEdit.setText(tags.get(i));
            return true;
        });

        // 删除最近搜索按钮
        binding.searchDeleteBtn.setOnClickListener(v -> {
            binding.searchHistoryLabel.setVisibility(View.GONE);
            binding.searchHistoryFlow.setVisibility(View.GONE);
            historyTags.clear();
        });

        // 搜索按钮
        binding.searchBtn.setOnClickListener(v -> {
            String content = binding.searchEdit.getText().toString();
            if (!content.equals("")) {
                if (historyTags.isEmpty()) {
                    binding.searchHistoryFlow.setVisibility(View.VISIBLE);
                    binding.searchHistoryLabel.setVisibility(View.VISIBLE);
                }
                if (!historyTags.contains(content)) {
                    historyTags.add(content);
                    SearchTagAdapter adapter = new SearchTagAdapter(this, historyTags);
                    binding.searchHistoryFlow.setAdapter(adapter);
                }
                // 搜索
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        setSupportActionBar(binding.searchToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // 模拟tag
        tags.add("ZJpkDd");
        tags.add("hWDDTi");
        tags.add("XHqfzcU");
        tags.add("QhXiN");
        tags.add("LZXWQK");

    }
}