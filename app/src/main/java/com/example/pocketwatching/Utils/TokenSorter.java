package com.example.pocketwatching.Utils;

import com.example.pocketwatching.Models.Ethplorer.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class TokenSorter {
    private String sortType;
    private Boolean descending;
    private List<Token> tokens;
    private String query;
    private Comparator<Token> comparator;

    public TokenSorter(List<Token> tokens, Boolean descending) {
        this.tokens = tokens;
        this.descending = descending;
    }

    public void sort(String sort, Boolean descending) {
        int start = 0;
        int end = tokens.size() - 1;

        this.sortType = sort;
        this.descending = descending;

        getComparator();
        mergeSort(start, end);

        if (this.descending) {
            reverseList(tokens);
        }
    }

    public void sort(String sort, String query) {
        int start = 0;
        int end = tokens.size() - 1;

        this.sortType = sort;
        this.descending = false;
        this.query = query;

        runSort(start, end);

        if (this.descending) {
            reverseList(tokens);
        }
    }

    private void runSort(int start, int end) {
        if ((start < end) && ((end - start) >= 1)) {
            int mid = (end + start) / 2;
            runSort(start, mid);
            runSort(mid + 1, end);

            searchSort(start, mid, end);
        }
    }

    private void searchSort(int start, int mid, int end) {
        List<Token> sortedArr = new ArrayList<>();
        int l = start;
        int r = mid + 1;

        Token leftToken;
        Token rightToken;
        while ((l <= mid) && (r <= end)) {
            leftToken = tokens.get(l);
            rightToken = tokens.get(r);

            String leftSymbol = leftToken.getTokenInfo().getSymbol().toLowerCase(Locale.ROOT).trim();
            String rightSymbol = rightToken.getTokenInfo().getSymbol().toLowerCase(Locale.ROOT).trim();

            String leftName = leftToken.getTokenInfo().getName().toLowerCase(Locale.ROOT).trim();
            String rightName = rightToken.getTokenInfo().getName().toLowerCase(Locale.ROOT).trim();

            int leftSymbolDistance = minDistance(query, leftSymbol);
            int rightSymbolDistance = minDistance(query, rightSymbol);

            int leftNameDistance = minDistance(query, leftName);
            int rightNameDistance = minDistance(query, rightName);

            int left = Math.min(leftSymbolDistance, leftNameDistance);
            int right = Math.min(rightSymbolDistance, rightNameDistance);

            if (left < right) {
                sortedArr.add(leftToken);
                l++;
            } else {
                sortedArr.add(rightToken);
                r++;
            }
        }

        while (l <= mid) {
            leftToken = tokens.get(l);
            sortedArr.add(leftToken);
            l++;
        }

        while (r <= end) {
            rightToken = tokens.get(r);
            sortedArr.add(rightToken);
            r++;
        }

        int i = 0;
        int j = start;

        while (i < sortedArr.size()) {
            tokens.set(j, sortedArr.get(i));
            i++;
            j++;
        }
    }

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
        int l = start;
        int r = mid + 1;

        Token leftToken;
        Token rightToken;
        while ((l <= mid) && (r <= end)) {
            leftToken = tokens.get(l);
            rightToken = tokens.get(r);

            if (comparator.compare(leftToken, rightToken) < 0) {
                sortedArr.add(leftToken);
                l++;
            } else {
                sortedArr.add(rightToken);
                r++;
            }
        }

        while (l <= mid) {
            leftToken = tokens.get(l);
            sortedArr.add(leftToken);
            l++;
        }

        while (r <= end) {
            rightToken = tokens.get(r);
            sortedArr.add(rightToken);
            r++;
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

    private int minDistance(String word1, String word2) {
        int m = word1.length()-1;
        int n = word2.length()-1;
        int[][] dp = new int[m+2][n+2];
        for (int[] d: dp) {
            Arrays.fill(d, -1);
        }
        return distHelper(word1, word2, m, n, dp);
    }

    private int distHelper(String word1, String word2, int m, int n, int[][] dp) {
        // the strings are null
        if (m + 1 == 0 && n + 1 == 0) {
            return 0;
        }
        // one of the strings are null
        if (m + 1 == 0 || n + 1 == 0) {
            return Math.max(m + 1, n + 1);
        }
        // both values at the index are equal
        if (dp[m][n] != -1) {
            return dp[m][n];
        }

        if (word1.charAt(m) == word2.charAt(n)) {
            dp[m][n] = distHelper(word1, word2, m - 1, n - 1, dp);
        } else {
            // try deletion
            int delete = 1  + distHelper(word1, word2, m - 1, n, dp);
            // try insertion
            int insert = 1 + distHelper(word1, word2, m, n - 1, dp);
            // try replacing
            int replace = 1 + distHelper(word1, word2, m - 1, n - 1, dp);
            // choose min from the three and add one
            dp[m][n] = Math.min(Math.min(delete, insert), replace);
        }

        return dp[m][n];
    }

    private void getComparator() {
        switch (sortType) {
            case "Percent Change (24h)":
                comparator = new Token.CompPctChange();
                break;
            case "Market Price":
                comparator = new Token.CompMarketPrice();
                break;
            case "Amount Held":
                comparator = new Token.CompAmount();
                break;
            case "Market Cap":
                comparator = new Token.CompMarketCap();
                break;
            case "Circulating Supply":
                comparator = new Token.CompCircSupply();
                break;
            case "Volume (24h)":
                comparator = new Token.CompVolume();
                break;
            case "Name":
                comparator = new Token.CompName();
                break;
            case "Symbol":
                comparator = new Token.CompSymbol();
                break;
            default:
                comparator = new Token.CompBalance();
        }
    }
}