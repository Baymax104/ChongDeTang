package com.cdtde.chongdetang.view.index;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityCultureBinding;
import com.cdtde.chongdetang.entity.Culture;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.viewModel.CultureViewModel;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

import com.cdtde.chongdetang.util.adapter.CulturePagerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.reactivex.annotations.NonNull;


public class CultureActivity extends AppCompatActivity {

    private ActivityCultureBinding binding;
    private CultureViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_culture);
        vm = new ViewModelProvider(this).get(CultureViewModel.class);
        WindowUtil.initActivityWindow(binding.toolbar, this, true);

        CulturePagerAdapter sectionsPagerAdapter = new CulturePagerAdapter(this, getSupportFragmentManager());
        binding.setLifecycleOwner(this);
        binding.setTabAdapter(sectionsPagerAdapter);
        binding.setViewModel(vm);




        binding.viewPager.setAdapter(sectionsPagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);


        vm.setCulturePage1(readCsv("culture1.csv",10));

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CultureActivity.class);
        context.startActivity(intent);
    }

    public ArrayList readCsv(String filename,int num){
        ArrayList readerArr = new ArrayList<>();
        InputStreamReader is = null;
        String nowLine;
        try {
            is = new InputStreamReader(getAssets().open(filename));
            BufferedReader reader = new BufferedReader(is);
//            reader.readLine(); //有表头加这一行
            for (int i=0;i<num;i++){
                if ((nowLine = reader.readLine())!= null){
                    String[] line=nowLine.split(",");
                    Culture bean = new Culture(line[2], line[1]);
                    readerArr.add(bean);
                    continue;
                }
                else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readerArr;
    }

}