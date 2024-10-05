package hexlet.code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public final class Differ {
    public static String generate(String path1, String path2) throws IOException {
        var nameToValueForFile1 = Parser.parse(path1);
        var nameToValueForFile2 = Parser.parse(path2);

        var diff = new ArrayList<DiffLine>();
        var file1Pairs = nameToValueForFile1.entrySet();
        for (var file1Pair : file1Pairs) {
            var fieldName = file1Pair.getKey();
            var fieldValue = Optional.ofNullable(file1Pair.getValue()).orElse("null");

            if (nameToValueForFile2.containsKey(fieldName)) {
                var updatedFieldValue = Optional.ofNullable(nameToValueForFile2.get(fieldName)).orElse("null");

                if (fieldValue.equals(updatedFieldValue)) {
                    diff.add(new DiffLine(Sign.NONE, fieldName, fieldValue));
                    continue;
                }
                diff.add(new DiffLine(Sign.PLUS, fieldName, updatedFieldValue));
            }
            diff.add(new DiffLine(Sign.MINUS, fieldName, fieldValue));
        }

        for (var fieldName : nameToValueForFile1.keySet()) {
            nameToValueForFile2.remove(fieldName);
        }

        var remainingFile2Pairs = nameToValueForFile2.entrySet();
        for (var file2Pair : remainingFile2Pairs) {
            diff.add(new DiffLine(Sign.PLUS, file2Pair.getKey(), file2Pair.getValue()));
        }

        return
            diff
                .stream()
                .sorted(
                    Comparator
                        .comparing(DiffLine::name)
                        .thenComparing(DiffLine::sign)
                )
                .map(DiffLine::toString)
                .collect(Collectors.joining("\n  ", "{\n  ", "\n}"));
    }
}
