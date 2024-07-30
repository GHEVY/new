package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AfricaActivity extends AppCompatActivity {

    public  AfricaActivity(){

    }
    RecyclerView recyclerView1;
    public Bundle args = new Bundle();
    public static final String result = "res";
    private static final String continent = "con";
    ArrayList<ImageItem> list;


    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, AfricaActivity.class);
        return i;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_africa);
        list = new ArrayList<>();
        list.add(new ImageItem(R.drawable.nigeria, "NIGERIA","AFRICA"));
        list.add(new ImageItem(R.drawable.kenya, "KENIA","AFRICA"));
        list.add(new ImageItem(R.drawable.south, "South Africa","AFRICA"));
        recyclerView1 = findViewById(R.id.rec_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        SecAdapter adapter = new SecAdapter(this, list);
        recyclerView1.setAdapter(adapter);
        Button add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            list.add(new ImageItem());
            updateAdapter();

        });
        updateAdapter();

    }
    private void updateAdapter() {
        if (args.getParcelable(result) != null) {
            Log.i("TAG", "updateAdapter: ");
            list.add(args.getParcelable(result));
        }
        SecAdapter adapter = new SecAdapter(this, list);
        recyclerView1.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}