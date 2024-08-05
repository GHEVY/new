package com.example.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class CountryActivity extends AppCompatActivity {
    private static final String extra = "extra";
    private static final int requestChange = 221;
    EditText editText;
    ImageView imageView;
    Button linkButton;
    CheckBox checkBox;
    ImageItem imageItem;
    Button saveButton;
    String newText;
    TextView info;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        editText = findViewById(R.id.imagetext);
        imageView = findViewById(R.id.imagebutton);
        imageItem = getIntent().getParcelableExtra(extra);
        String con = getIntent().getStringExtra("con");
        imageItem.setContinent(con);
        info = findViewById(R.id.info);
        info.setText("The continent of " +imageItem.getText() + " is " + imageItem.getContinent());
        assert imageItem != null;
        editText.setText(imageItem.getText());
        imageView.setImageResource(imageItem.getImage());
        linkButton = findViewById(R.id.link);
        checkBox = findViewById(R.id.Favbut);
        if (imageItem.isFav) {
            checkBox.setChecked(true);
        }
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

        linkButton.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://chatgpt.com"));
            startActivity(browserIntent);
        });
        newText = imageItem.getText();
        String oldtext = newText;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newText = s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                newText =s.toString();
            }
        });
        saveButton = findViewById(R.id.but);
        saveButton.setOnClickListener(v -> {
            if (newText != null) {
                String b = newText.replace(" ", "");
                if (!b.isEmpty()) {
                    if(Objects.equals(newText, oldtext)){
                        Intent result = new Intent();
                        result.putExtra("requestChange", imageItem);
                        setResult(requestChange, result);
                        finish();
                    }
                    imageItem.setText(newText);
                    Intent result = new Intent();
                    result.putExtra("requestChange", imageItem);
                    setResult(requestChange, result);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Write the name of a country!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "Write the name of a country!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

