package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {
    ArrayList<ImageItem> list;
    private static final String extra = "extra";
    private static final int requestChange = 221;
    int newPosition;
    RecyclerView recyclerView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        recyclerView1 = findViewById(R.id.rec_view1);
        list = ImageItem.getFavList();
        if (!list.isEmpty()) {
            TextView a = findViewById(R.id.note);
            a.setVisibility(View.GONE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        SecAdapter adapter = new SecAdapter(this, list, this);
        recyclerView1.setAdapter(adapter);
        updateAdapter();
    }

    @Override
    public void OnCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(FavActivity.this, CountryActivity.class);
        i.putExtra(extra, list.get(position));
        i.putExtra("con", list.get(position).getContinent());
        newPosition = position;
        startActivityForResult(i, requestChange);
    }

    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if (req == requestChange) {
            ImageItem a = data.getParcelableExtra("requestChange");
            assert a != null;
            if (!a.isFav) {
                list.remove(newPosition);
            } else {
                list.set(newPosition, data.getParcelableExtra("requestChange"));
            }
        }
        updateAdapter();
    }

    private void updateAdapter() {
        list = ImageItem.getFavList();
        SecAdapter adapter = new SecAdapter(this, list, this);
        recyclerView1.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (list.isEmpty()) {
            TextView a = findViewById(R.id.note);
            a.setVisibility(View.VISIBLE);
        }
        updateAdapter();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}