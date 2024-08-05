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

public class EuropeActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {
    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, EuropeActivity.class);
    }

    RecyclerView recyclerView1;
    public static final int requestAdd = 124;
    ArrayList<ImageItem> list;
    private static final String extra = "extra";
    private static final int requestChange = 221;
    int newPosition;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_europe);
        list = new ArrayList<>();
        list.add(new ImageItem(R.drawable.france, "FRANCE", "EUROPE"));
        list.add(new ImageItem(R.drawable.germany, "GERMANY", "EUROPE"));
        list.add(new ImageItem(R.drawable.spain, "SPAIN", "EUROPE"));
        recyclerView1 = findViewById(R.id.rec_view3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        SecAdapter adapter = new SecAdapter(this, list, this);
        recyclerView1.setAdapter(adapter);
        Button add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            Intent intent = AddActivity.newIntent(EuropeActivity.this);
            intent.putExtra("name","EUROPE");
            startActivityForResult(intent, requestAdd);
        });
    }

    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if (req == requestAdd) {
            ImageItem a = data.getParcelableExtra("key");
            assert a != null;
            a.setContinent("EUROPE");
            list.add(a);
        } else if (req == requestChange) {
            list.set(newPosition, data.getParcelableExtra("                Toast.makeText(this, data.getParcelableExtra(\"requestChange\"), Toast.LENGTH_SHORT).show();\n"));
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
        Intent i = new Intent(EuropeActivity.this, CountryActivity.class);
        i.putExtra(extra, list.get(position));
        i.putExtra("con","EUROPE");
        newPosition = position;
        startActivityForResult(i, requestChange);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}