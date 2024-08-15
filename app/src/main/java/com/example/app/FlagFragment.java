package com.example.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app.databinding.FragmentFlagBinding;

public class FlagFragment extends Fragment implements PhotoAdapter.OnItemClickListener {
    FragmentFlagBinding binding;
    PhotoAdapter.OnItemClickListener onItemClickListener;
    private final @DrawableRes int[] photoResIds = new int[]{
            R.drawable.usa,
            R.drawable.bra,
            R.drawable.china,
            R.drawable.canada,
            R.drawable.france,
            R.drawable.germany,
            R.drawable.india,
            R.drawable.nigeria,
            R.drawable.kenya,
            R.drawable.japan,
            R.drawable.spain,
            R.drawable.south
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFlagBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.recView.setLayoutManager(layoutManager);
        onItemClickListener = this;
        PhotoAdapter adapter = new PhotoAdapter(photoResIds, onItemClickListener);
        binding.recView.setAdapter(adapter);
    }
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("flag",photoResIds[position]);
        getParentFragmentManager().setFragmentResult("flag_key",bundle);
        getParentFragmentManager().popBackStack();
    }
}