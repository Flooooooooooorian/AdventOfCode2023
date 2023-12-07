package day6;

import input.AdventInput;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class BoatRace {

    public static void main(String[] args) {
        List<String> exampleInput1 = AdventInput.getInput(6, 1, true);
        System.out.println("Example 1: " + calcBoatRaceWinningChances(exampleInput1));

        List<String> taskInput1 = AdventInput.getInput(6, 1, false);
        System.out.println("Task 1: " + calcBoatRaceWinningChances(taskInput1));

        List<String> exampleInput2 = AdventInput.getInput(6, 2, true);
        System.out.println("Example 2: " + calcBoatRaceWinningChances(exampleInput2));

        List<String> taskInput2 = AdventInput.getInput(6, 2, false);
        System.out.println("Task 2: " + calcBoatRaceWinningChances(taskInput2));
    }

    public static Long calcBoatRaceWinningChances(List<String> input) {

        var result = input.stream()
                .map(line -> line.split(":")[1].trim())
                .map(line -> Arrays.stream(line.split(" "))
                        .filter(value -> !value.isBlank())
                        .mapToLong(Long::parseLong)
                        .boxed()
                        .toList())
                .toList();
        var races = IntStream.range(0, result.get(0).size())
                .mapToObj(i -> new Race(result.get(0).get(i), result.get(1).get(i)))
                .toList();

        return races.stream()
                .map(race -> LongStream.range(1, race.maxTime())
                        .map(time -> (race.maxTime() - time) * time)
                        .filter(distance -> distance > race.recordDistance())
                        .count())
                .reduce(1L, ((a, b) -> a * b));
    }
}
