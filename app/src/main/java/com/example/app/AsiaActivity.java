package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AsiaActivity extends AppCompatActivity {
    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, AsiaActivity.class);
    }

    ArrayList<ImageItem> list;
    RecyclerView recyclerView1;
    public Bundle args = new Bundle();
    public static final String result = "res";
    private static final String continent = "con";

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
        SecAdapter adapter = new SecAdapter(this, list);
        recyclerView1.setAdapter(adapter);
        Button add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            list.add(new ImageItem());
            updateAdapter();
        });

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

