package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app.databinding.ActivityFlagBinding;

public class FlagActivity extends AppCompatActivity implements ContinentAdapter.OnItemClickListener {

    private static final int REQUEST_CODE_FLAG = 1243;
    private final int[] photoResIds = new int[]{
            R.drawable.usa,
            R.drawable.bra,
            R.drawable.china,
            R.drawable.canada,
            R.drawable.france,
            R.drawable.germany,
            R.drawable.india,
            R.drawable.nigeria,
            R.drawable.kenya,
            R.drawable.japan,
            R.drawable.spain,
            R.drawable.south
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFlagBinding binding = ActivityFlagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.recView.setLayoutManager(layoutManager);
        PhotoAdapter adapter = new PhotoAdapter(this, photoResIds, this);
        binding.recView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(int position) {
        Intent data = new Intent();
        data.putExtra("flag", photoResIds[position]);
        setResult(REQUEST_CODE_FLAG, data);
        finish();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
