package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public final class Differ {
    public static String generate(String path1, String path2) throws IOException {
        var objectMapper = new ObjectMapper();
        var nameToValueForFile1 = parseJson(path1, objectMapper);
        var nameToValueForFile2 = parseJson(path2, objectMapper);

        var diff = new ArrayList<DiffLine>();
        var file1JsonPairs = nameToValueForFile1.entrySet();
        for (var file1JsonPair : file1JsonPairs) {
            var fieldName = file1JsonPair.getKey();
            var fieldValue = file1JsonPair.getValue();
            var updatedFieldValue = nameToValueForFile2.get(fieldName);

            if (fieldValue.equals(updatedFieldValue)) {
                diff.add(new DiffLine(Sign.NONE, fieldName, fieldValue));
                continue;
            }

            diff.add(new DiffLine(Sign.MINUS, fieldName, fieldValue));
            if (updatedFieldValue != null) {
                diff.add(new DiffLine(Sign.PLUS, fieldName, updatedFieldValue));
            }
        }

        for (var fieldName : nameToValueForFile1.keySet()) {
            nameToValueForFile2.remove(fieldName);
        }

        var remainFile2JsonPairs = nameToValueForFile2.entrySet();
        for (var file2JsonPair : remainFile2JsonPairs) {
            diff.add(new DiffLine(Sign.PLUS, file2JsonPair.getKey(), file2JsonPair.getValue()));
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

    private static Map<String, String> parseJson(String path2, ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(
            toFile(path2),
            new TypeReference<>() {}
        );
    }

    public static File toFile(String uri) {
        return Paths
            .get(uri)
            .toAbsolutePath()
            .normalize()
            .toFile();
    }
}
