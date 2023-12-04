package day4;

import input.AdventInput;

import java.util.*;

public class ScratchCards {

    public static void main(String[] args) {
        List<String> exampleInput1 = AdventInput.getInput(4, 1, true);
        System.out.println("Example 1: " + calcScratchCardsWinningPoints(exampleInput1));

        List<String> taskInput1 = AdventInput.getInput(4, 1, false);
        System.out.println("Task 1: " + calcScratchCardsWinningPoints(taskInput1));

        List<String> exampleInput2 = AdventInput.getInput(4, 2, true);
        System.out.println("Example 2: " + calcScratchCardsTotalCount(exampleInput2));

        List<String> taskInput2 = AdventInput.getInput(4, 2, false);
        System.out.println("Task 2: " + calcScratchCardsTotalCount(taskInput2));
    }

    public static int calcScratchCardsWinningPoints(List<String> input) {
        var count = input.get(0).split(":")[1].length() / 3;

        return input.stream()
                .map(c -> Arrays.stream(c.split(":")[1].replace("| ", "").trim().split(" "))
                        .filter(s -> !s.isEmpty())
                        .map(Integer::parseInt)
                        .toList())
                .map(HashSet::new)
                .map(hs -> count - hs.size())
                .map(winners -> Math.pow(2, winners - 1))
                .mapToInt(d -> (int) d.doubleValue())
                .sum();
    }

    public static int calcScratchCardsTotalCount(List<String> input) {
        Map<Integer, Integer> scratchCardCount = new HashMap<>();
        for (int i = 0; i < input.size(); i++) {
            scratchCardCount.put(i, 1);
        }

        var count = input.get(0).split(":")[1].length() / 3;

        for (int cardIndex = 0; cardIndex < input.size(); cardIndex++) {
            String card = input.get(cardIndex);

            int winnerAmount = count - new HashSet<>(Arrays.stream(card.split(":")[1].replace("| ", "").trim().split(" ")).filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .toList()).size();

            for (int i = cardIndex+1; i < cardIndex+1+winnerAmount; i++) {
                scratchCardCount.put(i, scratchCardCount.get(i) + scratchCardCount.get(cardIndex));
            }
        }

        return scratchCardCount.values().stream().mapToInt(Integer::intValue).sum();
    }
}
