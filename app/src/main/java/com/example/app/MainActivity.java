package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    Button favorites;


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
        favorites = findViewById(R.id.favBut);
        favorites.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, FavActivity.class);
            startActivity(i);
        });
        MyAdapter adapter = new MyAdapter(items, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnCreate(Bundle savedInstanceState) {

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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}