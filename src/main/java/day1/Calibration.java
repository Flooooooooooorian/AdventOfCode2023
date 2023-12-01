package day1;

import input.AdventInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Calibration {

    public static void main(String[] args) {

        List<String> exampleInput1 = AdventInput.getInput(1, 1, true);
        System.out.println("Example 1: " + calcCalibrationValue(exampleInput1));

        List<String> taskInput1 = AdventInput.getInput(1, 1, false);
        System.out.println("Task 1: " + calcCalibrationValue(taskInput1));


        List<String> exampleInput2 = AdventInput.getInput(1, 2, true);
        System.out.println("Example 2: " + calcAdvancedCalibrationValue(exampleInput2));

        List<String> taskInput2 = AdventInput.getInput(1, 2, false);
        System.out.println("Task 2: " + calcAdvancedCalibrationValue(taskInput2));
    }

    public static int calcCalibrationValue(List<String> input) {

        return input.stream()
                .map(s -> s.chars()
                        .mapToObj(i -> (char) i)
                        .filter(Character::isDigit)
                        .map(character -> Character.toString(character))
                        .toList())
                .map(l -> IntStream.range(0, l.size())
                        .filter(i -> i == 0 || i == l.size() - 1)
                        .mapToObj(l::get)
                        .collect(Collectors.joining())
                )
                .map(s -> s.length() == 1 ? s + s : s)
                .mapToInt(Integer::valueOf)
                .sum();
    }

    public static int calcAdvancedCalibrationValue(List<String> input) {

        return input.stream()
                .map(s -> {
                    List<String> numbers = new ArrayList<>(Arrays.asList(new String[s.length()]));
                    List<String> values = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9",
                            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
                    for (int i = 0; i < values.size(); i++) {
                        String value = values.get(i);

                        int index = 0;
                        do {
                            index = s.indexOf(value, index);
                            if (index != -1) {
                                numbers.set(index, values.get((i + 9) % 9));
                                index += value.length();
                            }
                        }
                        while (index != -1);

                    }
                    return numbers.stream()
                            .filter(Objects::nonNull)
                            .toList();
                })
                .map(l -> IntStream.range(0, l.size())
                        .filter(i -> i == 0 || i == l.size() - 1)
                        .mapToObj(l::get)
                        .collect(Collectors.joining())
                )
                .filter(s -> !s.isBlank())
                .map(s -> s.length() == 1 ? s + s : s)
                .mapToInt(Integer::valueOf)
                .sum();


    }
}
