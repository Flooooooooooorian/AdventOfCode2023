package day5;

import input.AdventInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Seeds {

    public static void main(String[] args) {
        List<String> exampleInput1 = AdventInput.getInput(5, 1, true);
        System.out.println("Example 1: " + calcClosestSeed(String.join("\n", exampleInput1)));

        List<String> taskInput1 = AdventInput.getInput(5, 1, false);
        System.out.println("Task 1: " + calcClosestSeed(String.join("\n", taskInput1)));

        List<String> exampleInput2 = AdventInput.getInput(5, 2, true);
        System.out.println("Example 2: " + calcClosestSeedRange(String.join("\n", exampleInput2)));

        List<String> taskInput2 = AdventInput.getInput(5, 2, false);
        System.out.println("Task 2: " + calcClosestSeedRange(String.join("\n", taskInput2)));
    }

    public static long calcClosestSeed(String input) {

        List<Long> seeds = Arrays.stream(input.split("\n")[0].split(": ")[1].split(" "))
                .mapToLong(Long::parseLong)
                .boxed()
                .toList();

        var parts = Arrays.stream(input.split("\n\n"))
                .skip(1)
                .map(part -> part.replaceFirst(".*:\\n", ""))
                .map(part -> part.split("\n"))
                .map(mappings -> Arrays.stream(mappings)
                        .map(mapping -> Arrays.stream(mapping.split(" "))
                                .mapToLong(Long::parseLong)
                                .boxed()
                                .toList())
                        .toList())
                .map(mappings -> mappings.stream()
                        .map(mapping -> new SingleMapping(mapping.get(1), mapping.get(0), mapping.get(2)))
                        .toList())
                .toList();

        return seeds.stream()
                .map(seed -> {
                            var value = seed;
                            for (List<SingleMapping> mapping : parts) {
                                for (SingleMapping part : mapping) {
                                    if (part.source() <= value && part.source() + part.distance() > value) {
                                        value = part.destination() + (value - part.source());
                                        break;
                                    }
                                }
                            }
                            return value;
                        }
                )
                .mapToLong(Long::longValue)
                .min()
                .getAsLong();
    }
    public static long calcClosestSeedRange(String input) {

        List<Long> seedRanges = Arrays.stream(input.split("\n")[0].split(": ")[1].split(" "))
                .mapToLong(Long::parseLong)
                .boxed()
                .toList();

        var parts = Arrays.stream(input.split("\n\n"))
                .skip(1)
                .map(part -> part.replaceFirst(".*:\\n", ""))
                .map(part -> part.split("\n"))
                .map(mappings -> Arrays.stream(mappings)
                        .map(mapping -> Arrays.stream(mapping.split(" "))
                                .mapToLong(Long::parseLong)
                                .boxed()
                                .toList())
                        .toList())
                .map(mappings -> mappings.stream()
                        .map(mapping -> new SingleMapping(mapping.get(1), mapping.get(0), mapping.get(2)))
                        .toList())
                .toList();

        Long closestSeed = null;
        for (int i = 0; i < seedRanges.size(); i +=2) {
            for (long seed = seedRanges.get(i); seed < seedRanges.get(i) + seedRanges.get(i + 1); seed++) {
                var value = seed;
                for (List<SingleMapping> mapping : parts) {
                    for (SingleMapping part : mapping) {
                        if (part.source() <= value && part.source() + part.distance() > value) {
                            value = part.destination() + (value - part.source());
                            break;
                        }
                    }
                }

                if (closestSeed == null || closestSeed > value) {
                    closestSeed = value;
                }
            }
        }
        return closestSeed;
    }
}
