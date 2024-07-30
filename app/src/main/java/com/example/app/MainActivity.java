package com.example.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ArrayList<String> items = new ArrayList<>();
        items.add("America");
        items.add("Asia");
        items.add("Europe");
        items.add("Africa");
        MyAdapter adapter = new MyAdapter(items, this, this);
        recyclerView.setAdapter(new MyAdapter(items, this, this));
    }

    @Override
    public void onItemClick(int position) {
        Intent i;
        if (position == 0) {
            i = AmericaActivity.newIntent(MainActivity.this);
            startActivity(i);
        } else if (position == 1) {
            i = AsiaActivity.newIntent(MainActivity.this);
            startActivity(i);
        } else if (position == 2) {
            i = EuropeActivity.newIntent(MainActivity.this);
            startActivity(i);
        } else if (position == 3) {
            i = AfricaActivity.newIntent(MainActivity.this);
            startActivity(i);
        }

    }
}