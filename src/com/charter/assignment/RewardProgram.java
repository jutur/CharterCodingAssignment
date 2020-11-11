package com.charter.assignment;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


/**
 *  Reward calculator to calculate reward points earned for each customer per month and total
 */
public class RewardProgram {
    public static void main(String[] args) {
        Map<String, Map<Month, List<Integer>>> customerMap = new HashMap<>();

        // Data Set for customer A and B
        String customerA = "Mark";
        String customerB = "Johnson";

        // Customer A transactions
        List<Integer> customerAJanTransactions = new ArrayList<>(Arrays.asList(60, 80));
        List<Integer> customerAFebTransactions = new ArrayList<>(Arrays.asList(100, 80));
        List<Integer> customerAMarTransactions = new ArrayList<>(Arrays.asList(60, 120));


        // Customer B transactions
        List<Integer> customerBJanTransactions = new ArrayList<>(Arrays.asList(70, 80));
        List<Integer> customerBFebTransactions = new ArrayList<>(Arrays.asList(120, 80));
        List<Integer> customerBMarTransactions = new ArrayList<>(Arrays.asList(80, 120));


        Map<Month, List<Integer>> customerATransactions = new HashMap<>();
        customerATransactions.put(Month.JAN, customerAJanTransactions);
        customerATransactions.put(Month.FEB, customerAFebTransactions);
        customerATransactions.put(Month.MAR, customerAMarTransactions);

        Map<Month, List<Integer>> customerBTransactions = new HashMap<>();
        customerBTransactions.put(Month.APR, customerBJanTransactions);
        customerBTransactions.put(Month.MAY, customerBFebTransactions);
        customerBTransactions.put(Month.JUN, customerBMarTransactions);


        customerMap.put(customerA, customerATransactions);
        customerMap.put(customerB, customerBTransactions);


        for (Map.Entry<String, Map<Month, List<Integer>>> entry : customerMap.entrySet()) {

            String customerName = entry.getKey();
            Map<Month, List<Integer>> customerTransactionMap = entry.getValue();
            List<Map<Month, Integer>> rewardsPerMonth = calculateRewards(customerTransactionMap);

            rewardsPerMonth.stream().forEach(e -> e.entrySet().stream().forEach(x ->
                            System.out.println("The Reward Points earned for " +
                                    customerName + " for the month of " + x.getKey() + " are "
                                    + x.getValue())
                    )
            );

            AtomicReference<Integer> totalReward = new AtomicReference<>(0);
            rewardsPerMonth.stream().forEach(
                    e -> e.entrySet().stream().forEach(x ->
                            totalReward.updateAndGet(v -> v + x.getValue())
                    )
            );

            System.out.println("Total Reward earned by " + customerName + " is " + totalReward);

        }


    }


    /**
     * Helper method to calculate reward points for each months transactions
     * @param transactionMap
     * @return rewardPerMonth
     */
    public static List<Map<Month, Integer>> calculateRewards(Map<Month, List<Integer>> transactionMap) {

        List<Map<Month, Integer>> rewardPerMonth = new ArrayList<>();

        for (Map.Entry<Month, List<Integer>> entry : transactionMap.entrySet()) {
            Integer point = 0;
            Month month = entry.getKey();
            List<Integer> transactions = entry.getValue();

            for (int i = 0; i < transactions.size(); i++) {
                Integer transaction = transactions.get(i);
                if (transaction > 100) {
                    point += ((transaction - 100) * 2) + 50;
                } else if (transaction > 50) {
                    point += transaction - 50;
                }
            }

            Map<Month, Integer> totalPointsForMonth = new HashMap<>();
            totalPointsForMonth.put(month, point);
            rewardPerMonth.add(totalPointsForMonth);

        }

        return rewardPerMonth;

    }

}
