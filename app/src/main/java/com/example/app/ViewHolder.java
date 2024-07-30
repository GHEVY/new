package com.example.app;
import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import  android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView imageView;
    TextView textView;




    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        imageView = itemView.findViewById(R.id.imageview);
        textView = itemView.findViewById(R.id.imagetext);
;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        Toast.makeText(itemView.getContext(), textView.getText(), Toast.LENGTH_SHORT).show();
    }
}
