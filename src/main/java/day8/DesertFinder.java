package day8;

import input.AdventInput;

import java.util.*;
import java.util.stream.Collectors;

public class DesertFinder {

    public static void main(String[] args) {
        int day = 8;
        List<String> exampleInput1 = AdventInput.getInput(day, 1, true);
        System.out.println("Example 1: " + calcRequiredSteps(exampleInput1));

        List<String> taskInput1 = AdventInput.getInput(day, 1, false);
        System.out.println("Task 1: " + calcRequiredSteps(taskInput1));

        List<String> exampleInput2 = AdventInput.getInput(day, 2, true);
        System.out.println("Example 2: " + calcRequiredStepsWithGhostMovement(exampleInput2));

        List<String> taskInput2 = AdventInput.getInput(day, 2, false);
        System.out.println("Task 2: " + calcRequiredStepsWithGhostMovement(taskInput2));
    }

    public static long calcRequiredSteps(List<String> input) {
        Queue<Integer> directions = new LinkedList<>();

        for (String s : input.get(0).split("")) {
            directions.offer("L".equals(s) ? 0 : 1);
        }

        Map<String, List<String>> map = input.stream()
                .skip(1)
                .filter(s -> !s.isEmpty())
                .collect(Collectors
                        .toMap(instruction -> instruction.split("=")[0].trim(),
                                instruction -> Arrays.stream(instruction.split("=")[1].trim().split(","))
                                        .map(goal -> goal.replaceFirst("[()]", "").trim())
                                        .toList()));

        String currentPos = "AAA";

        long steps = 0;


        while (!currentPos.equals("ZZZ")) {
            if (directions.isEmpty()) {
                for (String s : input.get(0).split("")) {
                    directions.offer("L".equals(s) ? 0 : 1);
                }
            }

            currentPos = map.get(currentPos).get(directions.poll());
            steps++;
        }

        return steps;
    }

    public static long calcRequiredStepsWithGhostMovement(List<String> input) {
        Queue<Integer> directions = new LinkedList<>();

        for (String s : input.get(0).split("")) {
            directions.offer("L".equals(s) ? 0 : 1);
        }

        Map<String, List<String>> map = input.stream()
                .skip(1)
                .filter(s -> !s.isEmpty())
                .collect(Collectors
                        .toMap(instruction -> instruction.split("=")[0].trim(),
                                instruction -> Arrays.stream(instruction.split("=")[1].trim().split(","))
                                        .map(goal -> goal.replaceFirst("[()]", "").trim())
                                        .toList()));

        List<String> currentPositions = map.keySet().stream()
                .filter(s -> s.matches("..A"))
                .collect(Collectors.toList());

        long[] steps = new long[currentPositions.size()];

        for (int i = 0; i < currentPositions.size(); i++) {
            while (!currentPositions.get(i).matches("..Z")) {
                if (directions.isEmpty()) {
                    for (String s : input.get(0).split("")) {
                        directions.offer("L".equals(s) ? 0 : 1);
                    }
                }

                currentPositions.set(i, map.get(currentPositions.get(i)).get(directions.poll()));
                steps[i]++;
            }
        }

        long currentSteps = steps[0];

        for (int i = 1; i < steps.length; i++) {
            if (currentSteps > steps[i]) {
                long stepIncrease = currentSteps;
                while (currentSteps % steps[i] != 0) {
                    currentSteps += stepIncrease;
                }
            } else if (currentSteps < steps[i]) {
                long stepIncrease = steps[i];
                while (steps[i] % currentSteps != 0) {
                    steps[i] += stepIncrease;
                }
                currentSteps = steps[i];
            }
        }

        return currentSteps;
    }
}
