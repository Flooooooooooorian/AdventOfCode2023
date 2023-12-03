package day3;

import input.AdventInput;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    public static void main(String[] args) {
        List<String> exampleInput1 = AdventInput.getInput(3, 1, true);
        System.out.println("Example 1: " + calcEnginePartNumberSum(exampleInput1));

        List<String> taskInput1 = AdventInput.getInput(3, 1, false);
        System.out.println("Task 1: " + calcEnginePartNumberSum(taskInput1));

        List<String> exampleInput2 = AdventInput.getInput(3, 2, true);
        System.out.println("Example 2: " + calcGearRatioSum(exampleInput2));

        List<String> taskInput2 = AdventInput.getInput(3, 2, false);
        System.out.println("Task 2: " + calcGearRatioSum(taskInput2));
    }

    public static int calcEnginePartNumberSum(List<String> input) {
        var scheme = input.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        List<List<Integer>> parts = new ArrayList<>();

        for (int y = 0; y < scheme.length; y++) {
            char[] line = scheme[y];
            for (int x = 0; x < line.length; x++) {
                char symbol = line[x];
                if (!Character.isDigit(symbol) && symbol != '.') {
                    parts.add(List.of(x, y));
                }
            }
        }

        int sum = 0;

        for (List<Integer> part : parts) {
            int partX = part.get(0);
            int partY = part.get(1);
            int[][] directions = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};

            List<String> partNumbers = new ArrayList<>();

            for (int[] direction : directions) {
                int partNumberX = partX + direction[0];
                int partNumberY = partY + direction[1];
                if (partNumberY >= 0
                        && partNumberY < scheme.length
                        && partNumberX >= 0
                        && partNumberX < scheme[partNumberY].length
                        && Character.isDigit(scheme[partNumberY][partNumberX])) {

                   int lastNumberIndex = partNumberX;
                   while (lastNumberIndex + 1 < scheme[partNumberY].length && Character.isDigit(scheme[partNumberY][lastNumberIndex + 1])) {
                       lastNumberIndex++;
                   }

                    int firstNumberIndex = partNumberX;
                    while (firstNumberIndex > 0 && Character.isDigit(scheme[partNumberY][firstNumberIndex-1])) {
                        firstNumberIndex--;
                    }

                    StringBuilder sb = new StringBuilder();
                    for (int i = firstNumberIndex; i <= lastNumberIndex; i++) {
                        sb.append(scheme[partNumberY][i]);
                    }
                    String number = sb.toString();
                    if(!partNumbers.contains(number)) {
                        partNumbers.add(number);
                    }
                }
            }
            sum += partNumbers.stream()
                    .mapToInt(Integer::valueOf)
                    .sum();
        }
        return sum;
    }

    public static int calcGearRatioSum(List<String> input) {
        var scheme = input.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        List<List<Integer>> possibleGears = new ArrayList<>();

        for (int y = 0; y < scheme.length; y++) {
            char[] line = scheme[y];
            for (int x = 0; x < line.length; x++) {
                char symbol = line[x];
                if (!Character.isDigit(symbol) && symbol == '*') {
                    possibleGears.add(List.of(x, y));
                }
            }
        }

        int sum = 0;

        for (List<Integer> part : possibleGears) {
            int partX = part.get(0);
            int partY = part.get(1);
            int[][] directions = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};

            List<String> partNumbers = new ArrayList<>();

            for (int[] direction : directions) {
                int partNumberX = partX + direction[0];
                int partNumberY = partY + direction[1];
                if (partNumberY >= 0
                        && partNumberY < scheme.length
                        && partNumberX >= 0
                        && partNumberX < scheme[partNumberY].length
                        && Character.isDigit(scheme[partNumberY][partNumberX])) {

                    int lastNumberIndex = partNumberX;
                    while (lastNumberIndex + 1 < scheme[partNumberY].length && Character.isDigit(scheme[partNumberY][lastNumberIndex + 1])) {
                        lastNumberIndex++;
                    }

                    int firstNumberIndex = partNumberX;
                    while (firstNumberIndex > 0 && Character.isDigit(scheme[partNumberY][firstNumberIndex-1])) {
                        firstNumberIndex--;
                    }

                    StringBuilder sb = new StringBuilder();
                    for (int i = firstNumberIndex; i <= lastNumberIndex; i++) {
                        sb.append(scheme[partNumberY][i]);
                    }
                    String number = sb.toString();
                    if(!partNumbers.contains(number)) {
                        partNumbers.add(number);
                    }
                }
            }
            if (partNumbers.size() == 2) {
                sum += Integer.parseInt(partNumbers.get(0)) * Integer.parseInt(partNumbers.get(1));
            }

        }
        return sum;
    }
}
