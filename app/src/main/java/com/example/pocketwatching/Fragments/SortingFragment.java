package com.example.pocketwatching.Fragments;

import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketwatching.Adapters.TokenAdapter;
import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Token;
import com.example.pocketwatching.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SortingFragment extends Fragment {
    private Spinner spinner_sort_types;
    private RecyclerView rvTokens;
    private String sort;
    private ArrayList<Token> tokens;

    private TokenAdapter adapter;

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

        rvTokens = view.findViewById(R.id.rvTokens);
        spinner_sort_types = (Spinner) view.findViewById(R.id.spinner_sort_types);
        tokens = (ArrayList<Token>) getArguments().getSerializable("tokens");

        adapter = new TokenAdapter(getContext(), tokens);
        rvTokens.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTokens.setAdapter(adapter);

        ArrayAdapter<CharSequence> arrayAdapter= ArrayAdapter.createFromResource(getContext(), R.array.sorts, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_sort_types.setAdapter(arrayAdapter);

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
        if (sort.toLowerCase(Locale.ROOT).equals("name")
                || sort.toLowerCase(Locale.ROOT).equals("symbol")) {
            Toast.makeText(getContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Sorting!", Toast.LENGTH_SHORT).show();
            balanceSort(tokens);
        }

        // to get tokens in decreasing order
        reverseList(tokens);
        adapter.notifyDataSetChanged();
    }

    private void balanceSort(List<Token> input) {
        if (input.size() > 1) {
            int mid = input.size() / 2;
            List<Token> left = input.subList(0, mid);
            List<Token> right = input.subList(mid, input.size());

            balanceSort(left);
            balanceSort(right);

            int i = 0;
            int j = 0;
            int k = 0;

            Token leftToken = left.get(i);
            Token rightToken = right.get(j);

            while ((i < left.size()) && (j < right.size())) {
                Double leftBalance = leftToken.getTokenBalance();
                Double rightBalance = rightToken.getTokenBalance();

                if (leftBalance < rightBalance) {
                    input.set(k, leftToken);
                    i += 1;
                } else {
                    input.set(k, rightToken);
                    j += 1;
                }

                k += 1;
            }

            while (i < left.size()) {
                input.set(k, leftToken);
                i += 1;
                k += 1;
            }

            while (j < right.size()) {
                input.set(k, rightToken);
                j += 1;
                k += 1;
            }
        }
    }

    private void reverseList(List<Token> input) {
        int start = 0;
        int end = input.size() - 1;

        while (start < end) {
            Collections.swap(input, start, end);
            start += 1;
            end -= 1;
        }
    }
}