package com.example.pocketwatching.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Token;
import com.example.pocketwatching.R;

import java.util.ArrayList;
import java.util.List;

public class SortingFragment extends Fragment {
    private Spinner spinner_sort_types;
    private String sort;
    private ArrayList<Token> tokens;
    public SortingFragment() {
    }

    // Inflate the layout for this fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sorting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner_sort_types = (Spinner) view.findViewById(R.id.spinner_sort_types);
        tokens = (ArrayList<Token>) getArguments().getSerializable("tokens");

        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(getContext(), R.array.sorts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_sort_types.setAdapter(adapter);

        if ((tokens == null) || (tokens.size() == 0)) {
            Toast.makeText(getContext(), "No tokens available to view.", Toast.LENGTH_SHORT).show();
            return;
        }

        spinner_sort_types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sort = parent.getSelectedItem().toString();
                processSort();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void processSort() {

    }

    /*
    Things to sort tokens by:
    1) USD Price
    2) User amount of token
    3) User $ value of token
    4) Name
    5) Total supply
    6) Market cap
    7) holdersCount
    8) Issuances Count
    9) diff, diff7d, diff30d
    10) Available supply
    11) volume24h
     */
}
