package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AmericaActivity extends AppCompatActivity {

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, AmericaActivity.class);
    }

    ArrayList<ImageItem> list;
    RecyclerView recyclerView1;
    public Bundle args = new Bundle();
    public static final String result = "res";
    private static final String continent = "con";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_america);
        if (list == null) {
            list = new ArrayList<>();
            list.add(new ImageItem(R.drawable.usa, "USA", "AMERICA"));
            list.add(new ImageItem(R.drawable.bra, "BRAZIL", "AMERICA"));
            list.add(new ImageItem(R.drawable.canada, "CANADA", "AMERICA"));
        }
        recyclerView1 = findViewById(R.id.rec_view1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        SecAdapter adapter = new SecAdapter(this, list);
        recyclerView1.setAdapter(adapter);
        Button add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            list.add(new ImageItem());
            updateAdapter();

        });

    }


    private void updateAdapter() {
        SecAdapter adapter = new SecAdapter(this, list);
        recyclerView1.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}