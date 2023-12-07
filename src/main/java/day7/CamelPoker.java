package day7;

import input.AdventInput;

import java.util.*;
import java.util.stream.IntStream;

public class CamelPoker {

    public static void main(String[] args) {
        int day = 7;
        List<String> exampleInput1 = AdventInput.getInput(day, 1, true);
        System.out.println("Example 1: " + calcTotalWinnings(exampleInput1));

        List<String> taskInput1 = AdventInput.getInput(day, 1, false);
        System.out.println("Task 1: " + calcTotalWinnings(taskInput1));

        List<String> exampleInput2 = AdventInput.getInput(day, 2, true);
        System.out.println("Example 2: " + calcTotalWinningsWithJokers(exampleInput2));

        List<String> taskInput2 = AdventInput.getInput(day, 2, false);
        System.out.println("Task 2: " + calcTotalWinningsWithJokers(taskInput2));
    }

    public static long calcTotalWinnings(List<String> input) {
        var orderedHands = input.stream()
                .map(play -> play.split(" "))
                .map(play -> new CamelPokerHand(play[0], Long.parseLong(play[1])))
                .sorted((o1, o2) -> {
                    int v1, v2;
                    v1 = handToType(o1);
                    v2 = handToType(o2);
                    if (v1 == v2) {
                        return handCardComparison(o1, o2);
                    } else {
                        return v1 - v2;
                    }
                })
                .toList();

        return IntStream.range(0, orderedHands.size())
                .mapToObj(i -> orderedHands.get(i).bet() * (orderedHands.size() - i))
                .mapToLong(Long::longValue)
                .sum();
    }

    public static long calcTotalWinningsWithJokers(List<String> input) {
        var orderedHands = input.stream()
                .map(play -> play.split(" "))
                .map(play -> new CamelPokerHand(play[0], Long.parseLong(play[1])))
                .sorted((o1, o2) -> {
                    int v1, v2;
                    v1 = handToTypeWithJokers(o1);
                    v2 = handToTypeWithJokers(o2);
                    if (v1 == v2) {
                        return handCardComparisonWithJokers(o1, o2);
                    } else {
                        return v1 - v2;
                    }
                })
                .toList();

        return IntStream.range(0, orderedHands.size())
                .mapToObj(i -> orderedHands.get(i).bet() * (orderedHands.size() - i))
                .mapToLong(Long::longValue)
                .sum();
    }

    public static int handToType(CamelPokerHand hand) {
        Map<String, Integer> cards = new HashMap<>();

        for (String card : hand.hand().split("")) {
            cards.put(card, cards.getOrDefault(card, 0) + 1);
        }

        if (cards.size() == 1) {
            return 1;
        }

        if (cards.size() == 2) {
            if (cards.containsValue(4)) {
                return 2;
            } else {
                return 3;
            }
        }

        if (cards.size() == 3) {
            if (cards.containsValue(3)) {
                return 4;
            } else {
                return 5;
            }
        }

        if (cards.size() == 4) {
            return 6;
        }

        return 7;
    }

    public static int handToTypeWithJokers(CamelPokerHand hand) {
        Map<String, Integer> cards = new HashMap<>();

        for (String card : hand.hand().split("")) {
            cards.put(card, cards.getOrDefault(card, 0) + 1);
        }

        Integer jokers = cards.remove("J");

        if (jokers != null) {
            if (jokers == 5) {
                cards.put("J", 5);
            } else {
                String mostCards = null;

                for (String card : cards.keySet()) {
                    if (mostCards == null || cards.get(mostCards) < cards.get(card)) {
                        mostCards = card;
                    }
                }
                cards.put(mostCards, cards.get(mostCards) + jokers);

            }
        }


        if (cards.size() == 1) {
            return 1;
        }

        if (cards.size() == 2) {
            if (cards.containsValue(4)) {
                return 2;
            } else {
                return 3;
            }
        }

        if (cards.size() == 3) {
            if (cards.containsValue(3)) {
                return 4;
            } else {
                return 5;
            }
        }

        if (cards.size() == 4) {
            return 6;
        }

        return 7;
    }

    public static int handCardComparison(CamelPokerHand c1, CamelPokerHand c2) {
        Map<String, Integer> cardValues = new HashMap<>();
        cardValues.put("A", 1);
        cardValues.put("K", 2);
        cardValues.put("Q", 3);
        cardValues.put("J", 4);
        cardValues.put("T", 5);
        cardValues.put("9", 6);
        cardValues.put("8", 7);
        cardValues.put("7", 8);
        cardValues.put("6", 9);
        cardValues.put("5", 10);
        cardValues.put("4", 11);
        cardValues.put("3", 12);
        cardValues.put("2", 13);

        var hand1Values = Arrays.stream(c1.hand().split(""))
                .map(cardValues::get)
                .toList();

        var hand2Values = Arrays.stream(c2.hand().split(""))
                .map(cardValues::get)
                .toList();


        for (int i = 0; i < hand1Values.size(); i++) {
            if (!Objects.equals(hand1Values.get(i), hand2Values.get(i))) {
                return hand1Values.get(i) - hand2Values.get(i);
            }
        }

        return 0;
    }

    public static int handCardComparisonWithJokers(CamelPokerHand c1, CamelPokerHand c2) {
        Map<String, Integer> cardValues = new HashMap<>();
        cardValues.put("A", 1);
        cardValues.put("K", 2);
        cardValues.put("Q", 3);
        cardValues.put("J", 15);
        cardValues.put("T", 5);
        cardValues.put("9", 6);
        cardValues.put("8", 7);
        cardValues.put("7", 8);
        cardValues.put("6", 9);
        cardValues.put("5", 10);
        cardValues.put("4", 11);
        cardValues.put("3", 12);
        cardValues.put("2", 13);

        var hand1Values = Arrays.stream(c1.hand().split(""))
                .map(cardValues::get)
                .toList();

        var hand2Values = Arrays.stream(c2.hand().split(""))
                .map(cardValues::get)
                .toList();


        for (int i = 0; i < hand1Values.size(); i++) {
            if (!Objects.equals(hand1Values.get(i), hand2Values.get(i))) {
                return hand1Values.get(i) - hand2Values.get(i);
            }
        }

        return 0;
    }
}
