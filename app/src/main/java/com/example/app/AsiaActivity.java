package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AsiaActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {
    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, AsiaActivity.class);
    }

    ArrayList<ImageItem> list;
    RecyclerView recyclerView1;
    public static final int requestAdd = 124;
    private static final String extra = "extra";
    private static final int requestChange = 221;
    int newPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asia);
        list = new ArrayList<>();
        list.add(new ImageItem(R.drawable.japan, "JAPAN", "ASIA"));
        list.add(new ImageItem(R.drawable.china, "CHINA", "ASIA"));
        list.add(new ImageItem(R.drawable.india, "INDIA", "ASIA"));
        recyclerView1 = findViewById(R.id.rec_view2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        SecAdapter adapter = new SecAdapter(this, list, this);
        recyclerView1.setAdapter(adapter);
        Button add = findViewById(R.id.add);
        add.setOnClickListener(v -> {

            Intent intent = AddActivity.newIntent(AsiaActivity.this);
            intent.putExtra("name","ASIA");
            startActivityForResult(intent, requestAdd);
        });
    }

    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if (req == requestAdd) {
            ImageItem a = data.getParcelableExtra("key");
            assert a != null;
            a.setContinent("ASIA");
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
        Intent i = new Intent(AsiaActivity.this, CountryActivity.class);
        i.putExtra(extra, list.get(position));
        i.putExtra("con","ASIA");
        newPosition = position;
        startActivityForResult(i, requestChange);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}

