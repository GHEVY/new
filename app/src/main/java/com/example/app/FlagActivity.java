package com.example.app;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FlagActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    public static final int REQUEST_FLAG = 1243;
    int[] photoResIds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag);
        photoResIds = new int[]{
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

        recyclerView = findViewById(R.id.recview);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        PhotoAdapter adapter = new PhotoAdapter(this, photoResIds,this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void OnCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onItemClick(int position) {
        Intent data = new Intent();
        data.putExtra("req", photoResIds[position]);
        setResult(REQUEST_FLAG, data);
        finish();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


}
