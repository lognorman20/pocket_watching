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
            mergeSort(0, tokens.size() - 1);
            reverseList(tokens);
        }

        adapter.notifyDataSetChanged();
    }

    /*
    UI Ideas
    2) Make sizing more uniform, apply to headings, sub headings, subtexts, body
    3) Txhistory shows less tx w/ a see more screen that shows more tx cuz rn the current view does
       not show very much anyways, show more information on the next screen in detail (tx hash,
       values, inputs & outputs, etc..)

    // focus on these
    1) Y axis w/ scaling
    4) Token distribution pie chart that is clickable and shows a more detailed breakdown w/ %s of
       each token. Further, it shows the token values for each token
    5) Users can add a profile picture or have a default set icon
    */

    private void mergeSort(int start, int end) {
        if ((start < end) && ((end - start) >= 1)) {
            int mid = (end + start) / 2;
            mergeSort(start, mid);
            mergeSort(mid + 1, end);

            merge(start, mid, end);
        }
    }

    private void merge(int start, int mid, int end) {
        List<Token> sortedArr = new ArrayList<>();
        int left = start;
        int right = mid + 1;

        Token leftToken;
        Token rightToken;
        while ((left <= mid) && (right <= end)) {
            leftToken = tokens.get(left);
            rightToken = tokens.get(right);

            Double leftBalance = leftToken.getTokenBalance();
            Double rightBalance = rightToken.getTokenBalance();

            if (leftBalance <= rightBalance) {
                sortedArr.add(leftToken);
                left++;
            } else {
                sortedArr.add(rightToken);
                right++;
            }
        }

        while (left <= mid) {
            leftToken = tokens.get(left);
            sortedArr.add(leftToken);
            left++;
        }

        while (right <= end) {
            rightToken = tokens.get(right);
            sortedArr.add(rightToken);
            right++;
        }

        int i = 0;
        int j = start;

        while (i < sortedArr.size()) {
            tokens.set(j, sortedArr.get(i));
            i++;
            j++;
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