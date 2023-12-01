package input;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AdventInput {

    public static List<String> getInput(int day) {
        return getInput(day, 1, false);
    }

    public static List<String> getInput(int day, int challenge, boolean example) {
        String fileName = "./src/main/resources/day" + day + "/inputDay" + day + "-" + challenge + (example ? "-example" : "") + ".txt";
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
