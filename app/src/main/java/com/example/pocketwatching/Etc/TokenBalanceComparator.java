package com.example.pocketwatching.Etc;

import com.example.pocketwatching.Models.Ethplorer.Token;

import java.util.Comparator;

public class TokenBalanceComparator implements Comparator<Token> {
    @Override
    public int compare(Token one, Token two) {
        if (one.getTokenBalance() < two.getTokenBalance()){
            return -1;
        } else if (one.getTokenBalance() > two.getTokenBalance()) {
            return 1;
        }
        return 0;
    }
}