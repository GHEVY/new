package com.example.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.app.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ContinentAdapter.OnItemClickListener {

    private final ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        items.add(getString(R.string.asia));
        items.add(getString(R.string.europe));
        items.add(getString(R.string.africa));
        items.add(getString(R.string.america));
        setContentView(binding.getRoot());
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.favBut.setOnClickListener(v -> {
            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
            tr.add(R.id.fragment_container,FavoriteFragment.newInstance());
            tr.addToBackStack(null);
            tr.commit();
        });
        ContinentAdapter adapter = new ContinentAdapter(items, this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(String name) {
            ContinentFragment frag = ContinentFragment.newInstance(name);
            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
            tr.add(R.id.fragment_container,frag);
            tr.addToBackStack(name);
            tr.commit();
    }

}