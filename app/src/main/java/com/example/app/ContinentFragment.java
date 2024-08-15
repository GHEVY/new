package com.example.app;

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

import com.example.app.databinding.FragmentContinentBinding;

import java.util.Observable;
import java.util.Observer;

public class ContinentFragment extends Fragment implements CountryAdapter.OnItemClickListener {

    private static String continent;
    private static CountryAdapter adapter;
    private FragmentContinentBinding binding;
    private DbViewModel dbViewModel;
    private static final String CONTINENT_KEY = "continent";


    public static ContinentFragment newInstance(String param1) {
        ContinentFragment fragment = new ContinentFragment();
        Bundle args = new Bundle();
        args.putString(CONTINENT_KEY, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentContinentBinding.inflate(getLayoutInflater());
        return (binding.getRoot());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        continent = getArguments().getString(CONTINENT_KEY);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.addButton.setOnClickListener(v -> {
            AddFragment add = AddFragment.newInstance(continent);
            FragmentTransaction tr = getParentFragmentManager().beginTransaction();
            tr.replace(R.id.fragment_container, add);
            tr.addToBackStack(null);
            tr.commit();
        });
        dbViewModel = new ViewModelProvider(requireActivity()).get(DbViewModel.class);
        dbViewModel.observable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                updateAdapter();
            }
        });
        adapter = new CountryAdapter(dbViewModel.getItems(requireContext(),continent), this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAdapter();
    }

    public void updateAdapter() {
        adapter.setData(dbViewModel.getItems(requireContext(),continent));
    }

    @Override
    public void onItemClick(CountryItem item) {
        FragmentTransaction tr = getParentFragmentManager().beginTransaction();
        CountryFragment frag = CountryFragment.newInstance(item.getId().toString());
        tr.replace(R.id.fragment_container, frag);
        tr.addToBackStack(null);
        tr.commit();
    }

    @Override
    public void itemFavoriteChanged(CountryItem data) {
        dbViewModel.update(requireContext(), data);
    }
}
