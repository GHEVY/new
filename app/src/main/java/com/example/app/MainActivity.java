package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.databinding.ActivityMainBinding;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ContinentAdapter.OnItemClickListener {
    private final List<String> items = Arrays.asList("America", "Asia", "Europe", "Africa");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         ActivityMainBinding binding;

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recView.setLayoutManager(new GridLayoutManager(this, 2));

        binding.favBut.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, FavActivity.class);
            startActivity(i);
        });
        ContinentAdapter adapter = new ContinentAdapter(items,  this);
        binding.recView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent i;
        if (position == 0) {
            i = ContinentActivity.newIntent(getApplicationContext(), "AMERICA");
            startActivity(i);
        } else if (position == 1) {
            i = ContinentActivity.newIntent(getApplicationContext(),"ASIA");
            startActivity(i);
        } else if (position == 2) {
            i = ContinentActivity.newIntent(getApplicationContext(),"EUROPE");
            startActivity(i);
        } else if (position == 3) {
            i = ContinentActivity.newIntent(getApplicationContext(),"AFRICA");
            startActivity(i);
        }
    }

}