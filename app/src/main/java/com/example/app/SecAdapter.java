package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SecAdapter extends RecyclerView.Adapter<SecAdapter.ViewHolder> {
    private final Context context;
    private final List<ImageItem> list;
    private final MyAdapter.OnItemClickListener listener;

    public SecAdapter(Context context, List<ImageItem> list, MyAdapter.OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.imageviewlist, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        ImageItem item = list.get(position);
        CheckBox checkBox = holder.itemView.findViewById(R.id.Favbut);
        if (item != null) {
            holder.bind(item);
        }
        assert item != null;
        if (item.isFav) {
            checkBox.setChecked(true);
        }
        checkBox.setOnClickListener(v -> {
            if (item.isFav) {
                item.setFav(false);
                item.delFav();
                checkBox.setChecked(false);
                Toast.makeText(v.getContext(), "Removed from favorites!", Toast.LENGTH_SHORT).show();
            } else {
                item.setFav(true);
                item.addFav();
                checkBox.setChecked(true);
                Toast.makeText(v.getContext(), "Added to favorites!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView, final MyAdapter.OnItemClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
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

        public void bind(ImageItem item) {
            imageView.setImageResource(item.getImage());
            textView.setText(item.getText());
        }
    }
}
