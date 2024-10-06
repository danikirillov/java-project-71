package hexlet.code;

import hexlet.code.formatters.Formatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public final class Differ {
    public static String generate(String path1, String path2) throws IOException {
        return generate(path1, path2, "stylish");
    }

    public static String generate(String path1, String path2, String format) throws IOException {
        var nameToValueForFile1 = Parser.parse(path1);
        var nameToValueForFile2 = Parser.parse(path2);

        var diff = new ArrayList<DiffLine>();
        var file1Pairs = nameToValueForFile1.entrySet();
        for (var file1Pair : file1Pairs) {
            var fieldName = file1Pair.getKey();
            var fieldValue = file1Pair.getValue();
            var updatedFieldValue = nameToValueForFile2.get(fieldName);
            var status = Status.REMOVED;
            if (nameToValueForFile2.containsKey(fieldName)) {
                status = Objects.equals(fieldValue, updatedFieldValue) ? Status.UNTOUCHED : Status.UPDATED;
            }
            diff.add(new DiffLine(status, fieldName, fieldValue, updatedFieldValue));
        }

        for (var fieldName : nameToValueForFile1.keySet()) {
            nameToValueForFile2.remove(fieldName);
        }

        var remainingFile2Pairs = nameToValueForFile2.entrySet();
        for (var file2Pair : remainingFile2Pairs) {
            diff.add(new DiffLine(Status.ADDED, file2Pair.getKey(), null, file2Pair.getValue()));
        }

        diff.sort(Comparator.comparing(DiffLine::name));
        return Formatter.format(diff, format);
    }
}
