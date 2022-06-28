package com.example.pocketwatching.Etc;

import com.example.pocketwatching.Models.Ethplorer.Token;

import java.util.Comparator;

public class TokenAmountComparator implements Comparator<Token> {
    @Override
    public int compare(Token one, Token two) {
        if (one.getTokenAmount() < two.getTokenAmount()){
            return -1;
        } else if (one.getTokenAmount() > two.getTokenAmount()) {
            return 1;
        }
        return 0;
    }
}
