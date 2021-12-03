package com.michaelburgstaller.adventofcode.reportrepair;

import com.michaelburgstaller.adventofcode.Exercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportRepair extends Exercise {

    private static void recursivelyFindSummands(List<Integer> expenses, Integer targetSum, List<Integer> result, List<Integer> fixedSummands, Integer startIndex, Integer remainingLayers) {
        var currentSum = fixedSummands.stream().reduce(0, Integer::sum);
        if (remainingLayers == 0) {
            if (currentSum.compareTo(targetSum) == 0) {
                result.addAll(fixedSummands);
            }
        } else if (currentSum < targetSum) {
            for (var i = startIndex; i < expenses.size() - (remainingLayers - 1); i++) {
                var summands = new ArrayList<>(fixedSummands);
                summands.add(expenses.get(i));
                recursivelyFindSummands(expenses, targetSum, result, summands, i + 1, remainingLayers - 1);
            }
        }
    }

    private static List<Integer> findSummands(List<Integer> expenses, Integer amount, Integer targetSum) {
        List<Integer> result = new ArrayList<>();
        recursivelyFindSummands(expenses, targetSum, result, Collections.emptyList(), 0, amount);
        return result;
    }

    private static void findTwoExpensesThatAddTo2020(List<Integer> expenses) {
        var twoSummands = findSummands(expenses, 2, 2020);
        var twoSummandsMultiplied = twoSummands.stream().reduce(1, (lhs, rhs) -> lhs * rhs);
        System.out.println("The two expenses are " + twoSummands + " with a result of '" + twoSummandsMultiplied + "'");
    }

    private static void findThreeExpensesThatAddTo2020(List<Integer> expenses) {
        var threeSummands = findSummands(expenses, 3, 2020);
        var threeSummandsMultiplied = threeSummands.stream().reduce(1, (lhs, rhs) -> lhs * rhs);
        System.out.println("The three expenses are " + threeSummands + " with a result of '" + threeSummandsMultiplied + "'");
    }

    public static void main(String[] args) {
        var expenses = getLineStream().map(Integer::parseInt).toList();

        findTwoExpensesThatAddTo2020(expenses);
        findThreeExpensesThatAddTo2020(expenses);
    }
}
