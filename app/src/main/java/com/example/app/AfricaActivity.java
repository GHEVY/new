package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AfricaActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {


    RecyclerView recyclerView1;
    public static final int requestAdd = 124;
    ArrayList<ImageItem> list;
    private static final String extra = "extra";
    private static final int requestChange = 221;
    int newPosition;


    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, AfricaActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_africa);
        list = new ArrayList<>();
        list.add(new ImageItem(R.drawable.nigeria, "NIGERIA", "AFRICA"));
        list.add(new ImageItem(R.drawable.kenya, "KENIA", "AFRICA"));
        list.add(new ImageItem(R.drawable.south, "South Africa", "AFRICA"));
        recyclerView1 = findViewById(R.id.rec_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        SecAdapter adapter = new SecAdapter(this, list, this);
        recyclerView1.setAdapter(adapter);
        Button add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            Intent intent = AddActivity.newIntent(AfricaActivity.this);
            intent.putExtra("name","AFRICA");
            startActivityForResult(intent, requestAdd);
        });

    }

    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if (req == requestAdd) {
            ImageItem a = data.getParcelableExtra("key");
            assert a != null;
            a.setContinent("AFRICA");
            list.add(a);
        } else if (req == requestChange) {
            list.set(newPosition, data.getParcelableExtra("requestChange"));
        }
        updateAdapter();

    }

    private void updateAdapter() {
        SecAdapter adapter = new SecAdapter(this, list, this);
        recyclerView1.setAdapter(adapter);
    }

    @Override
    public void OnCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(AfricaActivity.this, CountryActivity.class);
        i.putExtra(extra, list.get(position));
        i.putExtra("con","AFRICA");
        newPosition = position;
        startActivityForResult(i, requestChange);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}