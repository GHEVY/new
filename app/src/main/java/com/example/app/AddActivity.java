package com.example.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.databinding.ActivityAddBinding;

import java.util.UUID;

public class AddActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD = 124;
    private static final int REQUEST_CODE_FLAG = 1243;

    private ImageItem imageItem;
    private String newText;
    private ActivityAddBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, AddActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        imageItem = new ImageItem(UUID.randomUUID());
        binding.imagebutton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), FlagActivity.class);
            startActivityForResult(i, REQUEST_CODE_FLAG);
        });
        binding.edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                newText = s.toString();
            }
        });
        binding.checkbox.setOnClickListener(v -> {
            if (imageItem.isFavorite()) {
                imageItem.setFavorite(false);
                binding.checkbox.setChecked(false);
                Toast.makeText(getBaseContext(), "Removed from favorites!", Toast.LENGTH_SHORT).show();
            } else {
                imageItem.setFavorite(true);
                binding.checkbox.setChecked(true);
                Toast.makeText(getBaseContext(), "Added to favorites!", Toast.LENGTH_SHORT).show();
            }

        });
        binding.but.setOnClickListener(v -> {
            if (newText != null) {
                String n = newText.replace(" ", "");
                if (!n.isEmpty()) {
                    setResult(REQUEST_CODE_ADD, createResultIntent());
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Write the name of a country!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "Write the name of a country!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Intent createResultIntent() {
        UUID id = imageItem.getId();
        imageItem.setId(id);
        imageItem.setText(newText);
        Intent intent = new Intent();
        intent.putExtra("id", id.toString());
        intent.putExtra("text", newText);
        intent.putExtra("isFav", imageItem.isFavorite()? 1 : 0);
        intent.putExtra("photo", imageItem.getImage());
        return intent;
    }

    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if (req == REQUEST_CODE_FLAG) {
            if (data != null) {
                imageItem.setImage(data.getIntExtra("req", 0));
                int a = data.getIntExtra("req", 0);
                @SuppressLint("UseCompatLoadingForDrawables") BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable(a);
                assert bitmapDrawable != null;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 60, true);
                Drawable scaledDrawable = new BitmapDrawable(getResources(), scaledBitmap);
                binding.imagebutton.setImageDrawable(scaledDrawable);
            }

        }
    }
}
