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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText text;
    ImageItem imageItem;
    public static final int requestAdd = 124;
    public static final int requestFlag = 1243;
    Button saveButton;
    String newText;
    CheckBox checkBox;
    ImageButton imageButton;

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, AddActivity.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        imageItem = new ImageItem();
        text = findViewById(R.id.edittext);
        saveButton = findViewById(R.id.but);
        imageButton = findViewById(R.id.imagebutton);
        imageButton.setOnClickListener(v -> {
            Intent i = new Intent(AddActivity.this, FlagActivity.class);
            startActivityForResult(i, requestFlag);
        });
        String continent = getIntent().getStringExtra("name");
        imageItem.setContinent(continent);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imageItem.setText(s.toString());
                newText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        checkBox = findViewById(R.id.checkbox);
        checkBox.setOnClickListener(v -> {
            if (imageItem.isFav) {
                imageItem.setFav(false);
                imageItem.delFav();
                checkBox.setChecked(false);
                Toast.makeText(getBaseContext(), "Removed from favorites!", Toast.LENGTH_SHORT).show();
            } else {
                imageItem.setFav(true);
                imageItem.addFav();
                checkBox.setChecked(true);
                Toast.makeText(getBaseContext(), "Added to favorites!", Toast.LENGTH_SHORT).show();
            }
        });
        saveButton.setOnClickListener(v -> {
            if(imageItem.getImage() == Integer.parseInt(null)){
                Toast.makeText(getBaseContext(), "Choose the flag of a country!", Toast.LENGTH_SHORT).show();
            }
            if (newText != null) {
                newText = newText.replace(" ", "");
                if (!newText.isEmpty()) {
                    Intent result = new Intent();
                    result.putExtra("key", imageItem);
                    setResult(requestAdd, result);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Write the name of a country!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "Write the name of a country!", Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if (req == requestFlag) {
            if (data != null) {
                imageItem.setImage(data.getIntExtra("req", 0));
                int a = data.getIntExtra("req", 0);
                @SuppressLint("UseCompatLoadingForDrawables") BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable(a);
                assert bitmapDrawable != null;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 60, true);
                Drawable scaledDrawable = new BitmapDrawable(getResources(), scaledBitmap);
                imageButton.setImageDrawable(scaledDrawable);
            }

        }
    }}
