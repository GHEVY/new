package com.example.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.app.databinding.FragmentCountryBinding;

import java.util.UUID;

public class CountryFragment extends Fragment {
    private FragmentCountryBinding binding;
    private CountryItem countryItem;
    private String newName;
    private DbViewModel dbViewModel;

    public static CountryFragment newInstance(String param1) {
        CountryFragment fragment = new CountryFragment();
        Bundle args = new Bundle();
        args.putString("ID_key", param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentCountryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbViewModel = new ViewModelProvider(requireActivity()).get(DbViewModel.class);
        UUID id = UUID.fromString(getArguments().getString("ID_key"));
        countryItem = dbViewModel.getItem(requireContext(), id);
        String txt = getString(R.string.cont) + " " + countryItem.getContinent();
        binding.info.setText(txt);
        binding.nameText.setText(countryItem.getName());
        binding.flagButton.setImageResource(countryItem.getImage());
        if (countryItem.isFavorite()) {
            binding.FavBut.setChecked(true);
        }
        binding.FavBut.setOnClickListener(v -> {
            if (countryItem.isFavorite()) {
                countryItem.setFavorite(false);
                binding.FavBut.setChecked(false);
                Toast.makeText(requireContext(), R.string.delFromFav, Toast.LENGTH_SHORT).show();
            } else {
                countryItem.setFavorite(true);
                binding.FavBut.setChecked(true);
                Toast.makeText(requireContext(), R.string.addToFav, Toast.LENGTH_SHORT).show();
            }
        });
        binding.link.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://chatgpt.com"));
            startActivity(browserIntent);
        });
        newName = countryItem.getName();
        binding.nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                newName = s.toString();
            }
        });
        binding.saveButton.setOnClickListener(v -> {
            if (newName != null) {
                String changedName = newName.replace(" ", "");
                if (!changedName.isEmpty()) {
                    countryItem.setName(newName);
                    dbViewModel.update(requireContext(), countryItem);
                    getParentFragmentManager().popBackStack();
                } else {
                    Toast.makeText(requireContext(), R.string.hint, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), R.string.hint, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

