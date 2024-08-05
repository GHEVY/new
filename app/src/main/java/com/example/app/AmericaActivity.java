package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AmericaActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, AmericaActivity.class);
    }

    ArrayList<ImageItem> list;
    RecyclerView recyclerView1;
    public static final int requestADD = 124;
    private static final String extra = "extra";
    private static final int requestChange = 221;
    int newPosition;

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
        SecAdapter adapter = new SecAdapter(this, list, this);
        recyclerView1.setAdapter(adapter);
        Button add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            Intent intent = AddActivity.newIntent(AmericaActivity.this);
            intent.putExtra("name", "AMERICA");
            startActivityForResult(intent, requestADD);
        });
    }

    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if(data!=null){
            if (req == requestADD) {
                ImageItem a = data.getParcelableExtra("key");
                assert a != null;
                a.setContinent("AMERICA");
                list.add(a);
            } else if (req == requestChange) {
                list.set(newPosition, data.getParcelableExtra("requestChange"));
            }
            updateAdapter();
        }
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
        Intent i = new Intent(AmericaActivity.this, CountryActivity.class);
        i.putExtra(extra, list.get(position));
        i.putExtra("con","AMERICA");
        newPosition = position;
        startActivityForResult(i, requestChange);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}