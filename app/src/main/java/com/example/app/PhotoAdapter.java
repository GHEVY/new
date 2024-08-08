package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private final Context context;
    private final int[] photoResIds;
    private final ContinentAdapter.OnItemClickListener listener;

    public PhotoAdapter(Context context, int[] photoResIds, ContinentAdapter.OnItemClickListener listener) {
        this.context = context;
        this.photoResIds = photoResIds;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flag, parent, false);
        return new PhotoViewHolder(view, listener);
    }
    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        int resId = photoResIds[position];
        holder.imageView.setImageResource(resId);
    }


    @Override
    public int getItemCount() {
        return photoResIds.length;
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public PhotoViewHolder(@NonNull View itemView, ContinentAdapter.OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int positions = getAdapterPosition();
                    if (positions != RecyclerView.NO_POSITION) {
                        listener.onItemClick(positions);
                    }
                }
            });
        }
    }

}

