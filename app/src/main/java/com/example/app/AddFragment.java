package com.example.app;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.app.databinding.FragmentAddBinding;
import com.example.app.utils.BitmapUtils;

import java.util.UUID;


public class AddFragment extends Fragment {
    private FragmentAddBinding binding;


    private static CountryItem countryItem = new CountryItem(UUID.randomUUID());
    private String newName;

    public static DbViewModel dbViewModel;

    public static AddFragment newInstance(String param1) {
        AddFragment fragment = new AddFragment();
        countryItem.setContinent(param1);
        Bundle args = new Bundle();
        args.putString("con", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbViewModel = new ViewModelProvider(requireActivity()).get(DbViewModel.class);

        countryItem = new CountryItem(UUID.randomUUID());
        binding.flagButton.setOnClickListener(v -> {
            FragmentTransaction tr = getFragmentManager().beginTransaction();
            tr.add(R.id.fragment_container, new FlagFragment());
            tr.addToBackStack(null);
            tr.commit();
        });


        if (getFragmentManager() != null) {
            getFragmentManager().setFragmentResultListener("flag_key", getViewLifecycleOwner(), (requestKey, result) -> {
                if (requestKey.equals("flag_key")) {
                    countryItem.setImage(result.getInt("flag"));
                    binding.flagButton.setImageDrawable(BitmapUtils.resizeDrawable(requireContext(), getDrawable(requireContext(), result.getInt("flag")), 100, 70));
                }
            });
        }
        binding.name.addTextChangedListener(new TextWatcher() {
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
        binding.favoriteButton.setOnClickListener(v -> {
            if (countryItem.isFavorite()) {
                countryItem.setFavorite(false);
                binding.favoriteButton.setChecked(false);
                Toast.makeText(getContext(), R.string.delFromFav, Toast.LENGTH_SHORT).show();
            } else {
                countryItem.setFavorite(true);
                binding.favoriteButton.setChecked(true);
                Toast.makeText(getContext(), R.string.addToFav, Toast.LENGTH_SHORT).show();
            }
        });
        binding.saveButton.setOnClickListener(v -> {
            if (newName != null) {
                String changedName = newName.trim();
                if (!changedName.isEmpty()) {
                    countryItem.setName(newName);
                    countryItem.setContinent(getArguments().getString("con"));
                    dbViewModel.addToDb(requireContext(), countryItem);
                    dbViewModel.observable.notifyObservers();
                    getParentFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), R.string.hint, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), R.string.hint, Toast.LENGTH_SHORT).show();
            }
        });
    }


}






