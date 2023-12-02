package day2;

import input.AdventInput;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class CubeGame {

    public static void main(String[] args) {
        List<String> exampleInput1 = AdventInput.getInput(2, 1, true);
        System.out.println("Example 1: " + calcPossibleIdSum(exampleInput1));

        List<String> taskInput1 = AdventInput.getInput(2, 1, false);
        System.out.println("Task 1: " + calcPossibleIdSum(taskInput1));

        List<String> exampleInput2 = AdventInput.getInput(2, 2, true);
        System.out.println("Example 2: " + calcMinimalCubesPowerSum(exampleInput2));

        List<String> taskInput2 = AdventInput.getInput(2, 2, false);
        System.out.println("Task 2: " + calcMinimalCubesPowerSum(taskInput2));
    }

    public static int calcPossibleIdSum(List<String> games) {
        List<Boolean> value = games.stream()
                .map(game -> game.replaceAll("\\w* \\d*: ", ""))
                .map(grabs -> Arrays.stream(grabs.split(";"))
                        .map(grab -> Arrays.stream(grab.split(","))
                                .map(String::trim)
                                .toList())
                        .toList()
                )
                .map(grabs -> {
                    Map<String, Integer> grabValues = new HashMap<>();
                    grabs.forEach(grab -> grab.forEach(cubes -> {
                                String[] split = cubes.split(" ");
                                Integer count = grabValues.getOrDefault(split[1], 0);
                                if (count < Integer.parseInt(split[0])) {
                                    grabValues.put(split[1], Integer.valueOf(split[0]));
                                }
                            }));
                    return grabValues;
                })
                .map(grabValues ->
                    grabValues.getOrDefault("red", 0) <= 12
                            && grabValues.getOrDefault("green", 0) <= 13
                            && grabValues.getOrDefault("blue", 0) <= 14)
                .toList();

        return IntStream
                .range(0, value.size())
                .filter(value::get)
                .map(i -> i + 1)
                .sum();
    }


    public static int calcMinimalCubesPowerSum(List<String> games) {
        return games.stream()
                .map(game -> game.replaceAll("\\w* \\d*: ", ""))
                .map(grabs -> Arrays.stream(grabs.split(";"))
                        .map(grab -> Arrays.stream(grab.split(","))
                                .map(String::trim)
                                .toList())
                        .toList()
                )
                .map(grabs -> {
                    Map<String, Integer> grabValues = new HashMap<>();
                    grabs.forEach(grab -> grab.forEach(cubes -> {
                        String[] split = cubes.split(" ");
                        Integer count = grabValues.getOrDefault(split[1], 0);
                        if (count < Integer.parseInt(split[0])) {
                            grabValues.put(split[1], Integer.valueOf(split[0]));
                        }
                    }));
                    return grabValues;
                })
                .map(grabValues -> grabValues.values().stream()
                        .mapToInt(Integer::intValue)
                        .reduce(1, (a, b) -> a * b))
                .mapToInt(Integer::intValue)
                .sum();
    }
}
