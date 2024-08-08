package com.example.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public  class ContinentAdapter extends RecyclerView.Adapter<ContinentAdapter.ViewHolder> {
    private final List<String> itemList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ContinentAdapter(List<String> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String data = itemList.get(position);
        holder.textView.setText(data);
    }

    public int getItemCount() {
        return itemList.size();
    }
}