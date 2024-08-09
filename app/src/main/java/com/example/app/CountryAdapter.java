package com.example.app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.database.Countries;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(CountryItem imageItem);
    }

    private final List<CountryItem> list;
    private final OnItemClickListener listener;
    private final SQLiteDatabase database;

    public CountryAdapter(SQLiteDatabase database, List<CountryItem> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
        this.database = database;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<CountryItem> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageviewlist, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CountryItem item = list.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;
        private final CheckBox checkBox;


        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            imageView = itemView.findViewById(R.id.image);
            checkBox = itemView.findViewById(R.id.FavBut);
        }

        public void bind(CountryItem item, @NonNull OnItemClickListener listener) {
            imageView.setImageResource(item.getImage());
            textView.setText(item.getName());
            itemView.setOnClickListener(v -> listener.onItemClick(item));
            checkBox.setChecked(item.isFavorite());
            checkBox.setOnClickListener(v -> {
                        if (item.isFavorite()) {
                            item.setFavorite(false);
                            checkBox.setChecked(false);
                            Toast.makeText(v.getContext(), "Removed from favorites!", Toast.LENGTH_SHORT).show();
                        } else {
                            item.setFavorite(true);
                            checkBox.setChecked(true);
                            Toast.makeText(v.getContext(), "Added to favorites!", Toast.LENGTH_SHORT).show();
                        }
                        update(item);
                    }
            );
        }
    }

    private void update(CountryItem a) {
        ContentValues values = getContentValues(a);
        String selection = Countries.Table.Cols.id + " = ?";
        String[] selectionArgs = {a.getId().toString()};
        database.update(Countries.Table.name, values, selection, selectionArgs);
    }

    private ContentValues getContentValues(CountryItem item) {
        ContentValues values = new ContentValues();
        values.put(Countries.Table.Cols.id, item.getId().toString());
        values.put(Countries.Table.Cols.country_name, item.getName());
        values.put(Countries.Table.Cols.continents, item.getContinent());
        values.put(Countries.Table.Cols.isFav, item.isFavorite() ? 1 : 0);
        values.put(Countries.Table.Cols.photo, item.getImage());
        return values;
    }
}
