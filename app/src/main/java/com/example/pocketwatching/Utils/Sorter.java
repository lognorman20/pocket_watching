package com.example.pocketwatching.Utils;

import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Price;
import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Sorter {
    private String sortType;
    private Boolean descending;
    private List<Token> tokens;
    private String query;

    public Sorter(List<Token> tokens, Boolean descending) {
        this.tokens = tokens;
        this.descending = descending;
    }

    public void sort(String sort, Boolean descending) {
        int start = 0;
        int end = tokens.size() - 1;

        this.sortType = sort;
        this.descending = descending;

        runSort(start, end);

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

            switch (sortType) {
                case "search":
                    searchSort(start, mid, end);
                    break;
                case "Percent Change (24h)":
                    pctSort(start, mid, end);
                    break;
                case "Market Price":
                    priceSort(start, mid, end);
                    break;
                case "Amount Held":
                    amountSort(start, mid, end);
                    break;
                case "Market Cap":
                    marketCapSort(start, mid, end);
                    break;
                case "Circulating Supply":
                    supplySort(start, mid, end);
                    break;
                case "Volume (24h)":
                    volumeSort(start, mid, end);
                    break;
                case "Name":
                    nameSort(start, mid, end);
                    break;
                case "Symbol":
                    symbolSort(start, mid, end);
                    break;
                default:
                    balanceSort(start, mid, end);
            }
        }
    }

    private void searchSort(int start, int mid, int end) {
        List<Token> sortedArr = new ArrayList<>();
        int l = start;
        int r = mid + 1;

        Token leftToken;
        Token rightToken;
        // TODO: Rewrite to modularize core sorting
        while ((l <= mid) && (r <= end)) {
            leftToken = tokens.get(l);
            rightToken = tokens.get(r);

            String leftSymbol = leftToken.getTokenInfo().getSymbol().toLowerCase(Locale.ROOT);
            String rightSymbol = rightToken.getTokenInfo().getSymbol().toLowerCase(Locale.ROOT);

            String leftName = leftToken.getTokenInfo().getName().toLowerCase(Locale.ROOT);
            String rightName = rightToken.getTokenInfo().getName().toLowerCase(Locale.ROOT);

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

    private void symbolSort(int start, int mid, int end) {
        List<Token> sortedArr = new ArrayList<>();
        int l = start;
        int r = mid + 1;

        Token leftToken;
        Token rightToken;
        while ((l <= mid) && (r <= end)) {
            leftToken = tokens.get(l);
            rightToken = tokens.get(r);

            String leftString = leftToken.getTokenInfo().getSymbol().toLowerCase(Locale.ROOT);
            String rightString = rightToken.getTokenInfo().getSymbol().toLowerCase(Locale.ROOT);

            int cmp = strcmp(leftString, rightString);

            if (cmp < 0) {
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

    private void nameSort(int start, int mid, int end) {
        List<Token> sortedArr = new ArrayList<>();
        int l = start;
        int r = mid + 1;

        Token leftToken;
        Token rightToken;
        while ((l <= mid) && (r <= end)) {
            leftToken = tokens.get(l);
            rightToken = tokens.get(r);

            String leftString = leftToken.getTokenInfo().getName().toLowerCase(Locale.ROOT);
            String rightString = rightToken.getTokenInfo().getName().toLowerCase(Locale.ROOT);

            int cmp = strcmp(leftString, rightString);

            if (cmp < 0) {
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

    private int strcmp(String leftString, String rightString) {
        int leftLen = leftString.length();
        int rightLen = rightString.length();
        int minLen = Math.min(leftLen, rightLen);

        for (int i = 0; i < minLen; i++) {
            int leftChar = (int) leftString.charAt(i);
            int rightChar = (int) rightString.charAt(i);

            if (leftChar != rightChar) {
                return leftChar - rightChar;
            }
        }

        if (leftLen != rightLen) {
            return leftLen - rightLen;
        } else {
            return 0;
        }
    }

    private void volumeSort(int start, int mid, int end) {
        List<Token> sortedArr = new ArrayList<>();
        int l = start;
        int r = mid + 1;

        Token leftToken;
        Token rightToken;
        while ((l <= mid) && (r <= end)) {
            leftToken = tokens.get(l);
            rightToken = tokens.get(r);

            Price leftPrice = (Price) leftToken.getTokenInfo().getPrice();
            Price rightPrice = (Price) rightToken.getTokenInfo().getPrice();

            Double left = leftPrice.getVolume24h();
            Double right = rightPrice.getVolume24h();

            if (left <= right) {
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

    private void supplySort(int start, int mid, int end) {
        List<Token> sortedArr = new ArrayList<>();
        int l = start;
        int r = mid + 1;

        Token leftToken;
        Token rightToken;
        while ((l <= mid) && (r <= end)) {
            leftToken = tokens.get(l);
            rightToken = tokens.get(r);

            Price leftPrice = (Price) leftToken.getTokenInfo().getPrice();
            Price rightPrice = (Price) rightToken.getTokenInfo().getPrice();

            Double left = leftPrice.getAvailableSupply();
            Double right = rightPrice.getAvailableSupply();

            if (left <= right) {
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

    private void marketCapSort(int start, int mid, int end) {
        List<Token> sortedArr = new ArrayList<>();
        int l = start;
        int r = mid + 1;

        Token leftToken;
        Token rightToken;
        while ((l <= mid) && (r <= end)) {
            leftToken = tokens.get(l);
            rightToken = tokens.get(r);

            Price leftPrice = (Price) leftToken.getTokenInfo().getPrice();
            Price rightPrice = (Price) rightToken.getTokenInfo().getPrice();

            Double left = leftPrice.getMarketCapUsd();
            Double right = rightPrice.getMarketCapUsd();

            if (left <= right) {
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

    private void amountSort(int start, int mid, int end) {
        List<Token> sortedArr = new ArrayList<>();
        int l = start;
        int r = mid + 1;

        Token leftToken;
        Token rightToken;
        while ((l <= mid) && (r <= end)) {
            leftToken = tokens.get(l);
            rightToken = tokens.get(r);

            Double left = leftToken.getAmount();
            Double right = rightToken.getAmount();

            if (left <= right) {
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

    private void pctSort(int start, int mid, int end) {
        List<Token> sortedArr = new ArrayList<>();
        int l = start;
        int r = mid + 1;

        Token leftToken;
        Token rightToken;
        while ((l <= mid) && (r <= end)) {
            leftToken = tokens.get(l);
            rightToken = tokens.get(r);

            Price leftPrice = (Price) leftToken.getTokenInfo().getPrice();
            Price rightPrice = (Price) rightToken.getTokenInfo().getPrice();

            Double left = leftPrice.getDiff();
            Double right = rightPrice.getDiff();

            if (left <= right) {
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


    private void priceSort(int start, int mid, int end) {
        List<Token> sortedArr = new ArrayList<>();
        int l = start;
        int r = mid + 1;

        Token leftToken;
        Token rightToken;
        while ((l <= mid) && (r <= end)) {
            leftToken = tokens.get(l);
            rightToken = tokens.get(r);

            Price leftPrice = (Price) leftToken.getTokenInfo().getPrice();
            Price rightPrice = (Price) rightToken.getTokenInfo().getPrice();

            Double left = leftPrice.getRate();
            Double right = rightPrice.getRate();

            if (left <= right) {
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

    private void balanceSort(int start, int mid, int end) {
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
}