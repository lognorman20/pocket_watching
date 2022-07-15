package com.example.pocketwatching.Etc;

import android.util.Log;

import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Price;
import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TokenSorter {
    private String type;
    private Boolean descending;
    private List<Token> tokens;

    public TokenSorter(List<Token> tokens, String type, Boolean descending) {
        this.tokens = tokens;
        this.type = type;
        this.descending = descending;
    }

    public void sort() {
        int start = 0;
        int end = tokens.size() - 1;

        // add alpha sort case here
        if ((type.equalsIgnoreCase("name"))
                || (type.equalsIgnoreCase("symbol"))) {

        }

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

            switch (type) {
                case "Balance":
                    balanceSort(start, mid, end);
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
                default:
                    balanceSort(start, mid, end);
            }
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
}
