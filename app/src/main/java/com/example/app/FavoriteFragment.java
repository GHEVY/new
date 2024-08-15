package com.example.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app.databinding.FragmentFavoriteBinding;

public class FavoriteFragment extends Fragment implements CountryAdapter.OnItemClickListener {

    private DbViewModel dbViewModel;

    public CountryAdapter adapter;
    private FragmentFavoriteBinding binding;

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(getLayoutInflater());
        return (binding.getRoot());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.main.setOnRefreshListener(() -> {
            updateAdapter();
            updateVisibility();
            binding.main.setRefreshing(false);
        });
        dbViewModel = new ViewModelProvider(requireActivity()).get(DbViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new CountryAdapter(dbViewModel.getFavoriteItems(requireContext()), this);
        binding.recyclerView.setAdapter(adapter);
        updateVisibility();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAdapter();
        updateVisibility();
    }

    @Override
    public void onItemClick(CountryItem imageItem) {
        FragmentTransaction tr = getParentFragmentManager().beginTransaction();
        CountryFragment frag = CountryFragment.newInstance(imageItem.getId().toString());
        tr.replace(R.id.fragment_container, frag);
        tr.addToBackStack(null);
        tr.commit();
    }

    @Override
    public void itemFavoriteChanged(CountryItem data) {
        dbViewModel.update(requireContext(), data);
    }


    @SuppressLint("NotifyDataSetChanged")
    private void updateAdapter() {
        adapter.setData(dbViewModel.getFavoriteItems(requireContext()));
    }

    private void updateVisibility() {
        if (dbViewModel.getFavoriteItems(requireContext()).isEmpty()) {
            binding.note.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.INVISIBLE);
        } else {
            binding.note.setVisibility(View.INVISIBLE);
            binding.recyclerView.setVisibility(View.VISIBLE);
        }
    }
}

