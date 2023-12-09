package day9;

import input.AdventInput;

import java.util.Arrays;
import java.util.List;

public class OasisPrediction {

    public static void main(String[] args) {
        int day = 9;
        List<String> exampleInput1 = AdventInput.getInput(day, 1, true);
        System.out.println("Example 1: " + calcExtrapolatedValues(exampleInput1));

        List<String> taskInput1 = AdventInput.getInput(day, 1, false);
        System.out.println("Task 1: " + calcExtrapolatedValues(taskInput1));

        List<String> exampleInput2 = AdventInput.getInput(day, 2, true);
        System.out.println("Example 2: " + calcExtrapolatedValuesAtBeginning(exampleInput2));

        List<String> taskInput2 = AdventInput.getInput(day, 2, false);
        System.out.println("Task 2: " + calcExtrapolatedValuesAtBeginning(taskInput2));
    }

    public static long calcExtrapolatedValues(List<String> input) {

        List<List<Long>> histories = input.stream()
                .map(l -> Arrays.stream(l.split(" "))
                        .mapToLong(Long::parseLong)
                        .boxed()
                        .toList())
                .toList();

        return histories.stream()
                .mapToLong(OasisPrediction::predictNextValue)
                .sum();
    }

    public static long predictNextValue(List<Long> history) {
        Long[] differences = new Long[history.size() - 1];

        for (int h = 0; h < history.size() - 1; h++) {
            differences[h] = history.get(h + 1) - history.get(h);
        }

        if (Arrays.stream(differences).noneMatch(d -> d != 0)) {
            return history.get(history.size() - 1);
        }
        else {
            long predictedDiff = predictNextValue(Arrays.asList(differences));
            return history.get(history.size() - 1) + predictedDiff;
        }
    }

    public static long calcExtrapolatedValuesAtBeginning(List<String> input) {

        List<List<Long>> histories = input.stream()
                .map(l -> Arrays.stream(l.split(" "))
                        .mapToLong(Long::parseLong)
                        .boxed()
                        .toList())
                .toList();

        return histories.stream()
                .mapToLong(OasisPrediction::predictNextValueAtBeginning)
                .sum();
    }

    public static long predictNextValueAtBeginning(List<Long> history) {
        Long[] differences = new Long[history.size() - 1];

        for (int h = 0; h < history.size() - 1; h++) {
            differences[h] = history.get(h + 1) - history.get(h);
        }

        if (Arrays.stream(differences).noneMatch(d -> d != 0)) {
            return history.get(0);
        }
        else {
            long predictedDiff = predictNextValueAtBeginning(Arrays.asList(differences));
            return history.get(0) - predictedDiff;
        }
    }
}
