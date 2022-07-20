package com.example.pocketwatching.Utils;

import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Operation;

import java.util.ArrayList;
import java.util.List;

public class OperationSorter {
    private List<Operation> operations;

    public OperationSorter(List<Operation> operations) {
        this.operations = operations;
    }

    public void sort() {
        int start = 0;
        int end = operations.size() - 1;

        runSort(start, end);
    }

    private void runSort(int start, int end) {
        if ((start < end) && ((end - start) >= 1)) {
            int mid = (end + start) / 2;
            runSort(start, mid);
            runSort(mid + 1, end);

            timeSort(start, mid, end);
        }
    }

    private void timeSort(int start, int mid, int end) {
        List<Operation> sortedArr = new ArrayList<>();
        int l = start;
        int r = mid + 1;

        Operation leftOperation;
        Operation rightOperation;
        // TODO: Rewrite to modularize core sorting
        while ((l <= mid) && (r <= end)) {
            leftOperation = operations.get(l);
            rightOperation = operations.get(r);

            Long left = leftOperation.getTimestamp();
            Long right = rightOperation.getTimestamp();

            if (left < right) {
                sortedArr.add(leftOperation);
                l++;
            } else {
                sortedArr.add(rightOperation);
                r++;
            }
        }

        while (l <= mid) {
            leftOperation = operations.get(l);
            sortedArr.add(leftOperation);
            l++;
        }

        while (r <= end) {
            rightOperation = operations.get(r);
            sortedArr.add(rightOperation);
            r++;
        }

        int i = 0;
        int j = start;

        while (i < sortedArr.size()) {
            operations.set(j, sortedArr.get(i));
            i++;
            j++;
        }
    }
}
