package com.example.pocketwatching.Etc;

import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TokenSort {
    private String type;
    private Boolean descending;
    private List<Token> tokens;

    public TokenSort(List<Token> tokens, String type, Boolean descending) {
        this.tokens = tokens;
        this.type = type;
        this.descending = descending;
    }

    public void sort() {
        int start = 0;
        int end = tokens.size() - 1;

        // add alpha sort case
        numSort(start, end);

        if (descending == true) {
            reverseList(tokens);
        }
    }

    public void numSort(int start, int end) {
        if ((start < end) && ((end - start) >= 1)) {
            int mid = (end + start) / 2;
            numSort(start, mid);
            numSort(mid + 1, end);

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
