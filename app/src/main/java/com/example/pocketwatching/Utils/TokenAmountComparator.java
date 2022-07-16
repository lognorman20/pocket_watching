package com.example.pocketwatching.Utils;

import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Token;

import java.util.Comparator;

public class TokenAmountComparator implements Comparator<Token> {
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
